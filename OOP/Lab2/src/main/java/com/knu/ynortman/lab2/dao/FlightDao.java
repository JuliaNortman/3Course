package com.knu.ynortman.lab2.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.knu.ynortman.lab2.model.Flight;
import com.knu.ynortman.lab2.util.JdbcConnection;

public class FlightDao {
	
	private final String allFlightsQuery = "SELECT * FROM flight";
	private final String idFlightQuery = "SELECT * FROM flight WHERE flight.id= ?";
	private final String addFlightQuery = "INSERT INTO flight(departure_city_id, departure_time, dest_city_id, dest_time) "
			+ "VALUES(?, ?, ?, ?)";
	private final String deleteFlightQuery = "DELETE FROM flight WHERE flight.id = ?";
	
	public List<Flight> getAllFlights() {
		Iterable<Flight> result = new LinkedList<Flight>();
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(allFlightsQuery);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
