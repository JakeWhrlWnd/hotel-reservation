package model;

import java.util.regex.Pattern;

public record Customer(String firstName, String lastName, String email) {

    public Customer(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        String REGEX = "^(.+)@(.+)[.com]$";
        Pattern EMAIL = Pattern.compile(REGEX);
        if (!EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("Email is invalid.");
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return firstName.equals(customer.firstName) && lastName.equals(customer.lastName) && email.equals(customer.email);
    }

    @Override
    public String toString() {
        return "First name: " + firstName() + "\nLast name: " + lastName() + "\nEmail: " + email();
    }
}
