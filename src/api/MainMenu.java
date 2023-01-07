package api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;

import Models.Customer;
import Models.IRoom;
import Models.Reservation;
import service.ReservationService;

public class MainMenu {
    public static void main(String[] args) {
        Customer currentCustomer = new Customer("place", "holder", "abc123@gmail.com");
        Scanner scanner = new Scanner(System.in);
        Boolean appRunning = true;
        Boolean loggedIn = false;
        String adminPassword = "supersecretpassword";
        ArrayList<String> validOptions = new ArrayList<String>();
        validOptions.add("1");
        validOptions.add("2");
        validOptions.add("3");
        validOptions.add("4");
        validOptions.add("5");

        while (appRunning) {
            printMainMenu();
            String userChoice = scanner.nextLine().strip();
            System.out.println(userChoice);

            if (validOptions.contains(userChoice)) {

                if (userChoice.equals("1")) {
                    // Find and reserve a room
                    if (!loggedIn) {
                        System.out.println("Please create a customer account to login!");
                    } else {
                        System.out.println("Account found for user:");
                        System.out.println(currentCustomer);
                        findAndReserveARoom(scanner, currentCustomer);
                    }
                } else if (userChoice.equals("2")) {
                    HotelResource.getInstance().getCustomerReservations(currentCustomer.email);
                } else if (userChoice.equals("3")) {
                    currentCustomer = createUserAccount(scanner);
                } else if (userChoice.equals("4")) {
                    Boolean isAdmin = adminLogin(scanner, adminPassword);
                    if (isAdmin) {
                        System.out.println("Admin logged in. Loading Admin Menu");
                        System.out.println("______________________");
                        AdminMenu.startAdminMenu();
                    } else {
                        System.out.println("Sorry, that admin password is incorrect.");
                    }
                } else if (userChoice.equals("5")) {
                    System.out.println("User choice passes 5 check");
                    appRunning = false;
                    System.out.println("Exiting the Main Menu");
                }
            } else {
                // If no valid choice enter, re-run loop.
                System.out.println("Please enter a valid choice of 1-5.");
                System.out.println();
            }
        }
        scanner.close();
    }
    private static void printMainMenu() {
        System.out.println("______________________");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservation");
        System.out.println("3. Create an Account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("______________________");
        System.out.println("Please select a number for the menu option");
    }
    private static void findAndReserveARoom(Scanner scanner, Customer currentCustomer) {
        Boolean viewingRooms = true;
        while (viewingRooms) {
            try {
                // Take in a check-in and checkout date.
                System.out.println("Please enter a check-in and checkout date for your stay (yyyy-MM-dd)");
                System.out.println("For example, 2023-01-01 2023-01-08");
                String inputDates = scanner.nextLine();
                String[] roomDates = inputDates.split("\\s+");
                String checkIn = roomDates[1].strip();
                String checkOut = roomDates[2].strip();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
                LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
                LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
                System.out.println("Below are the following room options. Select using the room number (101)");
                Collection<IRoom> availableRooms = ReservationService.getInstance().findRooms(checkInDate, checkOutDate);
                // Show available rooms.
                for (IRoom room : availableRooms) {
                    System.out.println(room);
                }
                String roomSelection = scanner.nextLine().strip();
                IRoom desiredRoom = ReservationService.getInstance().getARoom(roomSelection);
                // Book new reservation
                Reservation newRez = ReservationService.getInstance().reserveARoom(currentCustomer, desiredRoom, checkInDate, checkOutDate);
                viewingRooms = false;
            } catch (DateTimeParseException ex) {
                System.out.println("Sorry, please re-enter the date using the correct format (yyyy-MM-dd)");
                System.out.println();
            }
        }
    }
    private static Customer createUserAccount(Scanner scanner) {
        System.out.println("Please enter a first name, last name, and email.");
        System.out.println("Ex: Joe Mama jmom@abc.com");
        String newCustomerDetails = scanner.nextLine();
        String[] contactInfo = newCustomerDetails.split("\\s+");
        String first = contactInfo[0].strip();
        String last = contactInfo[1].strip();
        String email = contactInfo[2].strip();
        HotelResource.getInstance().createACustomer(first, last, email);
        System.out.println("New customer account successfully created!");
        return HotelResource.getInstance().getCustomer(email);
    }
    private static Boolean adminLogin(Scanner scanner, String adminPassword) {
        System.out.println("Please enter the admin password");
        String password = scanner.nextLine().strip();
        if (password == adminPassword) {
            return true;
        } else {
            System.out.println("The admin password was incorrect. Please try again from the main menu.");
            return false;
        }
    }
}
