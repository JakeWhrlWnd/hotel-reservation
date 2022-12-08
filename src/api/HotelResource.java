package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public class HotelResource {
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public static Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public static void createACustomer(String firstName, String lastName, String email) {
        customerService.addCustomer(firstName, lastName, email);
    }

    public static IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public static Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.getCustomersReservation(customer);
    }

    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        Collection<IRoom> availableRooms = reservationService.findRooms(checkIn, checkOut);

        if (availableRooms.size() == 0) {
            System.out.println("No room was found for given dates.");
            Collection<IRoom> recommendedRooms;
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(checkIn);
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            Date newCheckIn = Date.from(calendar.toInstant());

            calendar.setTime(checkOut);
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            Date newCheckOut = Date.from(calendar.toInstant());

            recommendedRooms = reservationService.findRooms(newCheckIn, newCheckOut);

            if (recommendedRooms.size() != 0) {
                System.out.println("Showing recommended rooms from: " + newCheckIn + "to" + newCheckOut + ".");
                return recommendedRooms;
            }
        }
        return availableRooms;
    }
}
