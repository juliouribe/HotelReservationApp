package Models;

import java.time.LocalDate;

public class Reservation {
    public Customer customer;
    public IRoom room;
    public LocalDate checkInDate;
    public LocalDate checkOutDate;

    public Reservation (Customer customer, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

    }
    @Override
    public String toString() {
        return "Reservation has " + customer + " in room " + room + " from " + checkInDate + " to " + checkOutDate;
    }
}
