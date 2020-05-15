package com.knu.ynortman.lab2.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnection {
	public static Connection getConnection() throws FileNotFoundException, IOException, SQLException {
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + "application.properties";
		 
		Properties appProps = new Properties();
		appProps.load(new FileInputStream(appConfigPath));
		String url = appProps.getProperty("jdbc.database.url");
		String username = appProps.getProperty("jdbc.database.username");
		String password = appProps.getProperty("jdbc.database.password");
		return DriverManager.getConnection(url, username, password);
	}
}
