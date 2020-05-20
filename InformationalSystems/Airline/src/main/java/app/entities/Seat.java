package app.entities;

public class Seat {
    private int id;
    private int flightId;
    private int number;
    private boolean free;

    public Seat(int id, int flightId, int number, boolean free) {
        this.id = id;
        this.flightId = flightId;
        this.number = number;
        this.free = free;
    }

    public Seat(int id){
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isFree() {
        return free;
    }
}
