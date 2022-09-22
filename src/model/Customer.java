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

    private static final String REGEX = "^(.+)@(.+).com$"; // Constant Regular expression used to validate email
    private static final String NAME_REGEX = "^[A-Z]('.-)[a-z]*$"; // Constant Regular expression used to validate first and last names

    /**
     * Constructor for the Customer Class
     * Creates a customer with a first name, last name, and an email
     * @param firstName string, the customer's first name
     * @param lastName  string, the customer's last name
     * @param email     string, the customer's email
     */
    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
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
    public void setFirstName(String firstName) {
        Pattern namePattern = Pattern.compile(NAME_REGEX);
        boolean matches = namePattern.matcher(firstName).matches();
        if (!matches) {
            throw new IllegalArgumentException("Names must be at least one character, and begin with a capital letter.");
        } else {
            this.firstName = firstName;
        }
    }

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
    public void setLastName(String lastName) {
        Pattern namePattern = Pattern.compile(NAME_REGEX);
        boolean matches = namePattern.matcher(lastName).matches();
        if (!matches) {
            throw new IllegalArgumentException("Names must be at least one character, and begin with a capital letter.");
        } else {
            this.lastName = lastName;
        }
    }

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
    public void setEmail(String email) {
        Pattern pattern = Pattern.compile(REGEX);
        boolean matches = pattern.matcher(email).matches();
        if (!matches) {
            throw new IllegalArgumentException("Email does not match format name@domain.com");
        } else {
            this.email = email;
        }
    }

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