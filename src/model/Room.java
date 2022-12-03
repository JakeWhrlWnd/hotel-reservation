package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Room Class
 * Implements the IRoom Interface
 * @author James Norris
 */
public class Room implements IRoom{
    private String roomNumber; // Represents the Room number
    private Double price; // Represents the Room price
    private RoomType enumeration; // Represents the Room type - Single or Double

    private List<Pair<Date, Date>> bookedDates = new ArrayList<>();

    /**
     * Constructor for the Room Class
     * Creates a room with number, price, and type
     * @param roomNumber string, the hotel room number
     * @param price double, the hotel room price
     * @param enumeration string, the hotel room type - SINGLE or DOUBLE
     */
    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    /**
     * Overrides the getRoomNumber method declared by the IRoom Interface
     * @return The hotel room number
     */
    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Setter for the room number value
     * @param roomNumber string
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Overrides the getRoomPrice method declared by the IRoom Interface
     * @return The hotel room price
     */
    @Override
    public Double getRoomPrice() { return price; }

    /**
     * Setter for the price value
     * @param price double
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Overrides the getRoomType method declared by the IRoom Interface
     * @return The hotel room type - either SINGLE or DOUBLE
     */
    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    /**
     * Setter for enumeration value
     * @param enumeration RoomType
     */
    public void setEnumeration(RoomType enumeration) {
        this.enumeration = enumeration;
    }

    /**
     * Overrides the isFree method declared by the IRoom Interface
     * @return False when the room has a price
     */
    @Override
    public boolean isFree() {
        return false;
    }

    /**
     * Override the getBookedDates method declared by the IRoom Interface
     * @return Pairing of booked dates
     */
    @Override
    public List<Pair<Date, Date>> getBookedDates() {
        return bookedDates;
    }

    /**
     * Setter for the bookedDates value
     * @param bookedDates
     */
    public void setBookedDates(List<Pair<Date, Date>> bookedDates) {
        this.bookedDates = bookedDates;
    }

    /**
     * Overrides the toString method
     * @return A better description of the Room attributes
     */
    @Override
    public String toString() {
        return "Room number: " + roomNumber
                + "\nRoom price: $" + price
                + "\nRoom type: " + enumeration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber) && Objects.equals(price, room.price) && enumeration == room.enumeration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, enumeration);
    }
}
