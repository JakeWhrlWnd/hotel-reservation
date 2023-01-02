package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public class HotelResource {
    private static final HotelResource hotelResource = new HotelResource();
    private HotelResource() {}
    public static HotelResource getInstance() {
        return hotelResource;
    }

    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public static Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public static void createACustomer(String firstName, String lastName, String email) {
        try {
            customerService.addCustomer(firstName, lastName, email);
        } catch (IllegalArgumentException e) {
            System.out.println("""
                    Email is not valid.
                    Please, create an account with a valid email.""");
        }
    }

    public static IRoom getARoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public static Collection<Reservation> getCustomerReservation(String customerEmail) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.getCustomerReservation(customer);
    }

    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }
}
