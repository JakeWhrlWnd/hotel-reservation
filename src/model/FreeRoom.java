package model;
/** This creates the FreeRoom class. It extends the Room class and overrides the price.
 *
 * @author James Norris
 *
 */
public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, Double price, RoomType enumeration) {
        super(roomNumber, 0.0, enumeration);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
