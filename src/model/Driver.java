package model;
/** Tester for the models of the application.
 *
 * @author James Norris
 */
public class Driver {

    public static void main(String[] args) {
        // Customer customer = new Customer("first", "last", "j@domain.com");
        Customer customer = new Customer("first", "second", "email");
        System.out.println(customer);
    }
}
