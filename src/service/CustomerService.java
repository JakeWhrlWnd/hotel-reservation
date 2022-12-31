package service;

import model.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    /**
     * Singleton Pattern for CustomerService Class
     * Creates a static reference and a method to get the instance
     */
    private static final CustomerService customerService = new CustomerService(); // static reference
    private CustomerService() {}
    public static CustomerService getInstance() {
        return customerService;
    }

    private final Map<String, Customer> customers = new HashMap<>();

    /**
     * Adds the customer
     * @param customer - the customer
     */
    public void addCustomer(Customer customer) {
        customers.put(customer.getEmail(), customer);
    }

    /**
     * Gets the customer
     * @param customerEmail - Customer's email
     * @return customer
     */
    public Customer getCustomer(String customerEmail) { return customers.get(customerEmail); }

    /**
     * Gets all the customers
     * @return map of the customers
     */
    public Map<String, Customer> getAllCustomers() { return customers; }
}
