package model;
/**
 * Room types that are offered
 * {@link #SINGLE}
 * {@link #DOUBLE}
 *
 * @author James Norris
 */
public enum RoomType {
    /**
     * Single room
     */
    SINGLE("1"),
    /**
     * Double room
     */
    DOUBLE("2");

    public final String beds;
    RoomType(String beds) {
        this.beds = beds;
    }
    public static RoomType valueForBeds(String beds) {
        for (RoomType roomType : values()) {
            if (roomType.beds.equals(beds)) {
                return roomType;
            }
        }
        throw new IllegalArgumentException();
    }
    }
