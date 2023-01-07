package api;

import Models.Customer;
import Models.IRoom;
import Models.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.time.LocalDate;
import java.util.NoSuchElementException;

public class HotelResource {

    private static HotelResource instance = new HotelResource();
    public static HotelResource getInstance(){
        return instance;
    }

    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }
    public void createACustomer(String email, String firstName, String lastName) {
        CustomerService.getInstance().addCustomer(email, firstName, lastName);
    }
    public IRoom getRoom(String roomNumber) {
        return ReservationService.getInstance().getARoom(roomNumber);
    }
    public Reservation bookARoom (String customerEmail, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
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
    public Collection<IRoom> findARoom(LocalDate checkIn, LocalDate checkOut) {
        return ReservationService.getInstance().findRooms(checkIn, checkOut);
    }
}
