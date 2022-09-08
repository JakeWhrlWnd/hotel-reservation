package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    /**
     * Singleton Pattern for AdminResource Class
     * Creates a static reference and a method to get the instance
     */
    private static AdminResource adminResource; // static reference

    private AdminResource() {}

    public static AdminResource getInstance() {
        if (adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public static Customer getCustomer(String email) {
        CustomerService customerService = CustomerService.getInstance();
        return customerService.getCustomer(email);
    }

    public static void addRoom(List<IRoom> rooms) {
        ReservationService reservationService = ReservationService.getInstance();
        for (IRoom theRoom : rooms) {
            reservationService.addRoom(theRoom);
        }
    }

    public static Collection<IRoom> getAllRooms() {
        ReservationService reservationService = ReservationService.getInstance();
        return reservationService.getAllRooms();
    }

    public static Collection<Customer> getAllCustomers() {
        CustomerService customerService = CustomerService.getInstance();
        return customerService.getAllCustomers();
    }

    public static void displayAllReservations() {
        ReservationService reservationService = ReservationService.getInstance();
        reservationService.printAllReservation();
    }
}
