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
        for (int i = 1; i <= 8; i++) {
            String singleRoomNumber = "10" + i;
            Double singlePrice = 100.0;
            RoomType oneBed = RoomType.SINGLE;
            Room singleRoom = new Room(singleRoomNumber, singlePrice, oneBed, notFree);
            roomList.add(singleRoom);
        }
        // Create Double Rooms
        for (int i = 1; i <= 4; i++) {
            String doubleRoomNumber = "20" + i;
            Double doublePrice = 150.0;
            RoomType twoBeds = RoomType.DOUBLE;
            Room doubleRoom = new Room(doubleRoomNumber, doublePrice, twoBeds, notFree);
            roomList.add(doubleRoom);
        }
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
    public Reservation reserveARoom(Customer customer, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        Reservation newRez = new Reservation(customer, room, checkInDate, checkOutDate);
        // Prevent double booking and make sure this reservation doesn't already exist.
        for (Reservation rez : reservationList) {
            if (rez == newRez) {
                System.out.println("This booking already exists.");
                return rez;
            } else if (checkInDate.compareTo(rez.checkOutDate) > 0 || checkOutDate.compareTo(rez.checkInDate) > 0){
                System.out.println("Sorry but that room is already booked during those dates!");
                return rez;
            }
        }

        reservationList.add(newRez);
        System.out.println("Room successfully booked for " + customer + " at " + room.getRoomNumber() + "!");
        return newRez;
    }
    public Collection<IRoom> findRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<IRoom>(roomList);
        // Iterate over existing reservations and remove any rooms that are taken for given date.
        for (Reservation rez : reservationList) {
            // Remove room if dates overlap with an existing reservation.
            // Returns true if date 1 is after date 2.
            if (checkInDate.compareTo(rez.checkOutDate) > 0 || checkOutDate.compareTo(rez.checkInDate) > 0){
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
