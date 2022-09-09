package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();
    /**
     * Singleton Pattern for HotelResource Class
     * Creates a static reference and a method to get the instance
     */
    private static HotelResource hotelResource; // static reference

    private HotelResource() {}

    public static HotelResource getInstance() {
        if (hotelResource == null) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    public static Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public static void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public static IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public static Collection<Reservation> getCustomersReservations(String customerEmail) {
        return reservationService.getCustomersReservation(getCustomer(customerEmail));
    }

    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    public static Collection<IRoom> findAlternateRooms(Date checkIn, Date checkOut) {
        return reservationService.findAlternateRooms(checkIn, checkOut);
    }

    public static Date addPlusDays(Date date) {
        return reservationService.addPlusDays(date);
    }


}
