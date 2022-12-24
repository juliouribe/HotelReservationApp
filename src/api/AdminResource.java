package api;

import Models.Customer;
import Models.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static AdminResource instance = new AdminResource();
    public static AdminResource getInstance(){
        return instance;
    }
    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }
    public void addRoom(List<IRoom> rooms) {
        for (IRoom iRoom : rooms) {
            ReservationService.getInstance().addRoom(iRoom);
        }
    }
    public Collection<IRoom> getAllRooms() {
        return ReservationService.getInstance().getAllRooms();
    }
    public Collection<Customer> getAllCustomers() {
        return CustomerService.getInstance().getAllCustomers();
    }
    public void displayAllReservations() {
        ReservationService.getInstance().printAllReservation();
    }
}
