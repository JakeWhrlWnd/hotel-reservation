package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    /**
     * Singleton Pattern for ReservationService Class
     * Creates a static reference and a method to get the instance
     */
    private static ReservationService reservationService; // static reference
    private ReservationService() {} // constructor
    public static ReservationService getInstance() { // static method to create instance
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }
    private static final Map<String, Collection<Reservation> reservations = new HashMap<>();
    private static final Map<String, IRoom> rooms = new HashMap<>();
    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }
    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }
    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {}
    public Collection<Reservation> getCustomersReservation(Customer customer) {}
    public void printAllReservation() {}
}
