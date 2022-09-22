package model;

import java.util.Date;
import java.util.Objects;

/**
 * Reservation Class
 * Creates the Reservation class
 * @author James Norris
 */
public class Reservation {
    private Customer customer; // Represents a customer
    private IRoom room; // Represents the customer's room
    private Date checkInDate; // Represents the customer's check-in date
    private Date checkOutDate; // Represents the customer's check-out date

    /**
     * Constructor for the Reservation Class
     * Creates a reservation with customer info, room info, check-in date, check-out date
     * @param customer Customer, the customer
     * @param room IRoom, the room
     * @param checkInDate Date, the check-in date
     * @param checkOutDate Date, the check-out date
     */
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    /**
     * Getter for the customer
     * @return customer
     */
    public Customer getCustomer() { return customer; }

    /**
     * Setter for the customer
     * Creates a new instance of customer with a first name, last name, and email
     * @param customer Customer
     */
    public void setCustomer(Customer customer) {
        this.customer = new Customer(customer.getFirstName(), customer.getLastName(), getCustomer().getEmail());
    }

    /**
     * Getter for the room
     * @return room
     */
    public IRoom getRoom() {
        return room;
    }

    /**
     * Setter for the room
     * Creates a new instance of Room with a room number, price, check-in date, and check-out date
     * @param room IRoom
     */
    public void setRoom(IRoom room) {
        this.room = new Room(room.getRoomNumber(), room.getRoomPrice(), room.getRoomType());
    }

    /**
     * Getter for the check-in date
     * @return checkInDate
     */
    public Date getCheckInDate() {
        return checkInDate;
    }

    /**
     * Setter for the check-in date
     * @param checkInDate Date
     */
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * Getter for the check-out date
     * @return checkOutDate
     */
    public Date getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * Setter for the check-out date
     * @param checkOutDate Date
     */
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(customer, that.customer) && Objects.equals(room, that.room) && Objects.equals(checkInDate, that.checkInDate) && Objects.equals(checkOutDate, that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }

    @Override
    public String toString() {
        return "Customer: " + customer + "\nRoom: " + room + "\nCheck-in: " + checkInDate + "\nCheck-out: " + checkOutDate;
    }
}
