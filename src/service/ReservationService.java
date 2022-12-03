package service;

import model.IRoom;
import model.Reservation;

import java.util.*;

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
    private final Map<String, List<Reservation>> reservations = new HashMap<>();
    private final Map<String, IRoom> rooms = new HashMap<>();

    public void addRoom(IRoom room) {
        if (rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException("This room already exist.");
        } else {
            rooms.put(room.getRoomNumber(), room);
        }
    }

    public IRoom getARoom(String roomId) {
        if (rooms.containsKey(roomId)){
            return rooms.get(roomId);
        } else {
            throw new IllegalArgumentException("This room does not exist.");
        }
    }

    public Map<String, IRoom> getAllRooms() {
        return rooms;
    }

    public Collection<Reservation> getCustomerReservation(String email) {
        return reservations.get(email);
    }

    public Map<String, List<Reservation>> getAllReservations() {
        return reservations;
    }

    void addReservation(Reservation reservation) {
        reservations.put(reservation.getCustomer().getEmail(), List.of(reservation));
    }

    public void reserveARoom(Reservation reservation) {
        final String customerEmail = reservation.getCustomer().getEmail();
        List<Reservation> customerReservations = reservations.get(customerEmail);

        if (customerReservations == null) {
            List<Reservation> list = new ArrayList<>();
            list.add(reservation);
            reservations.put(customerEmail, list);
        } else {
            customerReservations.add(reservation);
        }
    }
}
