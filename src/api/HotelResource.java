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
     * Singleton Pattern for CustomerService Class
     * Creates a static reference and a method to get the instance
     */
    private static HotelResource hotelResource; // static reference
    private HotelResource() {} // constructor
    public static HotelResource getInstance() { // static method to create instance
        if (hotelResource == null) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private final AdminResource adminResource = AdminResource.getInstance();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        try {
            Customer customer = new Customer(firstName, lastName, email);
            customerService.addCustomer(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("Email is not valid. Please, create an account with a valid email");
        }
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, String roomId, Date checkInDate, Date checkOutDate) {
        Customer customer = adminResource.getCustomer(customerEmail);

        IRoom room = hotelResource.getRoom(roomId);

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        room.getBookedDates().add(Pair.createPair(checkInDate, checkOutDate));
        reservationService.reserveARoom(reservation);
        return reservation;
    }

    public Collection<IRoom> findAvailableRooms(Date checkIn, Date checkOut) {
        List<IRoom> availableRooms = new ArrayList<>();
        Collection<IRoom> allRooms = reservationService.getAllRooms().values();

        for (IRoom room : allRooms) {
            if (room.getBookedDates().size() == 0) {
                availableRooms.add(room);
                continue;
            }

            boolean isSuitable = false;
            for (Pair<Date, Date> bookedDates : room.getBookedDates()) {
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
        return (checkOut.equals(bookedDates.getBookedCheckIn()) || checkOut.after(bookedDates.getBookedCheckIn())) && (checkOut.equals(bookedDates.getBookedCheckOut()) || checkOut.before(bookedDates.getBookedCheckOut()));
    }

    private boolean isOutBookedRange(Date checkIn, Date checkOut, Pair<Date, Date> bookedDates) {
        return checkIn.before(bookedDates.getBookedCheckIn()) && checkOut.after(bookedDates.getBookedCheckOut());
    }

    public Collection<Reservation> getCustomerReservation(String email) {
        return reservationService.getCustomerReservation(email);
    }

    public Date addDefaultDays(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 7);
        return c.getTime();
    }
}
