package service;

import model.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    /**
     * Singleton Pattern for CustomerService Class
     * Creates a static reference and a method to get the instance
     */
    private static CustomerService customerService; // static reference
    private CustomerService() {}
    public static CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    private final Map<String, Customer> customers = new HashMap<>();

    public void addCustomer(Customer customer) { customers.put(customer.getEmail(), customer); }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Map<String, Customer> getAllCustomers() {
        return customers;
    }
}
