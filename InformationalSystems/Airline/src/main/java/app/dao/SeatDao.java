package app.dao;

import app.entities.Seat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SeatDao {
    public List<Seat> findSeatsOfFlight(int flightId) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "select * from seats where flight_id=" + flightId;

        ResultSet rs = statement.executeQuery(sql);

        List<Seat> seats = new ArrayList<Seat>();
        while (rs.next()){
            int id = rs.getInt("seat_id");
            int number = rs.getInt("quantity");
            int free = rs.getInt("isFree");
            boolean isFree = free > 0;
            seats.add(new Seat(id, flightId, number, isFree));
        }

        rs.close();
        conn.close();

        return seats;
    }

    public Seat findOne(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "select * from seats where seat_id="+id;

        ResultSet rs = statement.executeQuery(sql);

        Seat seat = null;
        if (rs.next()){
            int flightId = rs.getInt("flight_id");
            int number = rs.getInt("quantity");
            int free = rs.getInt("isFree");
            boolean isFree = free > 0;
            seat = new Seat(id, flightId, number, isFree);
        }

        rs.close();
        conn.close();

        return seat;
    }

    public int findNumOfFreeSeats(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "select count(*) as total from seats where flight_id="+ id + " and isfree=1";

        ResultSet rs = statement.executeQuery(sql);

        int count = 0;
        if (rs.next()){
            count = rs.getInt("total");
        }

        rs.close();
        conn.close();

        return count;
    }

    public void updateFreeStatus(int id, boolean isFree) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        int free = 0;
        if(isFree) free = 1;
        
        String sql = "update seats set isfree=" + free + " where seat_id=" + id;

        statement.executeUpdate(sql);
        conn.close();
    }
}
