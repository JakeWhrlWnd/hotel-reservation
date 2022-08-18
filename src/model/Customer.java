/** Creates the Customer class. Defines the first name, last name, and email.
 *  Uses a Regex to test for a valid email.
 *
 * @JamesNorris
 *
 */
package model;

import java.util.regex.Pattern;

public class Customer {

    private String firstName;
    private String lastName;
    private String email;

    private String emailRegex = "^(.+)@(.+).(.+)$";
    private Pattern emailPattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
