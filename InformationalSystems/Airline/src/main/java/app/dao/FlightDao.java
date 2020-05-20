package app.dao;

import app.entities.Flight;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FlightDao {
    public List<Flight> findAll() throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "SELECT * FROM flights";

        ResultSet rs = statement.executeQuery(sql);

        List<Flight> flights = new ArrayList<Flight>();
        while (rs.next()){
            int id = rs.getInt("flight_id");
            String direction = rs.getString("direction");
            String departureTime = rs.getString("departure_time");
            String flightTime = rs.getString("flight_time");
            int seatsNum = rs.getInt("seats");
            double startPrice = rs.getDouble("start_price");
            int availiableSeats = rs.getInt("seats_num_available");
            Flight flight = new Flight(id, direction, departureTime, flightTime, seatsNum, startPrice);
            flight.setSeatsNumAvailable(availiableSeats);
            flights.add(new Flight(id, direction, departureTime, flightTime, seatsNum, startPrice));
        }
        System.out.println("flights number " + flights.size());
        
        rs.close();
        conn.close();

        return flights;
    }

    public Flight findOne(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "select * from flights where flight_id="+id;

        ResultSet rs = statement.executeQuery(sql);

        Flight flight = null;
        if (rs.next()){
            String direction = rs.getString("direction");
            String departureTime = rs.getString("departure_time");
            String flightTime = rs.getString("flight_time");
            int seatsNum = rs.getInt("seats");
            double startPrice = rs.getDouble("start_price");
            flight = new Flight(id, direction, departureTime, flightTime, seatsNum, startPrice);
        }

        rs.close();
        conn.close();

        return flight;
    }

    public double findStartPrice(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "select start_price from flights where flight_id="+id;

        ResultSet rs = statement.executeQuery(sql);

        double price = 0;
        if (rs.next()){
            price = rs.getDouble("start_price");
        }

        rs.close();
        conn.close();

        return price;
    }
}
