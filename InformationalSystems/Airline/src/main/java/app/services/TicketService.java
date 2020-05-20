package app.services;

import app.dao.SeatDao;
import app.dao.TicketDao;
import app.dao.UserDao;
import app.entities.Seat;
import app.entities.Ticket;
import app.entities.User;

import java.sql.SQLException;

public class TicketService {
    private TicketDao ticketDao = new TicketDao();
    private UserDao userDao = new UserDao();
    private SeatDao seatDao = new SeatDao();

    public boolean buyTicket(String seatIdStr, String userIdStr, String baggageStr, String priorityStr, String priceStr) throws SQLException, ClassNotFoundException {
       int seatId = Integer.parseInt(seatIdStr);
       int userId = Integer.parseInt(userIdStr);
       boolean baggage = false;
       boolean priority = false;
       double price = Double.parseDouble(priceStr);

       if (baggageStr.equals("Available")){
           baggage = true;
       }
       if (priorityStr.equals("Available")){
           priority = true;
       }
       Seat seat = new Seat(seatId);
       User user = userDao.findOneById(userId);
       if (user.getAccount() < price) return false;

       Ticket ticket = new Ticket(seat, user, baggage, priority, price);
       userDao.updateAccount(userId, user.getAccount()-price);
       seatDao.updateFreeStatus(seatId, false);
       ticketDao.insert(ticket);
       return true;
    }
}
