package model;
/**
 * IRoom interface
 * Groups the methods needed to be implemented by the Room Class
 * {@link #getRoomNumber()}
 * {@link #getRoomPrice()}
 * {@link #getRoomType()}
 * {@link #isFree()}
 *
 * @author James Norris
 */
public interface IRoom {
    /**
     *
     * @return the room number
     */
    String getRoomNumber();

    /**
     *
     * @return the room price
     */
    Double getRoomPrice();

    /**
     *
     * @return the room type
     */
    RoomType getRoomType();

    /**
     *
     * @return whether the room is free
     */
    boolean isFree();
}

