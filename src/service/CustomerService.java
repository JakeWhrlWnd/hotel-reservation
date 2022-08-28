package service;

import model.Customer;

import java.util.Collection;
import java.util.LinkedList;

/**
 * This creates the CustomerService Class.
 * Creates a LinkedList Collection of the Customers. It collects the customer information.
 * @author James Norris
 */
public class CustomerService {
    /**
     * Creates a static reference to the Collection
     */
    private static final Collection<Customer> customers = new LinkedList<>();

    /**
     * Adds the new customer to the LinkedList
     * @param email
     * @param firstName
     * @param lastName
     */
    public void addCustomer(String email, String firstName, String lastName) {
        customers.add(new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String customerEmail) {
        Customer theCustomer = null;
        for (Customer customer:customers) {
            if (customer.getEmail().equals(customerEmail)) theCustomer = customer;
        }
        return theCustomer;
    }

    public Collection<Customer> getAllCustomers() {
        return customers;
    }
}
