package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection initDB() throws SQLException, ClassNotFoundException {

        /*String dbDriver = "oracle.jdbc.driver.OracleDriver";
        String dbURL = "jdbc:oracle:thin:@localhost:1521:orcl";
        String dbUsername = "c##julia";
        String dbPassword = "oK16081969";*/
    	
    	//String dbDriver = "oracle.jdbc.driver.OracleDriver";
        String dbURL = "jdbc:postgresql://127.0.0.1:5432/airline_IS";
        String dbUsername = "postgres";
        String dbPassword = "oK16081969";

        //Class.forName(dbDriver);
        return DriverManager.getConnection(dbURL, dbUsername, dbPassword);
    }
}
