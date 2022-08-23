/** Tester for the models of the application.
 *
 * @author James Norris
 */
package model;

public class Driver {

    public static void main(String[] args) {
        // Customer customer = new Customer("first", "last", "j@domain.com");
        Customer customer = new Customer("first", "second", "email");
        System.out.println(customer);
    }
}
