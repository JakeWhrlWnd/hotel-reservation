/** The IRoom interface creates the abstract methods for the reservation app.
 *
 * @JamesNorris
 *
 */
package model;

public interface IRoom {
    String getRoomNumber();
    Double getRoomPrice();
    RoomType getRoomType();
    boolean isFree();
}
