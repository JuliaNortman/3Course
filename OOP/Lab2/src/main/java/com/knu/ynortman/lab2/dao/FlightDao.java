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

import com.knu.ynortman.lab2.model.City;
import com.knu.ynortman.lab2.model.Flight;
import com.knu.ynortman.lab2.util.JdbcConnection;

public class FlightDao {
	private static final Logger logger = LogManager.getRootLogger();

	private static final String allFlightsQuery = "SELECT * FROM flight";
	private static final String idFlightQuery = "SELECT * FROM flight WHERE flight.id= ?";
	private static final String addFlightQuery = "INSERT INTO flight(departure_city_id, departure_time, dest_city_id, dest_time) "
			+ "VALUES(?, ?, ?, ?)";
	private final String deleteFlightQuery = "DELETE FROM flight WHERE flight.id = ?";
	
	public static List<Flight> getAllFlights() {
		List<Flight> result = new LinkedList<Flight>();
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(allFlightsQuery);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Flight flight = new Flight();
				flight.setId(rs.getInt(1));
				flight.setDepartTime(LocalDateTime.parse(rs.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				flight.setDestTime(LocalDateTime.parse(rs.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				
				City depCity = CityDao.getCityById(rs.getInt(2));
				if(depCity == null) {
					logger.warn("Cannot get departure city");
					continue;
				} else {
					flight.setDepartCity(depCity);
				}
				
				City destCity = CityDao.getCityById(rs.getInt(4));
				if(destCity == null) {
					logger.warn("Cannot get destination city");
					continue;
				} else {
					flight.setDestCity(destCity);
				}
				flight.setCrewMembers(CrewMembersDao.getFlightMembers(flight.getId()));
				result.add(flight);
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get all flights");
		}
		return result;
	}
	
	public static Flight getFlightById(int id) {
		Flight flight = null;
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(idFlightQuery);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				flight = new Flight();
				flight.setId(rs.getInt(1));
				flight.setDepartTime(LocalDateTime.parse(rs.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				logger.debug(LocalDateTime.parse(rs.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				logger.debug(flight.getDepartTime());
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
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get all flights");
		}
		return flight;
	}
	
	public static Flight insertFlight(Flight flight) {
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
			return null;
		}
		
		return flight;
	}

}
