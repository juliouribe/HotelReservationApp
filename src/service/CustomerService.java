package service;

import Models.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class CustomerService {

    private static CustomerService instance = new CustomerService();
    private ArrayList<Customer> customerList;

    private CustomerService() {
        this.customerList = new ArrayList<Customer>();
    }
    public static CustomerService getInstance(){
        return instance;
    }


    public void addCustomer(String firstName, String lastName, String email) {
        Customer newCustomer = new Customer(firstName, lastName, email);
        customerList.add(newCustomer);
    }
    public Customer getCustomer(String customerEmail) {
        for (Customer customer : customerList) {
            if (customer.email == customerEmail) {
                return customer;
            } else {
                throw new NoSuchElementException("No customer found with that email!");
            }
        }
        return null; // Is there something better to return if customer not found?
    }
    public Collection<Customer> getAllCustomers() {
        return customerList;
    }
}
