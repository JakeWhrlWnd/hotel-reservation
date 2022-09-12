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
    private static final CustomerService customerService = new CustomerService(); // static reference

    private CustomerService() {}

    public static CustomerService getInstance() {
        return customerService;
    }

    private static final Map<String, Customer> customers = new HashMap<>();

    public static void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        if (customers.containsKey(email)) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        customers.put(customer.getEmail(), customer);
    }

    public static Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public static Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
