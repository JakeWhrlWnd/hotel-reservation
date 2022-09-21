package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    private static final int RECOMMENDED_PLUS_DAYS = 7;
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
    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();
    private final Map<String, IRoom> rooms = new HashMap<>();

    public void addRoom(IRoom room) {
        if (rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException(room.getRoomNumber() + " already in the system.");
        } else {
            rooms.put(room.getRoomNumber(), room);
        }
    }

    public IRoom getARoom(String roomId) {
        if (rooms.containsKey(roomId)){
            return rooms.get(roomId);
        } else {
            throw new IllegalArgumentException(roomId + " not in the system.");
        }
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        Collection<Reservation> customerReservations = getCustomersReservation(customer);
        if (customerReservations == null) {
            customerReservations = new LinkedList<>();
        }
        customerReservations.add(reservation);
        reservations.put(customer.getEmail(), customerReservations);
        return reservation;
    }
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return findAvailableRooms(checkInDate, checkOutDate);
    }
    public Collection<IRoom> findAlternateRooms(Date checkInDate, Date checkOutDate) {
        return findAvailableRooms(addPlusDays(checkInDate), addPlusDays(checkOutDate));
    }
    public Collection<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
        Collection<Reservation> allReservations = getAllReservations();
        Collection<IRoom> unavailableRooms = new LinkedList<>();
        for (Reservation reservation : allReservations) {
            if (reservationOverlaps(reservation, checkInDate, checkOutDate)) {
                unavailableRooms.add(reservation.getRoom());
            }
        }
        return rooms.values().stream().filter(room -> unavailableRooms.stream().noneMatch(unavailableRoom -> unavailableRoom.equals(room))).collect(Collectors.toList());
    }
    public Date addPlusDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, RECOMMENDED_PLUS_DAYS);
        return calendar.getTime();
    }
    private boolean reservationOverlaps(Reservation reservation, Date checkInDate, Date checkOutDate) {
        return checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate());
    }
    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }
    public void printAllReservation() {
        Collection<Reservation> reservations = getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println("No reservations in the system");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }
    private Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservations = new LinkedList<>();
        for (Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }
        return allReservations;
    }
}
