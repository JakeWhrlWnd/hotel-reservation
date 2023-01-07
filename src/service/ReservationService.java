package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
/**
 * Reservation Service class
 *
 * @author James Norris
 */
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

    public Map<String, IRoom> additionalRooms = new HashMap<>();

    private static final int additionalDays = 7;
    Date addAdditionalDays(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, additionalDays);
        return c.getTime();
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
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
        int flag = 0;
        additionalRooms.putAll(rooms);
        Collection<Reservation> allReservations = getAllReservations();
        for (Reservation reservation : allReservations) {
            Date reservedCheckOutDate = reservation.getCheckOutDate();
            Date reservedCheckInDate = reservation.getCheckInDate();
            if (checkInDate.before(reservedCheckOutDate) && checkOutDate.after(reservedCheckInDate)) {
                flag = 1;
                IRoom existingRoom = reservation.getRoom();
                String existingRoomNumber = existingRoom.getRoomNumber();
                additionalRooms.remove(existingRoomNumber);
            }
        }
        if (flag == 0) {
            return rooms.values();
        } else {
            return additionalRooms.values();
        }
    }

    public Collection<IRoom> findRecommendedRooms(Date checkInDate, Date checkOutDate) {
        return findRooms(addAdditionalDays(checkInDate), addAdditionalDays(checkOutDate));
    }

    private Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservations = new LinkedList<>();
        for (Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }
        return allReservations;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public void printAllReservation() {
        Collection<Reservation> reservations = getAllReservations();
        int numberOfReservations = 0;
        if (reservations.size() != 0) {
            for (Reservation reservation : reservations) {
                numberOfReservations++;
                System.out.println("(" + numberOfReservations + ")");
                System.out.println(reservation.getCustomer().getFirstName() + " " + reservation.getRoom().getRoomType());
                System.out.println("Price per Night: " + reservation.getRoom().getRoomPrice());
                System.out.println("Check-in Date: " + reservation.getCheckInDate());
                System.out.println("Checkout Date: " + reservation.getCheckOutDate());
            }
        } else {
            System.out.println("Unfortunately, no reservations were found.");
        }
    }
}
