package menu;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

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
                    case 2 -> seeMyReservations();       // 2. See my reservations
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
        System.out.println("Enter check-in date as MM/DD/YYYY.");
        Date checkIn = getBookingDate();

        System.out.println("Enter check-in date as MM/DD/YYYY.");
        Date checkOut = getBookingDate();

        if (checkIn != null && checkOut != null) {
            Collection<IRoom> availableRooms = HotelResource.findARoom(checkIn, checkOut);

            if (availableRooms.isEmpty()) {
                Collection<IRoom> alternateRooms = HotelResource.findAlternateRooms(checkIn, checkOut);


                if (alternateRooms.isEmpty()) {
                    System.out.println("Sorry, no rooms were found.");
                } else {
                    Date alternateCheckIn = HotelResource.addPlusDays(checkIn);
                    Date alternateCheckOut = HotelResource.addPlusDays(checkOut);
                    System.out.println("Rooms available for alternate dates:\nCheck-in Date: " + alternateCheckIn + "\nCheck-out Date: " + alternateCheckOut);
                    printRooms(alternateRooms);
                    bookRoom(scanner, alternateCheckIn, alternateCheckOut, alternateRooms);
                }
            } else {
                printRooms(availableRooms);
                bookRoom(scanner, checkIn, checkOut, availableRooms);
            }
        }
    }

    private static Date getBookingDate() {
        try {
            return bookingDateFormat.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Date invalid.");
        }
        return null;
    }

    private static void printRooms(Collection<IRoom> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    private static void bookRoom(Scanner scanner, Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        System.out.println("Would you like to make a reservation? y/n");
        String bookRoom = scanner.nextLine();

        if ("y".equals(bookRoom)) {
            System.out.println("Do you have an account with us? y/n");
            String haveAccount = scanner.nextLine();

            if ("y".equals(haveAccount)) {
                System.out.println("Enter email format: name@domain.com");
                String customerEmail = scanner.nextLine();

                if (HotelResource.getCustomer(customerEmail) == null) {
                    System.out.println("Customer not found.\nYou may need to create a new account.");
                } else {
                    System.out.println("What room number would you like to reserve?");
                    String roomNumber = scanner.nextLine();

                    if (rooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber))) {
                        IRoom room = HotelResource.getRoom(roomNumber);

                        Reservation reservation = HotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
                        System.out.println("Reservation created successfully!");
                        System.out.println(reservation);
                    } else {
                        System.out.println("Room not available.\nStart reservation again.");
                    }
                }
                showMainMenu();
            } else {
                System.out.println("Create an account.");
                showMainMenu();
            }
        } else if ("n".equals(bookRoom)) {
            showMainMenu();
        } else {
            bookRoom(scanner, checkInDate, checkOutDate, rooms);
        }
    }

    private static void seeMyReservations() {
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        while (isValidEmail(email)) {
            System.out.println("Please enter a valid email.");
            email = scanner.nextLine();
        }

        printReservations(HotelResource.getCustomersReservations(email));
    }

    private static void printReservations(Collection<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            reservations.forEach(reservation -> System.out.println("\n" + reservation));
        }
    }

    private static void createAnAccount() {
        System.out.println("Please enter your First name:");
        String firstName = scanner.nextLine();
        while (isValidName(firstName)) {
            System.out.println("Name should begin with a letter.");
            firstName = scanner.nextLine();
        }

        System.out.println("Please enter your Last name:");
        String lastName = scanner.nextLine();
        while (isValidName(lastName)) {
            System.out.println("Name should begin with a letter.");
            lastName = scanner.nextLine();
        }

        System.out.println("Please enter your Email: name@domain.com");
        String email = scanner.nextLine();
        while (isValidEmail(email)) {
            System.out.println("Please enter a valid email.");
            email = scanner.nextLine();
        }

        try {
            HotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Welcome " + firstName + "!");
            takeABreak();
            System.out.println("Account creation successful!");
            takeABreak();
            showMainMenu();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
            createAnAccount();
        }
    }

    public static boolean isValidName(String name) {
        String REGEX = "^[A-Za-z]*$";
        Pattern NAME = Pattern.compile(REGEX);
        return NAME.matcher(name).matches();
    }
    public static boolean isValidEmail(String email) {
        String REGEX = "^(.+)@(.+).com$";
        Pattern EMAIL = Pattern.compile(REGEX);
        return EMAIL.matcher(email).matches();
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
