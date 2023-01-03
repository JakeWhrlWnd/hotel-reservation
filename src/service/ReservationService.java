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
    private ReservationService() {} // constructor
    public static ReservationService getInstance() { // static method to create instance
        return reservationService;
    }

    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();
    private final Map<String, IRoom> rooms = new HashMap<>();

    public Map<String, IRoom> getRoomList() {
        return rooms;
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        Collection<Reservation> reservations = getCustomerReservation(customer);

        if (Objects.isNull(reservations)) {
            reservations = new LinkedList<>();
        }

        reservations.add(reservation);
        this.reservations.put(customer.getEmail(), reservations);

        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new LinkedList<>();
        Collection<IRoom> unavailableRooms = findUnavailableRooms(checkInDate, checkOutDate);

        for (IRoom room : rooms.values()) {
            if (!unavailableRooms.contains(room)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Collection<IRoom> findUnavailableRooms(Date checkInDate, Date checkOutDate) {
        Collection<Reservation> allReservations = getAllReservations();
        Collection<IRoom> unavailableRooms = new LinkedList<>();

        for (Reservation reservation : allReservations) {
            if ((checkInDate.after(reservation.getCheckInDate()) ||
                    checkInDate.equals(reservation.getCheckInDate())) &&
                    checkOutDate.before(reservation.getCheckOutDate()) ||
                    checkOutDate.equals(reservation.getCheckOutDate())) {
                unavailableRooms.add(reservation.getRoom());
            }
        }
        return unavailableRooms;
    }

    private Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservations = new LinkedList<>();
        reservations.values().forEach(allReservations::addAll);
        return allReservations;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public void printAllReservation() {
        Collection<Reservation> reservations = getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("Unfortunately, no reservations were found.");
        } else {
            reservations.forEach(System.out::println);
        }
    }
}
