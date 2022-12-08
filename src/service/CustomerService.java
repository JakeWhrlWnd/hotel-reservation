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

    private final Map<String, Customer> customers = new HashMap<>();

    public void addCustomer(String firstName, String lastName, String email) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public Customer getCustomer(String customerEmail) { return customers.get(customerEmail); }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
