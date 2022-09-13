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

    private ReservationService() {}

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }
    private static final List<Reservation> reservations = new ArrayList<>();
    private static final Map<String, IRoom> rooms = new HashMap<>();
    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }
    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {}
    public Collection<Reservation> getCustomersReservation(Customer customer) {}
    public void printAllReservation() {}
}
