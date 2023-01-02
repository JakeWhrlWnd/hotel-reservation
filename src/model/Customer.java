package model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Customer Class
 * Creates the Customer class
 * @author James Norris
 */
public class Customer {
    private String firstName; // Represents the Customer's firstName
    private String lastName; // Represents the Customer's lastName
    private String email; // Represents the Customer's email

    /**
     * Constructor for the Customer Class
     * Creates a customer with a first name, last name, and an email
     * @param firstName string, the customer's first name
     * @param lastName  string, the customer's last name
     * @param email     string, the customer's email
     */
    public Customer(String firstName, String lastName, String email) {
        // Constant Regular expression used to validate email
        String EMAIL_REGEX = "^(.+)@(.+).com$";
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        this.firstName = firstName;
        this.lastName = lastName;
        if (!emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException ("Email is not valid. Format must be name@domain.com");
        }
        this.email = email;
    }

    /**
     * Getter for the firstName variable
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the firstName variable
     * Uses a Regex to validate the format of the variable value
     * The value of firstName should begin with a capital letter and should have at least one character
     *
     * @param firstName string
     */
    public void setFirstName(String firstName) {this.firstName = firstName;}

    /**
     * Getter for the lastName variable
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for the lastName variable
     * Uses a Regex to validate the format of the variable value
     * The value of lastName should begin with a capital letter and should have at least one character
     *
     * @param lastName string
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     * Getter for the email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    } // Getter for the User's email

    /**
     * Setter for the email input
     * Uses a Regex to validate the format of the variable value
     * The value of email should be name@domain.com
     * It should begin with a 2 strings separated by an @ symbol and end with .com
     *
     * @param email string
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Overrides the toString method
     * @return A better description of the Customer attributes
     */
    @Override
    public String toString() {
        return "First name: " + firstName
                + "\nLast name: " + lastName
                + "\nEmail: " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}