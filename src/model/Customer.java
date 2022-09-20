package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;

    private static final String REGEX = "^(.+)@(.+).com$";
    private static final String NAME_REGEX = "^[A-Z]('.-)[a-z]*$";

    public Customer(final String firstName, final String lastName, final String email) {
        isValidName(firstName);
        this.firstName = firstName;

        isValidName(lastName);
        this.lastName = lastName;

        isValidEmail(email);
        this.email = email;
    }

    public String getEmail() { return email; } // Getter for the User's email

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
        return Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "First name: " + firstName + "\nLast name: " + lastName + "\nEmail: " + email;
    }
}
