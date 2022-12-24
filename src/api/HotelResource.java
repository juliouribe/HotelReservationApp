package api;

import Models.Customer;
import Models.IRoom;
import service.CustomerService;
import service.ReservationService;

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
}
