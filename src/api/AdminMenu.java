package api;

import Models.Customer;
import Models.IRoom;
import Models.Room;
import Models.RoomType;

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
            String userChoice = scanner.nextLine();
            if (validOptions.contains(userChoice)) {
                if (userChoice == "1") {
                    printAllCustomers();
                } else if (userChoice == "2") {
                    printAllRooms();
                } else if (userChoice == "3") {
                    printAllReservations();
                } else if (userChoice == "4") {
                    createAndAddRoom(scanner);
                } else if (userChoice == "5") {
                    adminAppRunning = false;
                    System.out.println("Exiting the Admin Menu");
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
        ArrayList<Customer> allCustomers = AdminResource.getInstance().getAllCustomers().stream().collect(toCollection(ArrayList::new));
        for (Customer customer : allCustomers) {
            System.out.println(customer);
        }
    }
    private static void printAllRooms() {
        ArrayList<IRoom> allRooms = AdminResource.getInstance().getAllRooms().stream().collect(toCollection(ArrayList::new));
        for (IRoom room : allRooms) {
            System.out.println(room);
        }
    }
    private static void printAllReservations() {
        AdminResource.getInstance().getAllRooms();
    }
    private static void createAndAddRoom(Scanner scanner) {
        ArrayList<IRoom> newRooms = new ArrayList<IRoom>();
        System.out.println("Please enter a room number, price, room type(SINGLE/DOUBLE), and if it's free (true/false)");
        System.out.println("Ex: 101 55 SINGLE false");
        String roomDetails = scanner.nextLine();
        String[] roomInfo = roomDetails.split("\\s+");
        String roomNumber = roomInfo[0];
        Double price = Double.parseDouble(roomInfo[1]);
        String roomTypeInput = roomInfo[2];
        RoomType roomType = RoomType.SINGLE;
        if (roomTypeInput.toUpperCase() == "DOUBLE") {
            roomType = RoomType.DOUBLE;
        }
        Boolean isFree = Boolean.parseBoolean(roomInfo[3]);
        Room newRoom = new Room(roomNumber, price, roomType, isFree);
        System.out.println("New room successfully created:");
        System.out.println(newRoom);
        newRooms.add(newRoom);
        AdminResource.getInstance().addRoom(newRooms);
    }
}
