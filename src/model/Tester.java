package model;

/**
 * Tester Class
 * Allows for testing of the implementation of the Customer Class.
 * @author James Norris
 */
public class Tester {
    public static void main(String[] args) {
        Customer customer = new Customer("first", "second", "j@gmail.com");
        System.out.println(customer);
        Customer customer1 = new Customer("John", "Smith", "email");
        System.out.println(customer1);
    }
}
