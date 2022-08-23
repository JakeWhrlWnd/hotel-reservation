package model;
/** Creates the Customer class. Defines the first name, last name, and email.
 *  Uses a Regex to test for a valid email.
 *
 * @author James Norris
 *
 */
import java.util.regex.Pattern;

public class Customer {

    private String firstName;
    private String lastName;
    private String email;

    private final String emailRegex = "^(.+)@(.+).(.+)$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        try {
            if (pattern.matcher(email).matches()) {
                this.email = email;
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("Invalid email format.");
        }
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

    @Override
    public String toString() {
        return "First Name: " + firstName
                + "\nLast Name: " + lastName
                + "\nEmail: " + email;
    }
}
