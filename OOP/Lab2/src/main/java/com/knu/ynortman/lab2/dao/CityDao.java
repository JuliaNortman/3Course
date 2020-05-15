package com.knu.ynortman.lab2.dao;

import java.awt.Container;
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
import com.knu.ynortman.lab2.model.City;
import com.knu.ynortman.lab2.model.Country;
import com.knu.ynortman.lab2.util.JdbcConnection;

public class CityDao {

	private static final Logger logger = LogManager.getRootLogger();
	private static final String idCityQuery = "SELECT * FROM city WHERE city.id = ?";
	private static final String allCitiesQuery = "SELECT * FROM city";
	
	public static City getCityById(int id) throws ServerException {
		City city = null;
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(idCityQuery);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				city = new City();
				city.setId(rs.getInt(1));
				city.setName(rs.getString(2));
				Country country = CountryDao.getCountryById(rs.getInt(3));
				if(country == null) {
					logger.warn("Country with id " + rs.getInt(3) + " does not exist");
					return null;
				} else {
					city.setCountry(country);
				}
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get city");
			throw new ServerException();
		} 
		return city;
	}
	
	public static List<City> getAllCities() throws ServerException {
		List<City> cities = new LinkedList<City>();
		try(Connection conn = JdbcConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(allCitiesQuery);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				City city = new City();
				city.setId(rs.getInt(1));
				city.setName(rs.getString(2));
				Country country = CountryDao.getCountryById(rs.getInt(3));
				if(country == null) {
					logger.warn("Country with id " + rs.getInt(3) + " does not exist");
				} else {
					city.setCountry(country);
					cities.add(city);
				}
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get cities");
			throw new ServerException();
		}
		return cities;
	}
}