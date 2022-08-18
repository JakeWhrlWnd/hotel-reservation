/** The IRoom interface creates the abstract methods for the reservation app.
 *
 * @JamesNorris
 *
 */
package model;

public interface IRoom {
    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getRoomType();
    public boolean isFree();
}
