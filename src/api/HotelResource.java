package api;

import Models.Customer;
import Models.IRoom;
import Models.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.NoSuchElementException;

public class HotelResource {

    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }
    public void createACustomer(String email, String firstName, String lastName) {
        CustomerService.getInstance().addCustomer(email, firstName, lastName);
    }
    public IRoom getRoom(String roomNumber) {
        return ReservationService.getInstance().getARoom(roomNumber);
    }
    public Reservation bookARoom (String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        try {
            Customer bookingCustomer = CustomerService.getInstance().getCustomer(customerEmail);
            return ReservationService.getInstance().reserveARoom(bookingCustomer, room, checkInDate, checkOutDate);
        } catch (NoSuchElementException ex) {
            System.out.println("No customer found with that email: " + customerEmail);
        }
        return null; //Better way?
    }
    public Collection<Reservation> getCustomerReservations(String customerEmail) {
        Customer bookingCustomer = CustomerService.getInstance().getCustomer(customerEmail);
        return ReservationService.getInstance().getCustomerReservation(bookingCustomer);
    }
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return ReservationService.getInstance().findRooms(checkIn, checkOut);
    }
}
