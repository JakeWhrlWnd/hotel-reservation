package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    /**
     * Singleton Pattern for CustomerService Class
     * Creates a static reference and a method to get the instance
     */
    private static CustomerService customerService; // static reference
    private CustomerService() {} // constructor
    public static CustomerService getInstance() { // static method to create instance
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    /**
     * Creates an object of {@link #customers} that will store String keys and Customer values
     */
    private static final Map<String, Customer> customers = new HashMap<>();

    /**
     * Creates a new Customer
     * @param email string, customer email
     * @param firstName string, customer first name
     * @param lastName string, customer last name
     */
    public void addCustomer(String email, String firstName, String lastName) {
        customers.put(email, new Customer(firstName, lastName, email));
    }

    /**
     * Returns the customer created with the email entered
     * @param customerEmail string, customers' email
     * @return customer using the email provided
     */
    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    /**
     * Returns all registered users
     * @return collection of customers
     */
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
