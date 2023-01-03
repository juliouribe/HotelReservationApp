package api;

import java.util.ArrayList;
import java.util.Scanner;

import Models.Customer;
import api.HotelResource;

public class MainMenu {
    // Set up scanner
    public static void main(String[] args) {
        Customer currentCustomer = new Customer("Keren", "Zendejas", "kerenz@gmail.com");
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("______________________");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservation");
            System.out.println("3. Create an Account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("______________________");
            System.out.println("Please select a number for the menu option");
            String userChoice = scanner.nextLine();

            ArrayList<String> validOptions = new ArrayList<String>();
            validOptions.add("1");
            validOptions.add("2");
            validOptions.add("3");
            validOptions.add("4");
            validOptions.add("5");

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
                    // See my reservation
                    HotelResource.getInstance().getCustomerReservations(currentCustomer.email);

                } else if (userChoice == "3"){
                    // Create an account
                    System.out.println("Please enter a first name, last name, and email.");
                    System.out.println("Ex: Joe Mama jmom@abc.com");
                    String newCustomerDetails = scanner.nextLine();
                    String[] contactInfo = newCustomerDetails.split("\\s+");
                    String first = contactInfo[0];
                    String last = contactInfo[1];
                    String email = contactInfo[2];
                    HotelResource.getInstance().createACustomer(first, last, email);
                    System.out.println("New customer account successfully created!");

                } else if (userChoice == "4"){
                    // Admin


                } else if (userChoice == "5"){
                    // Exit
                }
            }

        } catch (Exception ex) {
            ex.getLocalizedMessage();
        } finally {
            scanner.close();
        }

    }




}
