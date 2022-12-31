package api;

import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminResource {

    /**
     * Singleton Pattern for AdminResource Class
     * Creates a static reference and a method to get the instance
     */
    private static final AdminResource adminResource = new AdminResource(); // static reference
    private AdminResource() {} // constructor
    public static AdminResource getInstance() { // static method to create instance
        return adminResource;
    }


    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public static Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public static void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public static Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms().values();
    }

    public static Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers().values();
    }

    public static Collection<Reservation> displayAllReservations() {
        List<Reservation> allReservations = new ArrayList<>();
        Collection<List<Reservation>> reservations = reservationService.getAllReservations().values();
        for (List<Reservation> listRes : reservations) {
            allReservations.addAll(listRes);
        }
        return allReservations;
    }
}
