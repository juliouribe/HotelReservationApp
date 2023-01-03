package api;

import java.util.Scanner;

public class MainMenu {
    // Set up scanner
    public static void main(String[] args) {

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

        } catch (Exception ex) {
            ex.getLocalizedMessage();
        } finally {
            scanner.close();
        }

    }



    // Find and reserve a room


    // See my reservation

    // Create an account

    // Admin

    // Exit
}
