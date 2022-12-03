package api;

import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminResource {
    /**
     * Singleton Pattern for CustomerService Class
     * Creates a static reference and a method to get the instance
     */
    private static AdminResource adminResource; // static reference
    private AdminResource() {} // constructor
    public static AdminResource getInstance() { // static method to create instance
        if (adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(String roomNumber, double price, int roomType) {
        RoomType rType;
        if (roomType == 1) {
            rType = RoomType.SINGLE;
        } else {
            rType = RoomType.DOUBLE;
        }

        Room room = new Room(roomNumber, price, rType);
        reservationService.addRoom(room);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms().values();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers().values();
    }

    public Collection<Reservation> getAllReservations() {
        List<Reservation> allReservations = new ArrayList<>();
        Collection<List<Reservation>> reservations = reservationService.getAllReservations().values();
        for (List<Reservation> reservationList : reservations) {
            allReservations.addAll(reservationList);
        }
        return allReservations;
    }
}
