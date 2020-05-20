package app.dao;

import app.entities.Seat;
import app.entities.Ticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {
    public List<Ticket> findTicketsOfUser(int userId) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "select * from tickets where user_id=" + userId;

        ResultSet rs = statement.executeQuery(sql);

        List<Ticket> tickets = new ArrayList<Ticket>();
        while (rs.next()){
            int id = rs.getInt("ticket_id");
            int seatId = rs.getInt("seat_id");
            boolean baggage = rs.getInt("baggage") > 0;
            boolean priority = rs.getInt("prioritet") > 0;
            double price = rs.getDouble("price");
            tickets.add(new Ticket(id, new Seat(seatId), baggage, priority, price));
        }

        rs.close();
        conn.close();

        return tickets;
    }

    public void insert(Ticket ticket) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        int baggage = 0;
        if(ticket.hasBaggage()) baggage = 1;
        int priority = 0;
        if(ticket.hasPriority()) priority = 1;
        
        String sql = "insert into tickets (seat_id, user_id, baggage, prioritet, price) values(" + ticket.getSeat().getId() + ", " +
                ticket.getUser().getId() + ", " + baggage + ", " + priority + ", " + ticket.getPrice() + ")";

        statement.executeUpdate(sql);
        conn.close();
    }
}
