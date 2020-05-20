package app.entities;

import java.util.List;

public class Flight {
    private int id;
    private String direction;
    private String departureTime;
    private String flightTime;
    private int seatsNum;
    private int seatsNumAvailable;
    private List<Seat> seats;
    private double startPrice;
    private double price;

    public Flight(int id, String direction, String departureTime, String flightTime, int seatsNum, double startPrice) {
        this.id = id;
        this.direction = direction;
        this.departureTime = departureTime;
        this.seatsNum = seatsNum;
        this.flightTime = flightTime;
        this.startPrice = startPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public void setSeatsNum(int seatsNum) {
        this.seatsNum = seatsNum;
    }

    public int getSeatsNum() {
        return seatsNum;
    }

    public void setSeatsNumAvailable(int seatsNumAvailable) {
        this.seatsNumAvailable = seatsNumAvailable;
    }

    public int getSeatsNumAvailable() {
        return seatsNumAvailable;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
