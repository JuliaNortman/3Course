package com.knu.ynortman.lab2.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
				flight.setDepartTime(LocalDateTime.parse(rs.getString(3)));
				flight.setDestTime(LocalDateTime.parse(rs.getString(5)));
				
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

}
