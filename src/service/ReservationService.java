package service;

import Models.*;

import java.time.LocalDate;

import java.util.*;

public class ReservationService {

    private static ReservationService instance = new ReservationService();
    public static ArrayList<Reservation> reservationList;
    public static ArrayList<IRoom> roomList;

    private ReservationService() {
        reservationList = new ArrayList<Reservation>();
        roomList = new ArrayList<IRoom>();
        Boolean notFree = false;
        // Create some single and double rooms
        // Create Single Rooms
        RoomType oneBed = RoomType.SINGLE;
        Double singlePrice = 100.0;
        for (int i = 1; i <= 8; i++) {
            String singleRoomNumber = "10" + i;
            Room singleRoom = new Room(singleRoomNumber, singlePrice, oneBed, notFree, false);
            roomList.add(singleRoom);
        }
        // Create Double Rooms
        Double doublePrice = 150.0;
        RoomType twoBeds = RoomType.DOUBLE;
        for (int i = 1; i <= 4; i++) {
            String doubleRoomNumber = "20" + i;
            Room doubleRoom = new Room(doubleRoomNumber, doublePrice, twoBeds, notFree, false);
            roomList.add(doubleRoom);
        }
        Room pineappleRoom = new Room("301", 500.0, oneBed, notFree, true);
        roomList.add(pineappleRoom);
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
            if (iRoom.getRoomNumber().equals(roomId)) {
                return iRoom;
            }
        }
        throw new NoSuchElementException("No room found with that room number!");
    }
    public Reservation reserveARoom(Customer customer, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        Reservation newRez = new Reservation(customer, room, checkInDate, checkOutDate);
        // Prevent double booking of the same room and make sure this reservation doesn't already exist.
        for (Reservation rez : reservationList) {
            // If the room number isn't the same, move onto the next reservation.
            if (newRez.room.getRoomNumber() != rez.room.getRoomNumber()) {continue;}
            Boolean dateOverlap = isThereOverlap(rez.checkInDate, rez.checkOutDate, newRez.checkInDate, newRez.checkOutDate);
            if (dateOverlap) {
                if (rez.customer.email.equals(newRez.customer.email)) {
                    System.out.println("A booking for this room and customer already exists with similar dates!");
                } else {
                    System.out.println("Sorry but that room is already booked during those dates!");
                }
                return rez;
            }
        }
        reservationList.add(newRez);
        System.out.println("Room successfully booked for " + customer + " at room " + room.getRoomNumber() + "!");
        return newRez;
    }
    public Collection<IRoom> findRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<IRoom>(roomList);
        // Iterate over existing reservations and remove any rooms that are taken for given date.
        for (Reservation rez : reservationList) {
            // Remove room if dates overlap with an existing reservation.
            if (isThereOverlap(rez.checkInDate, rez.checkOutDate, checkInDate, checkOutDate)){
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
        if (reservationList.size() > 0) {
            for (Reservation rez : reservationList) {
                System.out.println(rez);
            }
        } else {
            System.out.println("There are no existing reservations.");
        }
    }
    public boolean isThereOverlap(LocalDate oldCheckIn, LocalDate oldCheckOut, LocalDate newCheckIn, LocalDate newCheckout) {
        if ( (oldCheckOut.compareTo(newCheckIn) > 0) // Old checkout after new checkIn
                && (oldCheckOut.compareTo(newCheckout) <= 0) ) { // Old checkout before or equal to new checkout
            return true;
            // If old rez checkIn date is before the new rez checkout date and after or equal to the new rez checkIn date.
        } else if ((oldCheckIn.compareTo(newCheckout) < 0) // old checkIn before new check out.
                && (oldCheckIn.compareTo(newCheckIn) >= 0)) { // old checkIn after or equal to new checkIn.
            return true;
            // If new rez is in the middle of the existing reservation.
        } else if ((oldCheckIn.compareTo(newCheckIn) <= 0 ) // Old checkIn before new checkIn
                && (oldCheckOut.compareTo(newCheckout) >= 0)) { // Old checkout after new checkout
            return true;
        }
        return false;
    }
}
