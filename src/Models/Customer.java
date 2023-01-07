package Models;

import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    public String email;

    public Customer(String firstName, String lastName, String email) {
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (pattern.matcher(email).matches()){
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email must use the correct format (i.e., name@domain.com" + email);
        }
    }

    @Override
    public String toString() {
        return "Customer " + firstName + " " + lastName + " , Email: " + email;
    }
}
