package model;
/** The IRoom interface creates the abstract methods for the reservation app.
 *
 * @author James Norris
 *
 */
public interface IRoom {
    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getRoomType();
    public boolean isFree();
}
