package service;

import Models.Customer;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;

public class CustomerService {

    private static CustomerService reference = new CustomerService();
    private Set<Customer> customerList;

    public void addCustomer(String email, String firstName, String lastName) {
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
