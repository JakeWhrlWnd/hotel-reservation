package model;
/**
 * Creates the FreeRoom Class. Extends the Room Class.
 * Overrides the price from the Room Class to specify the room as free.
 * @author James Norris
 */
public class FreeRoom extends Room{
    /**
     * Constructor for FreeRoom Class
     * @param roomNumber string, the hotel room number
     * @param price double, the hotel room price
     * @param enumeration string, the hotel room type - SINGLE or DOUBLE
     */
    public FreeRoom(final String roomNumber, final Double price, final RoomType enumeration) {
        super(roomNumber, 0.0, enumeration);
    }

    @Override
    public String toString() {
        return "Your room is free.\n" + super.toString();
    }
}
