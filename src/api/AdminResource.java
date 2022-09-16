package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

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

    public void addRoom(List<IRoom> rooms) { rooms.forEach(reservationService::addRoom); }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() { reservationService.printAllReservation(); }
}
