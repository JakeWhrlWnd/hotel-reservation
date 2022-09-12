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
    SINGLE (1),
    /**
     * Double room
     */
    DOUBLE (2);

    RoomType(int roomType) {
        this.roomType = roomType;
    }

    private final int roomType;

    public int getRoomTypeAsInt() {
        return roomType;
    }

    public static int convertRoomTypeToInt(RoomType inputType) {
        for (RoomType roomType : RoomType.values()) {
            if (roomType.getRoomTypeAsInt() == inputType.getRoomTypeAsInt()) {
                return roomType.getRoomTypeAsInt();
            }
        }
        return -1;
    }

    public static RoomType convertIntToRoomType(int intType) {
        for (RoomType roomType : RoomType.values()) {
            if (roomType.getRoomTypeAsInt() == intType) {
                return roomType;
            }
        }
        return null;
        }
    }
