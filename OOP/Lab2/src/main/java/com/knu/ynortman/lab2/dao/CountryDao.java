package com.knu.ynortman.lab2.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.Country;
import com.knu.ynortman.lab2.util.JdbcConnection;

public class CountryDao {
	private static final Logger logger = LogManager.getRootLogger();
	private static final String idCountryQuery = "SELECT * FROM country WHERE country.id = ?";
	
	public static Country getCountryById(int id) throws ServerException {
		Country country = null;
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(idCountryQuery);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				country = new Country();
				country.setId(rs.getInt(1));
				country.setName(rs.getString(2));
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get country");
			throw new ServerException();
		}
		return country;
	}
}
