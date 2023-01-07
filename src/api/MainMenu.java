package api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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
            System.out.println("You selected " + userChoice);
            System.out.println();

            if (validOptions.contains(userChoice)) {

                if (userChoice.equals("1")) {
                    // Find and reserve a room
                    if (!loggedIn) {
                        System.out.println("Customer is not logged in. Please create an account to log in!");
                        continue;
                    }
                    System.out.println("Account found for user:");
                    System.out.println(currentCustomer);
                    try {
                        findAndReserveARoom(scanner, currentCustomer);
                    } catch (Exception ex) {
                        System.out.println(ex.getLocalizedMessage());
                        System.out.println("Something went wrong with finding or reserving a room. Try again.");
                    }
                } else if (userChoice.equals("2")) {
                    if (!loggedIn) {
                        System.out.println("Customer is not logged in. Please create an account to log in!");
                        continue;
                    }
                    try {
                        printCustomerReservations(currentCustomer);
                    } catch (NoSuchElementException ex) {
                        System.out.println(ex.getLocalizedMessage());
                        System.out.println("Something went wrong with finding customer with that email. Try again.");
                    }
                } else if (userChoice.equals("3")) {
                    try {
                        currentCustomer = createUserAccount(scanner);
                        loggedIn = true;
                    } catch (Exception ex) {
                        System.out.println(ex.getLocalizedMessage());
                        System.out.println("Something went wrong with creating customer. Try again.");
                    }
                } else if (userChoice.equals("4")) {
                    Boolean isAdmin = adminLogin(scanner, adminPassword);
                    if (isAdmin) {
                        System.out.println("Admin logged in. Loading Admin Menu");
                        System.out.println("______________________");
                        AdminMenu.startAdminMenu();
                    } else {
                        System.out.println("The admin password was incorrect. Taking you back to the main menu.");
                    }
                } else if (userChoice.equals("5")) {
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
                String checkIn = roomDates[0].strip();
                String checkOut = roomDates[1].strip();
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
    private static void printCustomerReservations(Customer customer) {
        System.out.println("All current reservations:");
        ArrayList<Reservation> allReservations = new ArrayList<>(ReservationService.getInstance().getCustomerReservation(customer));
        if (allReservations.size() > 0) {
            for (Reservation rez : allReservations) {
                System.out.println(rez);
            }
        } else {
            System.out.println("There are currently no reservations for this customer.");
        }
    }
    private static Customer createUserAccount(Scanner scanner) {
        System.out.println("Please enter a first name, last name, and email.");
        System.out.println("Ex: Jose Gonzalez jgon@abc.com");
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
        if (password.equals(adminPassword)) {
            return true;
        } else {
            return false;
        }
    }
}
