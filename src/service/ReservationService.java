package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static ReservationService reservationService = null;

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();

    private ReservationService() {}

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        Collection<Reservation> customerReservations = reservations.get(customer.getEmail());

        if (customerReservations == null) {
            customerReservations = new LinkedList<>();
        }

        customerReservations.add(reservation);
        reservations.put(customer.getEmail(), customerReservations);

        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new LinkedList<>();

        Collection<IRoom> reservedRooms = findReservedRooms(checkInDate, checkOutDate);

        for (IRoom room : rooms.values()) {
            if (!reservedRooms.contains(room)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Collection<IRoom> findReservedRooms(Date checkInDate, Date checkOutDate) {
        Collection<Reservation> allReservations = getAllReservations();

        Collection<IRoom> reservedRooms = new LinkedList<>();

        for (Reservation reservation : allReservations) {
            if ((checkInDate.after(reservation.getCheckInDate()) || checkInDate.equals(reservation.getCheckInDate())) && checkOutDate.before(reservation.getCheckOutDate()) || checkOutDate.equals(reservation.getCheckOutDate())) {
                reservedRooms.add(reservation.getRoom());
            }
        }
        return reservedRooms;
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    void printAllRooms() {
        Collection<IRoom> allRooms = getAllRooms();
        for (IRoom room : allRooms) {
            System.out.println(room);
        }
    }

    private Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservations = new LinkedList<>();

        for (Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }
        return allReservations;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }
    public void printAllReservation() {
        Collection<Reservation> allReservations = getAllReservations();

        if (allReservations.isEmpty()) {
            System.out.println("Reservations not found");
        }
        else {
            for (Reservation room : allReservations) {
                System.out.println(room + "\n");
            }
        }
        System.out.println();
    }
}
