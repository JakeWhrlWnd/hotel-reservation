package menu;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String BOOKING_DATA_FORMAT = "MM/dd/yyyy";
    public static void showMainMenu() {
        System.out.println(mainMenuTxt);
        while (flag) {
            try {
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1 -> findAndReserveARoom();     // 1. Find and reserve a room
                    case 2 -> getCustomerReservations(); // 2. See my reservations
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
        System.out.println("When would you like to check-in: (MM/DD/YYYY)");
        Date checkIn = getDateEntry();

        System.out.println("When would you like to check-out: (MM/DD/YYYY)");
        Date checkOut = getDateEntry();

        if (checkIn != null && checkOut != null) {
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);
            if (availableRooms.isEmpty()) {
                Collection<IRoom> alternateRooms = hotelResource.findAlternateRooms(checkIn, checkOut);
                if (alternateRooms.isEmpty()) {
                    System.out.println("No rooms in the system");
                } else {
                    Date alternateCheckIn = hotelResource.addPlusDays(checkIn);
                    Date alternateCheckOut = hotelResource.addPlusDays(checkOut);
                    System.out.println("Here are some available alternate dates:\nCheck-in Date(s): " + alternateCheckIn + "\nCheck-out Date(s): " + alternateCheckOut);
                    printRooms(alternateRooms);
                    reserveARoom(scanner, alternateCheckIn, alternateCheckOut, alternateRooms);
                }
            } else {
                printRooms(availableRooms);
                reserveARoom(scanner, checkIn, checkOut, availableRooms);
            }
        }
    }

    private static Date getDateEntry() {
        try {
            return new SimpleDateFormat(BOOKING_DATA_FORMAT).parse(MainMenu.scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Invalid date");
            getDateEntry();
        }
        return null;
    }

    private static void reserveARoom(Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        final boolean isAccountExist = Menu.isYesResponse("Do you have an account with us? (y/n)");
        if (!isAccountExist) {
            System.out.println("Please create an account");
            return;
        }

        System.out.println("Enter account email (format: name@domain.com)");
        final String enteredEmail = scanner.nextLine();

        if (hotelResource.getCustomer(enteredEmail) == null) {
            System.out.println("Customer not found. Please create an account");
            return;
        }

        final String reserveRoomNumber = getRoomNumber(availableRooms);

        Reservation reservation = hotelResource.bookARoom(enteredEmail, reserveRoomNumber, checkInDate, checkOutDate);

        if (reservation != null) {
            System.out.println("Reservation successful! Enjoy your stay");
            takeABreak();
            System.out.println(reservation);
            takeABreak();
            showMainMenu();
        }
    }

    private static void printRooms(Collection<IRoom> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    private static void getCustomerReservations() {
        System.out.println("Please enter your Email: name@domain.com");
        String customerEmail = scanner.nextLine();
        printReservations(hotelResource.getCustomersReservations(customerEmail));
    }

    private static void printReservations(Collection<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            reservations.forEach(System.out::println);
        }
    }

    private static void createAnAccount() {
        final String email = getEmail();
        final String firstName = getFirstName();
        final String lastName = getLastName();

        hotelResource.createACustomer(firstName, lastName, email);
        System.out.println("Welcome " + firstName + "!");
        takeABreak();
        System.out.println("Account created successfully!");
        takeABreak();
        showMainMenu();
    }

    private static String getEmail() {
        boolean isError;
        String email;

        do {
            isError = false;
            System.out.println("Please, enter your Email: (name@domain.com)");
            email = scanner.nextLine();
            if (Customer.emailMatches(email, Customer.REGEX)) {
                isError = true;
            } else if (hotelResource.getCustomer(email) != null) {
                System.out.println("Customer already exists");
                isError = true;
            }
        } while (isError);

        return email;
    }

    private static String getFirstName() {
        boolean isError;
        String firstName;

        do {
            isError = false;
            System.out.println("Please, enter your First name:");
            firstName = scanner.nextLine();
            if (Customer.nameMatches(firstName, Customer.NAME_REGEX)) {
                isError = true;
            } else if (hotelResource.getCustomer(firstName) != null) {
                System.out.println("Customer already exists");
                isError = true;
            }
        } while (isError);

        return firstName;
    }

    private static String getLastName() {
        boolean isError;
        String lastName;

        do {
            isError = false;
            System.out.println("Please, enter your Last name:");
            lastName = scanner.nextLine();
            if (Customer.nameMatches(lastName, Customer.NAME_REGEX)) {
                isError = true;
            } else if (hotelResource.getCustomer(lastName) != null) {
                System.out.println("Customer already exists");
                isError = true;
            }
        } while (isError);

        return lastName;
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
