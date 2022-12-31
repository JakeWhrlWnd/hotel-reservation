package api;

import model.Customer;
import model.IRoom;
import model.Pair;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public class HotelResource {
    /**
     * Singleton Pattern for HotelResource Class
     * Creates a static reference and a method to get the instance
     */
    private static final HotelResource hotelResource = new HotelResource(); // static reference
    private HotelResource() {} // constructor
    public static HotelResource getInstance() { // static method to create instance
        return hotelResource;
    }

    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();
    private static final AdminResource adminResource = AdminResource.getInstance();

    public static Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public static void createACustomer(String firstName, String lastName, String email) {
        try {
            Customer customer = new Customer(firstName, lastName, email);
            customerService.addCustomer(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("""
                    Email is not valid.
                    Please, create an account with a valid email.""");
        }
    }

    public static IRoom getARoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public boolean isRoomAvailable(String roomNumber, Collection<IRoom> availableRooms) {
        for (IRoom availableRoom : availableRooms) {
            if (roomNumber.equals(availableRoom.getRoomNumber())) {
                return true;
            }
        }
        return false;
    }

    public static Reservation bookARoom(String customerEmail, String roomNumber, Date checkInDate, Date checkOutDate) {
        Customer customer = adminResource.getCustomer(customerEmail);
        IRoom room = hotelResource.getARoom(roomNumber);
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        room.getBookedDates().add(Pair.createPair(checkInDate, checkOutDate));
        reservationService.reserveARoom(reservation);
        return reservation;
    }

    public static Collection<IRoom> findAvailableRooms(Date checkIn, Date checkOut) {
        List<IRoom> availableRooms = new ArrayList<>();
        Collection<IRoom> allRooms = reservationService.getAllRooms().values();

        for (IRoom room : allRooms) {
            if (room.getBookedDates().size() == 0) {
                availableRooms.add(room);
                continue;
            }
            boolean isSuitable = false;
            for (Pair<Date, Date> bookDates : room.getBookedDates()) {
                if (!isInBookedRange(checkIn, checkOut, bookedDates) && !isOutBookedRange(checkIn, checkOut, bookedDates)) {
                    isSuitable = true;
                } else {
                    isSuitable = false;
                    break;
                }
            }
            if (isSuitable) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    private boolean isInBookedRange(Date checkIn, Date checkOut, Pair<Date, Date> bookedDates) {
        if ((checkIn.equals(bookedDates.getBookedCheckIn()) || checkIn.after(bookedDates.getBookedCheckIn())) && (checkIn.equals(bookedDates.getBookedCheckOut()) || checkIn.before(bookedDates.getBookedCheckOut()))) {
            return true;
        }
        return (checkOut.equals(bookedDates.getBookedCheckOut()) || checkOut.after(bookedDates.getBookedCheckIn())) && (checkOut.equals(bookedDates.getBookedCheckOut()) || checkOut.before(bookedDates.getBookedCheckOut()));
    }

    private boolean isOutBookedRange(Date checkIn, Date checkOut, Pair<Date, Date> bookedDates) {
        return checkIn.before(bookedDates.getBookedCheckIn()) && checkOut.after(bookedDates.getBookedCheckOut());
    }

    public static Collection<Reservation> getCustomerReservation(String email) {
        return reservationService.getCustomerReservation(email);
    }

    public static Date addDefaultDays(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 7);
        return c.getTime();
    }
}
