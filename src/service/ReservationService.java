package service;

import Models.Customer;
import Models.IRoom;
import Models.Reservation;

import java.util.*;

public class ReservationService {

    private static ReservationService instance = new ReservationService();
    private ArrayList<Reservation> reservationList;
    private ArrayList<IRoom> roomList;

    private ReservationService() {
        this.reservationList = new ArrayList<Reservation>();
        this.roomList = new ArrayList<IRoom>();
    }
    public static ReservationService getInstance() {
        return instance;
    }

    public Collection<IRoom> getAllRooms() {
        return roomList;
    }
    public void addRoom(IRoom room) {
        roomList.add(room);
    }
    public IRoom getARoom(String roomId) {
        for (IRoom iRoom : roomList) {
            if (iRoom.getRoomNumber() == roomId) {
                return iRoom;
            } else {
                throw new NoSuchElementException("No room found with that room number!");
            }
        }
        return null; // Is there something better to return if rom not found?
    }
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        // Add some logic to prevent double booking a room.
        // Check that this reservation doesn't already exist.
        Reservation newRez = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationList.add(newRez);
        System.out.println("Room successfully booked for " + customer + " at " + room.getRoomNumber() + "!");
        return newRez;
    }
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<IRoom>(roomList);
        // Iterate over existing reservations and remove any rooms that are taken for given date.
        for (Reservation rez : reservationList) {
            if (checkInDate.compareTo(rez.checkOutDate) > 0 ){
                availableRooms.removeIf(room -> room == rez.room);
            }
        }
        return availableRooms;
    }
    public Collection<Reservation> getCustomerReservation(Customer customer) {
        Collection<Reservation> customerReservations = new ArrayList<Reservation>();
        for (Reservation rez : reservationList) {
            if (rez.customer == customer) {
                customerReservations.add(rez);
            }
        }
        return customerReservations;
    }
    public void printAllReservation() {
        for (Reservation rez : reservationList) {
            System.out.println(rez);
        }
    }
}
