package com.knu.ynortman.lab2.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.City;
import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.Flight;
import com.knu.ynortman.lab2.util.JdbcConnection;

public class FlightDao {
	private static final Logger logger = LogManager.getRootLogger();

	private static final String allFlightsQuery = "SELECT * FROM flight";
	private static final String idFlightQuery = "SELECT * FROM flight WHERE flight.id= ?";
	private static final String addFlightQuery = "INSERT INTO flight(departure_city_id, departure_time, dest_city_id, dest_time) "
			+ "VALUES(?, ?, ?, ?)";
	private static final String deleteFlightQuery = "DELETE FROM flight WHERE flight.id = ?";
	private static final String addMemberQuery = "INSERT INTO crew_flight VALUES (?, ?)";
	private static final String deleteMemberQuery = "DELETE FROM crew_flight WHERE flight_id = ? AND crew_id = ?";
	private static final String updateFlightQuery = "UPDATE flight "
			+ "SET  departure_city_id = ?, departure_time = ?, dest_city_id = ?, dest_time = ?"
			+ "WHERE id = ?";
	
	public static List<Flight> getAllFlights() throws ServerException {
		List<Flight> result = new LinkedList<Flight>();
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(allFlightsQuery);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Flight flight = flightFromResultSet(rs);
				if(flight != null) {
					result.add(flight);
				}
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get all flights");
			throw new ServerException();
		}
		return result;
	}
	
	public static Flight getFlightById(int id) throws ServerException {
		Flight flight = null;
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(idFlightQuery);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				flight = flightFromResultSet(rs);
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get all flights");
			throw new ServerException();
		}
		return flight;
	}
	
	public static Flight insertFlight(Flight flight) throws ServerException {
		if(flight == null) return null;
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(addFlightQuery);
			ps.setInt(1, flight.getDepartCity().getId());
			ps.setObject(2, flight.getDepartTime());
			ps.setInt(3, flight.getDestCity().getId());
			ps.setObject(4, flight.getDestTime());
			int rows = ps.executeUpdate();
			if(rows > 0) {
				logger.debug("Flight inserted");
			} else {
				logger.warn("Flight was not inserted");
				return null;
			}
		} catch (SQLException | IOException e) {
			logger.error("Error in adding flight");
			throw new ServerException();
		}
		
		return flight;
	}
	
	public static Flight addCrewMember(int flightId, CrewMember member) throws ServerException {
		Flight flight = getFlightById(flightId);
		member = CrewMembersDao.getCrewMemberById(member.getId());
		if(flight == null) {
			logger.error("Flight does not exist");
			return null;
		}
		if(!CrewMembersDao.isCrewMemberExists(member.getId())) {
			logger.error("Crew member does not exist");
			return null;
		}
		if(flight.getCrewMembers() != null && flight.getCrewMembers().contains(member)) {
			logger.debug("Member is already there");
			return flight;
		}
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(addMemberQuery);
			ps.setInt(1, flightId);
			ps.setInt(2, member.getId());
			int rows = ps.executeUpdate();
			if(rows > 0) {
				logger.debug("Succesfully insert crew member to flight");
				List<CrewMember> members = flight.getCrewMembers();
				if(members == null) {
					members = new LinkedList<CrewMember>();
				}
				members.add(member);
			} else {
				logger.error("Cannot insert crew member into flight crew");
				return null;
			}
		} catch (SQLException | IOException e) {
			logger.error("Exception in adding crew member");
			throw new ServerException();
		}
		return flight;
	}
	
	public static void deleteFlight(int id) throws ServerException {
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(deleteFlightQuery);
			ps.setInt(1, id);
			int rows = ps.executeUpdate();
			if(rows <= 0) {
				logger.warn("Cannot delete flight");
			}
		} catch (SQLException | IOException e) {
			logger.error("Error deleting flight");
			throw new ServerException();
		}
	}
	
	public static Flight deleteFlightMember(int flightId, CrewMember member) throws ServerException {
		Flight flight = getFlightById(flightId);
		if(flight == null) {
			logger.error("Flight does not exist");
			return null;
		}
		member = CrewMembersDao.getCrewMemberById(member.getId());
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(deleteMemberQuery);
			ps.setInt(1, flightId);
			ps.setInt(2, member.getId());
			int rows = ps.executeUpdate();
			if(rows <= 0) {
				logger.warn("Cannot delete member");
			}
			flight.getCrewMembers().remove(member);
		} catch (SQLException | IOException e) {
			logger.error("Cannot delete flight member");
			throw new ServerException();
		}
		return flight;
	}
	
	public static Flight updateFlight(Flight flight) throws ServerException {
		if(flight == null) return null;
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(updateFlightQuery);
			ps.setInt(1, flight.getDepartCity().getId());
			ps.setObject(2, flight.getDepartTime());
			ps.setInt(3, flight.getDestCity().getId());
			ps.setObject(4, flight.getDestTime());
			ps.setInt(5, flight.getId());
			if(ps.executeUpdate() <= 0) {
				logger.error("Cannot update flight");
				return null;
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot update flight");
			throw new ServerException();
		}
		return flight;
	}
	
	private static Flight flightFromResultSet(ResultSet rs) throws SQLException, ServerException {
		Flight flight = new Flight();
		flight.setId(rs.getInt(1));
		flight.setDepartTime(LocalDateTime.parse(rs.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		flight.setDestTime(LocalDateTime.parse(rs.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		City depCity = CityDao.getCityById(rs.getInt(2));
		if(depCity == null) {
			logger.warn("Cannot get departure city");
			return null;
		} else {
			flight.setDepartCity(depCity);
		}
		
		City destCity = CityDao.getCityById(rs.getInt(4));
		if(destCity == null) {
			logger.warn("Cannot get destination city");
			return null;
		} else {
			flight.setDestCity(destCity);
		}
		flight.setCrewMembers(CrewMembersDao.getFlightMembers(flight.getId()));
		return flight;
	}
}