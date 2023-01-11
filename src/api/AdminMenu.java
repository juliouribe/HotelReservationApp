package api;

import Models.Customer;
import Models.IRoom;
import Models.Room;
import Models.RoomType;
import service.ReservationService;

import java.util.*;

import static java.util.stream.Collectors.*;

public class AdminMenu {
    public static void main(String[] args) {
        startAdminMenu();
    }
    public static void startAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        Boolean adminAppRunning = true;
        ArrayList<String> validOptions = new ArrayList<String>();
        validOptions.add("1");
        validOptions.add("2");
        validOptions.add("3");
        validOptions.add("4");
        validOptions.add("5");

        while (adminAppRunning) {
            printAdminMenu();
            String userChoice = scanner.nextLine().strip();
            if (validOptions.contains(userChoice)) {
                switch (userChoice) {
                    case "1":
                        printAllCustomers();
                        break;
                    case "2":
                        printAllRooms();
                        break;
                    case "3":
                        printAllReservations();
                        break;
                    case "4":
                        try {
                            createAndAddRoom(scanner);
                        } catch (Exception ex) {
                            System.out.println(ex.getLocalizedMessage());
                            System.out.println("Something went wrong with creating or adding a room. Try again.");
                        }
                        break;
                    case "5":
                        adminAppRunning = false;
                        System.out.println("Exiting the Admin Menu");
                        break;
                }
            } else {
                System.out.println("Please enter a valid choice of 1-5.");
                System.out.println();
            }
        }
    }
    private static void printAdminMenu() {
        System.out.println("______________________");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("______________________");
        System.out.println("Please select a number for the menu option");
    }
    private static void printAllCustomers() {
        System.out.println("All customers:");
        ArrayList<Customer> allCustomers = new ArrayList<>(AdminResource.getInstance().getAllCustomers());
        if (allCustomers.size() > 0) {
            for (Customer customer : allCustomers) {
                System.out.println(customer);
            }
        } else {
            System.out.println("There are no current customers to display.");
        }
    }
    private static void printAllRooms() {
        System.out.println("All current rooms:");
        ArrayList<IRoom> allRooms = new ArrayList<>(AdminResource.getInstance().getAllRooms());
        if (allRooms.size() > 0) {
            for (IRoom room : allRooms) {
                System.out.println(room);
            }
        } else {
            System.out.println("There are currently no rooms to display.");
        }
    }
    private static void printAllReservations() {
        AdminResource.getInstance().displayAllReservations();
    }
    private static void createAndAddRoom(Scanner scanner) {
        ArrayList<IRoom> newRooms = new ArrayList<IRoom>();
        System.out.println("Please enter a room number, price, room type(SINGLE/DOUBLE), and if it's free (true/false)");
        System.out.println("Ex: 101 55 SINGLE false");
        String roomDetails = scanner.nextLine();
        String[] roomInfo = roomDetails.split("\\s+");
        String roomNumber = roomInfo[0].strip();
        // Verify that an integer is given.
        try {
            Integer.parseInt(roomNumber);
        } catch (NumberFormatException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("That is not a valid room number. Please try again.");
            return;
        }
        // Make sure room number isn't already taken.
        for (IRoom room : ReservationService.roomList) {
            if (room.getRoomNumber().equals(roomNumber)) {
                System.out.println("That room number is already taken. Please try again with a different room number.");
                return;
            }
        }
        Double price = Double.parseDouble(roomInfo[1]);
        String roomTypeInput = roomInfo[2].strip();
        // Use default SINGLE or if user enters DOUBLE then change it. If there is a typo we will default to single.
        RoomType roomType = RoomType.SINGLE;
        if (roomTypeInput.toUpperCase() == "DOUBLE") {
            roomType = RoomType.DOUBLE;
        }
        Boolean isFree = Boolean.parseBoolean(roomInfo[3]);
        Room newRoom = new Room(roomNumber, price, roomType, isFree, false);
        System.out.println("New room successfully created:");
        System.out.println(newRoom);
        newRooms.add(newRoom);
        AdminResource.getInstance().addRoom(newRooms);
    }
}
