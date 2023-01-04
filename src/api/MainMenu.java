package api;

import java.util.ArrayList;
import java.util.Scanner;

import Models.Customer;

import javax.sound.midi.SysexMessage;

public class MainMenu {
    public static void main(String[] args) {
        Customer currentCustomer = new Customer("Keren", "Zendejas", "kerenz@gmail.com");
        Scanner scanner = new Scanner(System.in);
        Boolean runApp = true;
        Boolean loggedIn = false;
        Boolean isAdmin = false;
        String adminPassword = "supersecretpassword";
        ArrayList<String> validOptions = new ArrayList<String>();
        validOptions.add("1");
        validOptions.add("2");
        validOptions.add("3");
        validOptions.add("4");
        validOptions.add("5");

        while (runApp) {
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
                } else if (userChoice == "2"){
                    HotelResource.getInstance().getCustomerReservations(currentCustomer.email);
                } else if (userChoice == "3"){
                    createUserAccount(scanner);
                } else if (userChoice == "4"){
                    isAdmin = adminLogin(scanner, adminPassword);
                    if (isAdmin) {
                        System.out.println("Admin logged in");
                        // call admin menu function.
                    } else {
                        System.out.println("Sorry, that admin password is incorrect.");
                    }
                } else if (userChoice == "5"){
                    runApp = false;
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
    private static void createUserAccount(Scanner scanner) {
        System.out.println("Please enter a first name, last name, and email.");
        System.out.println("Ex: Joe Mama jmom@abc.com");
        String newCustomerDetails = scanner.nextLine();
        String[] contactInfo = newCustomerDetails.split("\\s+");
        String first = contactInfo[0];
        String last = contactInfo[1];
        String email = contactInfo[2];
        HotelResource.getInstance().createACustomer(first, last, email);
        System.out.println("New customer account successfully created!");
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
