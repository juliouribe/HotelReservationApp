package service;

import Models.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;


public class ServiceTester {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        // Check that there are 13 rooms available and print them out.
        Collection<IRoom> allRooms = new ArrayList<IRoom>(ReservationService.getInstance().getAllRooms());
        System.out.println(allRooms.size() + " rooms available.");
        for (IRoom room : allRooms) {
            System.out.println(room);
        }
        System.out.println();

        IRoom desiredRoom = ReservationService.getInstance().getARoom("101");
        CustomerService.getInstance().addCustomer("Julio", "Uribe", "uribejulioc@gmail.com");
        CustomerService.getInstance().addCustomer("Juan", "Gonzalez", "jgon@aol.com");
        CustomerService.getInstance().addCustomer("Jules", "Rules", "julesrulez@mybusiness.com");
        CustomerService.getInstance().addCustomer("Julian", "Uribe", "uribejulioc@gmail.com");
        Customer newCustomer = CustomerService.getInstance().getCustomer("uribejulioc@gmail.com");
        Collection<Customer> allCustomers = new ArrayList<Customer>(CustomerService.getInstance().getAllCustomers());
        System.out.println("There are a total of " + allCustomers.size() + " customers.");
        LocalDate checkIn = LocalDate.parse("2023-01-01", formatter);
        LocalDate checkOut = LocalDate.parse("2023-01-08", formatter);
        Reservation rez = ReservationService.getInstance().reserveARoom(newCustomer, desiredRoom, checkIn, checkOut);
        System.out.println();

        // See available rooms
        Collection<IRoom> availableRooms = ReservationService.getInstance().findRooms(checkIn, checkOut);
        System.out.println(availableRooms.size() + " rooms are still available for " + checkIn + " to " + checkOut);
        System.out.println();

        // Double book with same customer
        ReservationService.getInstance().reserveARoom(newCustomer, desiredRoom, checkIn, checkOut);
        // Double book with another customer
        Customer secondCustomer = CustomerService.getInstance().getCustomer("julesrulez@mybusiness.com");
        ReservationService.getInstance().reserveARoom(secondCustomer, desiredRoom, checkIn, checkOut);

        System.out.println("Comparing reservations for customer Julio. Expected value is true:");
        Collection<Reservation> julioRez = new ArrayList<>() {{add(rez);}};
        System.out.println(julioRez.equals(ReservationService.getInstance().getCustomerReservation(newCustomer)));
        System.out.println();

        ReservationService.getInstance().printAllReservation();
    }
}
