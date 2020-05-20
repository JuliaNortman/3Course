package app.services;

import app.dao.FlightDao;
import app.dao.SeatDao;
import app.entities.Flight;
import app.entities.Seat;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FlightService {
    private FlightDao flightDao = new FlightDao();
    private SeatDao seatDao = new SeatDao();

    public List<Flight> getAllFlights() throws SQLException, ClassNotFoundException, ParseException {
        List<Flight> flights = flightDao.findAll();
        for (Flight flight : flights){
            flight.setSeatsNumAvailable(seatDao.findNumOfFreeSeats(flight.getId()));
            flight.setPrice(calculateCurrPrice(flight));
        }
        return flights;
    }

    public Flight getFlight(int id) throws SQLException, ClassNotFoundException, ParseException {
        Flight flight = flightDao.findOne(id);
        List<Seat> seats = seatDao.findSeatsOfFlight(flight.getId());
        flight.setSeats(seats);
        int seatsNum = seatDao.findNumOfFreeSeats(id);
        flight.setSeatsNumAvailable(seatsNum);
        flight.setPrice(calculateCurrPrice(flight));
        return flight;
    }

    public Seat getSeat(int id) throws SQLException, ClassNotFoundException {
        return seatDao.findOne(id);
    }

    public double calculateCurrPrice(Flight flight) throws ParseException {
        double currPrice = flight.getStartPrice();
        currPrice += calcExtraPriceSeats(getFillPercent(flight.getSeatsNumAvailable(), flight.getSeatsNum()));
        currPrice += calcExtraPriceDate(getDepartureDateDiff(flight.getDepartureTime()));
        return currPrice;
    }

    public double calcExtraPriceDate(double diff){
        if (diff <= 90 && diff > 30){
            return 10;
        } else if (diff <= 30 && diff > 14){
            return  15;
        } else if (diff <= 14 && diff > 7){
            return  20;
        } else if (diff <= 7 && diff > 3){
            return  25;
        } else if (diff <= 3){
            return 35;
        } else {
            return 0;
        }
    }

    public double getDepartureDateDiff(String departureDateStr) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy - HH:mm");
        Date currDate = new Date();
        Date departureDate = df.parse(departureDateStr);

        long diff = Math.abs(departureDate.getTime() - currDate.getTime());
        return (double) diff / 1000 / 60 / 60 / 24;
    }

    public double calcExtraPriceSeats(double percent){
        if (percent <= 50 && percent > 25){
            return 5;
        } else if (percent <= 25 && percent > 15){
            return  10;
        } else if (percent <= 15 && percent > 5){
            return  15;
        } else if (percent <= 5){
            return  25;
        } else {
            return 0;
        }
    }

    public double getFillPercent(int freeSeatsNum, int allSeatsNum){
        return Math.round((double) freeSeatsNum * 100 / allSeatsNum);
    }
}
