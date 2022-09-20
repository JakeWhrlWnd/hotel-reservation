package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;

    private static final String REGEX = "^(.+)@(.+).com$";
    private static final String NAME_REGEX = "^[A-Z]('.-)[a-z]*$";

    public Customer(String firstName, String lastName, String email) {
        isValidName(firstName);
        this.firstName = firstName;

        isValidName(lastName);
        this.lastName = lastName;

        isValidEmail(email);
        this.email = email;
    }

    public String getFirstName() { return firstName; } // Getter for the User's first name

    public String getLastName() { return lastName; } // Getter for the User's last name

    public String getEmail() {
        return email;
    } // Getter for the User's email

    private void isValidName(String name) {
        Pattern namePattern = Pattern.compile(NAME_REGEX);
        boolean matches = namePattern.matcher(name).matches();
        if (!matches) {
            throw new IllegalArgumentException("Names must be at least one character, and begin with a capitol letter.");
        }
    }

    private void isValidEmail(String email) {
        Pattern pattern = Pattern.compile(REGEX);
        boolean matches = pattern.matcher(email).matches();
        if(!matches) {
            throw new IllegalArgumentException("Email does not match format name@domain.com");
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
