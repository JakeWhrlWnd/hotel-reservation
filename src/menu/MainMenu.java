package menu;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

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

    private static void reserveARoom(Scanner scanner, Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        System.out.println("Would you like to book a room? (y/n)");
        String bookARoom = scanner.nextLine();

        if ("y".equals(bookARoom)) {
            System.out.println("Do you have an account with us? (y/n)");
            String accountStatus = scanner.nextLine();

            if ("y".equals(accountStatus)) {
                System.out.println("Enter your email: name@domain.com");
                String customerEmail = scanner.nextLine();

                if (hotelResource.getCustomer(customerEmail) == null) {
                    System.out.println("Account not found. Please, create a new account.");
                } else {
                    System.out.println("What room would you like to reserve?");
                    String roomNumber = scanner.nextLine();

                    if (rooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber))) {
                        IRoom room = hotelResource.getRoom(roomNumber);
                        Reservation reservation = hotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
                        System.out.println("Reservation was created successfully!");
                        takeABreak();
                        System.out.println(reservation);
                    } else {
                        System.out.println("Room number not available. Please, retry reservation.");
                    }
                }
                showMainMenu();
            } else {
                System.out.println("To make a reservation, you need to create an account.");
                createAnAccount();
            }
        } else if ("n".equals(bookARoom)) {
            showMainMenu();
        } else {
            reserveARoom(scanner, checkInDate, checkOutDate, rooms);
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
        String firstName = null;
        String lastName = null;
        String email = null;
        try {
            System.out.println("Please enter your First name:");
            firstName = scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }

        try {
            System.out.println("Please enter your Last name:");
            lastName = scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }

        try {
            System.out.println("Please enter your Email: name@domain.com");
            email = scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }

        hotelResource.createACustomer(email, firstName, lastName);
        System.out.println("Welcome " + firstName + "!");
        takeABreak();
        System.out.println("Account created successfully!");
        takeABreak();
        showMainMenu();
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
