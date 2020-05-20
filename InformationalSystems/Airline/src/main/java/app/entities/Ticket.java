package app.entities;

public class Ticket {
    private int id;
    private Flight flight;
    private Seat seat;
    private User user;
    private boolean baggage;
    private boolean priority;
    private double price;

    public Ticket(int id, Seat seat, boolean baggage, boolean priority, double price) {
        this.id = id;
        this.seat = seat;
        this.baggage = baggage;
        this.priority = priority;
        this.price = price;
    }

    public Ticket(Seat seat, User user, boolean baggage, boolean priority, double price) {
        this.seat = seat;
        this.user = user;
        this.baggage = baggage;
        this.priority = priority;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public Flight getFlight() {
        return flight;
    }

    public Seat getSeat() {
        return seat;
    }

    public User getUser() {
        return user;
    }

    public boolean hasBaggage() {
        return baggage;
    }

    public boolean hasPriority() {
        return priority;
    }

    public boolean isBaggage() {
        return baggage;
    }

    public boolean isPriority() {
        return priority;
    }

    public double getPrice() {
        return price;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
