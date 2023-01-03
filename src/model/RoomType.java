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

    public final String label;

    RoomType(String label) {
        this.label = label;
    }

    public static RoomType valueOfLabel(String label) {
        for (RoomType roomType : values()) {
            if (roomType.label.equals(label)) {
                return roomType;
            }
        }
        throw new IllegalArgumentException();
    }
}
