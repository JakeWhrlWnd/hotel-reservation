package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;

    private static final String REGEX = "^(.+)@(.+)\\.com$";

    public Customer(String firstName, String lastName, String email) {
        isValidEmail(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() { return firstName; } // Getter for the User's first name

    public String getLastName() { return lastName; } // Getter for the User's last name

    public String getEmail() {
        return email;
    } // Getter for the User's email

    private void isValidEmail(String email) {
        Pattern pattern = Pattern.compile(REGEX);
        boolean matches = pattern.matcher(email).matches();
        if(!matches) {
            throw new IllegalArgumentException("Email does not match format MM/DD/YYYY");
        }
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

    @Override
    public String toString() {
        return "First name: " + firstName + "\nLast name: " + lastName + "\nEmail: " + email;
    }
}
