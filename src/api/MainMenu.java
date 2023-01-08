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
        runMainMenu();
    }
    public static void runMainMenu() {
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
                switch (userChoice) {
                    case "1":
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
                        break;
                    case "2":
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
                        break;
                    case "3":
                        try {
                            currentCustomer = createUserAccount(scanner);
                            loggedIn = true;
                        } catch (Exception ex) {
                            System.out.println(ex.getLocalizedMessage());
                            System.out.println("Something went wrong with creating customer. Try again.");
                        }
                        break;
                    case "4":
                        Boolean isAdmin = adminLogin(scanner, adminPassword);
                        if (isAdmin) {
                            System.out.println("Admin logged in. Loading Admin Menu");
                            System.out.println("______________________");
                            AdminMenu.startAdminMenu();
                        } else {
                            System.out.println("The admin password was incorrect. Taking you back to the main menu.");
                        }
                        break;
                    case "5":
                        appRunning = false;
                        System.out.println("Exiting the Main Menu");
                        break;
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
                System.out.println("Below are the following room options:");
                Collection<IRoom> availableRooms = ReservationService.getInstance().findRooms(checkInDate, checkOutDate);
                // Show available rooms.
                for (IRoom room : availableRooms) {
                    System.out.println(room);
                }
                System.out.println("Select using the room number, ex: 101");
                String roomSelection = scanner.nextLine().strip();
                IRoom desiredRoom = ReservationService.getInstance().getARoom(roomSelection);
                if (desiredRoom.isPineappleRoom()) {
                    bookPineappleRoom();
                    break;
                }
                // Book new reservation
                ReservationService.getInstance().reserveARoom(currentCustomer, desiredRoom, checkInDate, checkOutDate);
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
    private static void bookPineappleRoom() {
        System.out.println("Sorry, the Pineapple Suite is booked until at least Wednesday.");
        System.out.println("Currently there is a wonderful German couple in there and the cleaners will need at least 24 hours");
        System.out.println("to clean the room so I'm afraid we won't be able to get you in there.");
        System.out.println("The Presidential Suite you are in has a great view of the ocean.");
        System.out.println("...");
        System.out.println("You complain about this to your wife at breakfast, by the pool, while she's contemplating her career.");
        System.out.println("You call your mother to verify you had booked the Pineapple Suite and she emails you the receipt.");
        System.out.println("You approach the front desk man during breakfast, at dinner, and during the dinner show.");
        System.out.println("You hunt down the front desk man in his office and refuse to take no for an answer.");
        System.out.println("Your wife is flustered by your obsession of the Pineapple suite. Your marriage is strained.");
        System.out.println("The front desk man has your mother fly in to join you and your fiance on your honey moon.");
        System.out.println("...");
        System.out.println("It's a dead end, please book a different room.");
    }
}
