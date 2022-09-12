package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    /**
     * Singleton Pattern for ReservationService Class
     * Creates a static reference and a method to get the instance
     */
    private static final ReservationService reservationService = new ReservationService(); // static reference

    private ReservationService() {}

    public static ReservationService getInstance() {
        return reservationService;
    }

    private static final Map<String, IRoom> rooms = new HashMap<>();
    private static final Map<String, Collection<Reservation>> reservations = new HashMap<>();

    public static void addRoom(IRoom room) {
        if (rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException(room.getRoomNumber() + " already in the system.");
        } else {
            rooms.put(room.getRoomNumber(), room);
        }
    }

    public static IRoom getARoom(String roomId) {
        if (rooms.containsKey(roomId)){
            return rooms.get(roomId);
        } else {
            throw new IllegalArgumentException(roomId + " not in the system.");
        }
    }

    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (isRoomReserved(room, checkInDate, checkOutDate)) {
            return null;
        }
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        Collection<Reservation> customerReservations = getCustomersReservation(customer);
        if (customerReservations == null) {
            customerReservations = new LinkedList<>();
        }
        customerReservations.add(reservation);
        reservations.put(customer.email(), customerReservations);
        return reservation;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> unavailableRooms = getAllUnavailableRooms(checkInDate, checkOutDate);

        Collection<IRoom> availableRooms = new LinkedList<>();
        for (IRoom room : getAllRooms()) {
            if (!unavailableRooms.contains(room)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public static Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer.email());
    }

    public static Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservations = new LinkedList<>();
        for (Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }
        return allReservations;
    }

    public static Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    private static Collection<IRoom> getAllUnavailableRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> unavailableRooms = new LinkedList<>();
        for (Reservation reservation : getAllReservations()) {
            if (reservation.isRoomReserved(checkInDate, checkOutDate)) {
                unavailableRooms.add(reservation.room());
            }
        }
        return unavailableRooms;
    }

    public static boolean isRoomReserved(IRoom room, Date checkInDate, Date checkOutDate) {
        Collection<IRoom> unavailableRooms = getAllUnavailableRooms(checkInDate, checkOutDate);
        return unavailableRooms.contains(room);
    }
}
