package api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
            String userChoice = scanner.nextLine();

            if (validOptions.contains(userChoice)) {
                if (userChoice == "1") {
                    // Find and reserve a room
                    /*
                    Make sure customer is logged in or offer to create account
                    Display available starting today or offer option to view another date
                    Once given option, offer to reserve a room
                    Take in reservation info,
                    Create reservation
                    Display successful reservation information
                     */
                    // verify log in
                    if (!loggedIn) {
                        System.out.println("Please create a customer account to login!");
                    } else {
                        System.out.println("Account found for user:");
                        System.out.println(currentCustomer);
                        System.out.println("Please enter a check-in and checkout date for your stay (yyyy-MM-dd)");
                        System.out.println("For example, 2023-01-01 2023-01-08");
                        String inputDates = scanner.nextLine();
                        String[] roomDates = inputDates.split("\\s+");
                        String checkIn = roomDates[1];
                        String checkOut = roomDates[2];
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
                        LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
                        LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
                        System.out.println("Below are the following room options. Select using the room number (101)");
                        String roomSelection = scanner.nextLine();
                        IRoom desiredRoom = ReservationService.getInstance().getARoom(roomSelection);
                        // Check existing reservations

                        // Book new reservation
                        Reservation newRez = ReservationService.getInstance().reserveARoom(currentCustomer, desiredRoom, checkInDate, checkOutDate);
                        System.out.println("Reservation successfully created!");
                        System.out.println(newRez);
                    }

                } else if (userChoice == "2"){
                    HotelResource.getInstance().getCustomerReservations(currentCustomer.email);
                } else if (userChoice == "3"){
                    currentCustomer = createUserAccount(scanner);
                } else if (userChoice == "4"){
                    Boolean isAdmin = adminLogin(scanner, adminPassword);
                    if (isAdmin) {
                        System.out.println("Admin logged in. Loading Admin Menu");
                        System.out.println("______________________");
                        AdminMenu.startAdminMenu();
                    } else {
                        System.out.println("Sorry, that admin password is incorrect.");
                    }
                } else if (userChoice == "5"){
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
    private static Customer createUserAccount(Scanner scanner) {
        System.out.println("Please enter a first name, last name, and email.");
        System.out.println("Ex: Joe Mama jmom@abc.com");
        String newCustomerDetails = scanner.nextLine();
        String[] contactInfo = newCustomerDetails.split("\\s+");
        String first = contactInfo[0];
        String last = contactInfo[1];
        String email = contactInfo[2];
        HotelResource.getInstance().createACustomer(first, last, email);
        System.out.println("New customer account successfully created!");
        return HotelResource.getInstance().getCustomer(email);
    }
    private static Boolean adminLogin(Scanner scanner, String adminPassword) {
        System.out.println("Please enter the admin password");
        String password = scanner.nextLine();
        if (password == adminPassword) {
            return true;
        } else {
            System.out.println("The admin password was incorrect. Please try again from the main menu.");
            return false;
        }
    }
}
