package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    /**
     * Singleton Pattern for AdminResource Class
     * Creates a static reference and a method to get the instance
     */
    private static final AdminResource adminResource = new AdminResource(); // static reference

    private AdminResource() {}

    public static AdminResource getInstance() {
        return adminResource;
    }

    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public static Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public static void addRoom(List<IRoom> rooms) {
        rooms.forEach(reservationService::addRoom);
    }

    public static Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public static Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public static Collection<Reservation> getAllReservations() { return reservationService.getAllReservations(); }
}
