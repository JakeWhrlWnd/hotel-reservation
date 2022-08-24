package model;
/**
 * Creates the Room Class.
 * This class is used to implement the IRoom Interface.
 * @author James Norris
 */
public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    /**
     * Constructor for Room Class
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
     *
     * @return The hotel room number
     */
    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Overrides the getRoomPrice method declared by the IRoom Interface
     * @return The hotel room price
     */
    @Override
    public Double getRoomPrice() {
        return price;
    }

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

    @Override
    public String toString() {
        return "Room number: " + roomNumber
                + "\n Room price: $" + price
                + "\n Room type: " + enumeration;
    }
}
