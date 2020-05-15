package com.knu.ynortman.lab2.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.CrewRole;
import com.knu.ynortman.lab2.model.CrewRoleEnum;
import com.knu.ynortman.lab2.model.Flight;
import com.knu.ynortman.lab2.util.JdbcConnection;

public class CrewMembersDao {
	private static final Logger logger = LogManager.getRootLogger();
	
	private static final String crewByFlightIdQuery = 
			"SELECT crew_member.id, crew_member.name, crew_role.id, crew_role.role " + 
			"FROM crew_member INNER JOIN crew_role ON crew_member.role_id = crew_role.id " + 
			"WHERE crew_member.id IN ( " + 
			"						SELECT crew_flight.crew_id " + 
			"						FROM crew_flight " + 
			"						WHERE crew_flight.flight_id = ?)";
	private static final String flightsByCrewIdQuery = 
			"SELECT DISTINCT flight.id " + 
			"FROM flight INNER JOIN crew_flight ON flight.id = crew_flight.flight_id " + 
			"WHERE crew_flight.crew_id = ?";
	private static final String crewMemberByIdQuery = 
			"SELECT crew_member.id, crew_member.name, crew_role.id, crew_role.role " + 
			"FROM crew_member INNER JOIN crew_role ON crew_member.role_id = crew_role.id " + 
			"WHERE crew_member.id = ?";
	private static final String allCrewMembers = 
			"SELECT crew_member.id, crew_member.name, crew_role.id, crew_role.role " + 
			"FROM crew_member INNER JOIN crew_role ON crew_member.role_id = crew_role.id ";
	private static final String addCrewMemberQuery = "INSERT INTO crew_member(name, role_id) VALUES (?, ?)";
	private static final String updateCrewMemberQuery = "UPDATE crew_member SET name = ?, role_id = ? WHERE id = ?";
	private static final String deleteCrewMemberQuery = "DELETE FROM crew_member WHERE id = ?";
	
	public static List<CrewMember> getFlightMembers(int flightId) throws ServerException {
		List<CrewMember> members = new LinkedList<CrewMember>();
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(crewByFlightIdQuery);
			ps.setInt(1, flightId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CrewMember member = crewMemberFromResultSet(rs);
				if(member != null) {
					members.add(member);
				}
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get members");
			throw new ServerException();
		}
		return members;
	}
	
	public static List<CrewMember> getAllCrewMembers() throws ServerException {
		List<CrewMember> members = new LinkedList<CrewMember>();
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(allCrewMembers);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CrewMember member = crewMemberFromResultSet(rs);
				if(member != null) {
					members.add(member);
				}
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get members");
			throw new ServerException();
		}
		return members;
	}
	
	public static CrewMember getCrewMemberById(int id) throws ServerException {
		CrewMember member = null;
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(crewMemberByIdQuery);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				member = crewMemberFromResultSet(rs);
			} else {
				logger.error("Cannot get crew member by id");
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get crew member by id");
			throw new ServerException();
		}
		return member;
	}
	
	public static List<Flight> getMemberFlights(int id) throws ServerException {
		List<Flight> flights = new LinkedList<>();
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(flightsByCrewIdQuery);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Flight flight = FlightDao.getFlightById(rs.getInt(1));
				if(flight != null) {
					flights.add(flight);
				}
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get all flights by member id");
			throw new ServerException();
		}
		return flights;
	}
	
	public static boolean isCrewMemberExists(int id) throws ServerException {
		return (getCrewMemberById(id) != null);
	}
	
	public static CrewMember addCrewMember(CrewMember member) throws ServerException {
		if(member == null) {
			return null;
		}
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(addCrewMemberQuery);
			ps.setString(1, member.getName());
			ps.setInt(2, member.getRole().getId());
			int rows = ps.executeUpdate();
			if(rows <= 0) {
				logger.warn("Cannot insert crew member");
				return null;
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot add crew member");
			throw new ServerException();
		}
		return member;
	}
	
	public static CrewMember updateCrewMember(CrewMember member) throws ServerException {
		if(member == null) return null;
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(updateCrewMemberQuery);
			ps.setString(1, member.getName());
			ps.setInt(2, member.getRole().getId());
			ps.setInt(3, member.getId());
			int rows = ps.executeUpdate();
			if(rows <= 0) {
				logger.warn("Cannot update member");
				return null;
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot update member");
			throw new ServerException();
		}
		return member;
	}
	
	public static void deleteById(int id) throws ServerException {
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(deleteCrewMemberQuery);
			ps.setInt(1, id);
			if(ps.executeUpdate() <= 0) {
				logger.warn("Cannot delete crew member");
			}
		} catch (SQLException | IOException e) {
			logger.error("Error deleting crew member");
			throw new ServerException();
		}
	}
	
	private static CrewMember crewMemberFromResultSet(ResultSet rs) throws SQLException {
		CrewMember member = new CrewMember();
		member.setId(rs.getInt(1));
		member.setName(rs.getString(2));
		CrewRole role = new CrewRole();
		role.setId(rs.getInt(3));
		role.setRole(CrewRoleEnum.valueOf(rs.getString(4)));
		member.setRole(role);
		
		return member;
	}
}