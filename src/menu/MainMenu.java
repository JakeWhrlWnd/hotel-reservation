package menu;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat bookingDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public static void showMainMenu() {
        System.out.println(mainMenuTxt);
        while (flag) {
            try {
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1 -> findAndReserveARoom();     // 1. Find and reserve a room
                    case 2 -> getCustomerReservations();       // 2. See my reservations
                    case 3 -> createAnAccount();         // 3. Create an account
                    case 4 -> AdminMenu.showAdminMenu(); // 4. Open the Admin Menu
                    case 5 -> flag = false;              // 5. Exit application
                    default -> throw new IllegalArgumentException("Invalid input. Please, enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Only numbers accepted.");
            } catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void findAndReserveARoom() {
        Date checkIn = getCheckInDate();
        Date checkOut = getCheckOutDate(checkIn);

        Collection<IRoom> availableRooms = HotelResource.findARoom(checkIn, checkOut);
        boolean wantsToBook = false;
        if (availableRooms.isEmpty()) {
            Date newCheckIn = getAlternateDate(checkIn);
            Date newCheckOut = getAlternateDate(checkOut);
            availableRooms = HotelResource.findARoom(newCheckIn, newCheckOut);
            if (!availableRooms.isEmpty()) {
                    System.out.println("No rooms available for those dates.\nRooms available for alternate dates:\nCheck-in Date: " + newCheckIn + "\nCheck-out Date: " + newCheckOut);
                    wantsToBook = showAvailableRoomsAndBook(availableRooms);
                    checkIn = newCheckIn;
                    checkOut = newCheckOut;
            } else {
                System.out.println("No rooms available for those dates.");
            }
        } else {
            System.out.println("Available rooms for check-in on " + checkIn + " and check-out on " + checkOut);
            wantsToBook = showAvailableRoomsAndBook(availableRooms);
        }
        if (!wantsToBook){
            return;
        }

        Customer customer = getCustomerForReservation();
        if (customer == null) {
            System.out.println("Sorry, no account exists for that email.");
        }

        IRoom room = getRoomForReservation(availableRooms);

        assert customer != null;
        Reservation reservation = HotelResource.bookARoom(customer.getEmail(), room, checkIn, checkOut);
        if (reservation == null) {
            System.out.println("Couldn't process your booking, the room is not available.");
        } else {
            System.out.println("Your booking was successful.");
            System.out.println(reservation);
        }
    }

    private static Date getCheckInDate() {
        Date checkIn = null;
        boolean validCheckInDate = false;
        while (!validCheckInDate) {
            System.out.println("Enter check-in date as MM/DD/YYYY.");
            String checkInDateInput = scanner.nextLine();
            try {
                checkIn = bookingDateFormat.parse(checkInDateInput);
                Date today = new Date();
                if (checkIn.before(today)) {
                    System.out.println("Check-in date cannot be in the past.");
                } else {
                    validCheckInDate = true;
                }
            } catch (ParseException e) {
                System.out.println("Date format is invalid - (MM/DD/YYYY)");
            }
        }
        return checkIn;
    }

    private static Date getCheckOutDate(Date checkIn) {
        Date checkOut = null;
        boolean validCheckOutDate = false;
        while (!validCheckOutDate) {
            System.out.println("Enter check-in date as MM/DD/YYYY.");
            String checkOutDateInput = scanner.nextLine();
            try {
                checkOut = bookingDateFormat.parse(checkOutDateInput);
                if (checkOut.before(checkIn)) {
                    System.out.println("Check-in date cannot be in the past.");
                } else {
                    validCheckOutDate = true;
                }
            } catch (ParseException e) {
                System.out.println("Date format is invalid - (MM/DD/YYYY)");
            }
        }
        return checkOut;
    }

    private static Date getAlternateDate(Date date) {
        Calendar a = Calendar.getInstance();
        a.setTime(date);
        a.add(Calendar.DATE, 7);
        return a.getTime();
    }

    private static boolean showAvailableRoomsAndBook(Collection<IRoom> availableRooms) {
        for (IRoom room : availableRooms) {
            System.out.println(room.toString());
        }
        System.out.println();
        System.out.println("Would you like to book a room? (y/n)");
        String choice = scanner.nextLine();
        try {
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Enter y for yes or any other character for no.");
        }
        return false;
    }

    private static Customer getCustomerForReservation() {
        String email;
        boolean hasAccount = false;
        System.out.println("Do you have an account with us? (y/n)");
        String choice = scanner.nextLine();
        try {
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                hasAccount = true;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Enter y for yes or any other character for no.");
        }
        if (hasAccount) {
            System.out.println("Please enter your Email: name@domain.com");
            email = scanner.nextLine();
        } else {
            email = createAnAccount();
        }
        return HotelResource.getCustomer(email);
    }

    private static IRoom getRoomForReservation(Collection<IRoom> availableRooms) {
        IRoom room = null;
        String roomNumber;
        boolean validRoomNumber = false;
        while (!validRoomNumber) {
            System.out.println("What room would you like to book? Enter the room number: ");
            roomNumber = scanner.nextLine();
            room = HotelResource.getRoom(roomNumber);
            if (room == null) {
                System.out.println("That room doesn't exist. Enter a valid room number.");
            } else {
                if (!availableRooms.contains(room)) {
                    System.out.println("That room is not available. Enter a valid room number.");
                } else {
                    validRoomNumber = true;
                }
            }
        }
        return room;
    }

    private static void getCustomerReservations() {
        System.out.println("Please enter your Email: name@domain.com");
        String email = scanner.nextLine();
        Customer customer = HotelResource.getCustomer(email);
        if (customer == null) {
            System.out.println("No account exists for that email.");
        }

        assert customer != null;
        Collection<Reservation> reservations = HotelResource.getCustomersReservations(customer.getEmail());
        if (reservations.isEmpty()) {
            System.out.println("No reservations in the system.");
        }

        for (Reservation reservation : reservations) {
            System.out.println(reservation.toString());
        }
    }

    private static String createAnAccount() {
        System.out.println("Please enter your First name:");
        String firstName = scanner.nextLine();

        System.out.println("Please enter your Last name:");
        String lastName = scanner.nextLine();

        String email = null;
        boolean validEmail = false;
        while (!validEmail) {
            try {
                System.out.println("Please enter your Email: name@domain.com");
                email = scanner.nextLine();
                HotelResource.createACustomer(email, firstName, lastName);
                System.out.println("Welcome " + firstName + "!");
                takeABreak();
                System.out.println("Account creation successful!");
                takeABreak();
                validEmail = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        return email;
    }

    public static void takeABreak() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected static boolean flag = true;

    protected static final String mainMenuTxt = """
            Welcome to the Hotel Reservation Application
            -----------------------------------------------
            1. Find and Reserve a room
            2. See my reservations
            3. Create an account
            4. Admin
            5. Exit
            -----------------------------------------------
            Please choose an option from the menu""";
}
