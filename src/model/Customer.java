package model;

import java.util.regex.Pattern;

/**
 * Creates the Customer Class.
 * Gets the Customer information. Uses a Regex to validate the user's email.
 * @author James Norris
 */
public class Customer {

    private String firstName;
    private String lastName;
    private String email;

    /**
     * Constructor for Customer Class
     * @param firstName string, Customer's first name
     * @param lastName string, Customer's last name
     * @param email string, Customer's email
     */
    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        final String emailRegex = "^(.+)@(.+)(.)(.+)$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if(!emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Email is invalid");
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
        return "First name: " + firstName
                + "\nLast name: " + lastName
                + "\nEmail: " + email;
    }
}
