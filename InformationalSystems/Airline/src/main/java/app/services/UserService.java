package app.services;

import app.dao.FlightDao;
import app.dao.SeatDao;
import app.dao.TicketDao;
import app.dao.UserDao;
import app.entities.Seat;
import app.entities.Ticket;
import app.entities.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDao userDao = new UserDao();
    private TicketDao ticketDao = new TicketDao();
    private SeatDao seatDao = new SeatDao();
    private FlightDao flightDao = new FlightDao();

    public User getUser(String login, String password) throws SQLException, ClassNotFoundException {
        return userDao.findOne(login, password);
    }

    public User getUserById(int id) throws SQLException, ClassNotFoundException {
    	System.out.println(id);
        User user = userDao.findOneById(id);
        List<Ticket> tickets = ticketDao.findTicketsOfUser(id);

        for(Ticket ticket : tickets){
            Seat seat = seatDao.findOne(ticket.getSeat().getId());
            ticket.setSeat(seat);
            ticket.setFlight(flightDao.findOne(seat.getFlightId()));
        }

        user.setTickets(tickets);
        return user;
    }

    public void register(User user) throws SQLException, ClassNotFoundException {
        userDao.insert(user);
    }

    public void replenish(int userId, String moneyStr) throws SQLException, ClassNotFoundException {
        double money = Double.parseDouble(moneyStr);
        User user = userDao.findOneById(userId);
        userDao.updateAccount(userId, user.getAccount() + money);
    }
}
