package menu;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
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
        hasAccount();

        Date checkInDate = getDateEntry("When would you like to check-in: (MM/DD/YYYY)");
        Date checkOutDate = getDateEntry("When would you like to check-out: (MM/DD/YYYY)");

        Collection<IRoom> availableRooms = hotelResource.findAvailableRooms(checkInDate, checkOutDate);
        if (availableRooms.size() == 0) {
            final Date alternateCheckInDate = hotelResource.addDefaultDays(checkInDate);
            final Date alternateCheckOutDate = hotelResource.addDefaultDays(checkInDate);
            Collection<IRoom> alternateRooms = hotelResource.findAvailableRooms(alternateCheckInDate, alternateCheckOutDate);
            if (alternateRooms.size() == 0) {
                System.out.println("No available rooms");
                takeABreak();
                showMainMenu();
            }
            availableRooms = alternateRooms;
            checkInDate = alternateCheckInDate;
            checkOutDate = alternateCheckOutDate;
            System.out.println("Here are some available alternate dates:\nCheck-in Date(s): " + alternateCheckInDate + "\nCheck-out Date(s): " + alternateCheckOutDate);
        }
        printRooms(availableRooms);

//        if (isYesResponse("Would you like to book a room? (y/n)")) {
//            reserveARoom(checkInDate, checkOutDate, availableRooms);
//        }
    }

    private static Date getDateEntry(String message) {
        boolean isException;
        Date validDate = null;

        do {
            System.out.println(message);
            final String d = scanner.nextLine();
            try {
                isException = false;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate formattedDate = LocalDate.parse(d, formatter);
                validDate = Date.from(formattedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            } catch (DateTimeParseException e) {
                isException = true;
                System.out.println("Date is invalid");
            }
           } while (isException);

        return validDate;
    }

    private static void hasAccount() {
        System.out.println("Do you have an account? (y/n)");
        String hasAccount = scanner.nextLine();
        if ("y".equals(hasAccount)) {
            System.out.println("Enter email associated with account. (format: name@domain.com");
            final String enteredEmail = scanner.nextLine();
            if (hotelResource.getCustomer(enteredEmail) == null) {
                System.out.println("Email not found. Please create an account.");
                takeABreak();
                createAnAccount();
            }
        } else if ("n".equals(hasAccount)) {
            System.out.println("Please create an account.");
            takeABreak();
            createAnAccount();
        }
    }
//    private static void reserveARoom(Date checkInDate, Date checkOutDate, Collection<IRoom> availableRooms) {
//        final boolean isAccountExist = isYesResponse("Do you have an account with us? (y/n)");
//        if (!isAccountExist) {
//            System.out.println("Please create an account");
//            return;
//        }
//
//        System.out.println("Enter account email (format: name@domain.com)");
//        final String enteredEmail = scanner.nextLine();
//
//        if (hotelResource.getCustomer(enteredEmail) == null) {
//            System.out.println("Customer not found. Please create an account");
//            return;
//        }
//
//        final String reserveRoomNumber = getRoomNumber(availableRooms);
//
//        Reservation reservation = hotelResource.bookARoom(enteredEmail, reserveRoomNumber, checkInDate, checkOutDate);
//
//        if (reservation != null) {
//            System.out.println("Reservation successful! Enjoy your stay");
//            takeABreak();
//            System.out.println(reservation);
//            takeABreak();
//            showMainMenu();
//        }
//    }

    private static void printRooms(Collection<IRoom> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    private static void getCustomerReservations() {
        String enteredEmail = getEmail();
        printReservations(hotelResource.getCustomerReservation(enteredEmail));
    }

    private static void printReservations(Collection<Reservation> reservations) {
        if (reservations == null || reservations.size() == 0) {
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
        String email = null;
        System.out.println("Please, enter your Email: (name@domain.com)");
        try {
            email = scanner.nextLine();
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        return email;
    }

    private static String getFirstName() {
        System.out.println("Please, enter your First name:");
        return scanner.nextLine();
    }

    private static String getLastName() {
        System.out.println("Please, enter your Last name:");
        return scanner.nextLine();
    }

    public static void takeABreak() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getRoomNumber(Collection<IRoom> availableRooms) {
        String roomNumber;
        boolean isError;

        do {
            System.out.println("What room number would you like to reserve?");
            roomNumber = scanner.nextLine();
            isError = false;

            if (roomNumber.isBlank() || hotelResource.getRoom(roomNumber) == null || hotelResource.isRoomExistInAvailable(roomNumber, availableRooms)) {
                isError = true;
                System.out.println("Sorry no room exists");
            }
        }while (isError);

        return roomNumber;
    }
    static boolean isYesResponse(String question) {
        System.out.println(question);
        final String response = scanner.nextLine();

        if ("y".equalsIgnoreCase(response)) {
            return true;
        } else if ("n".equalsIgnoreCase(response)) {
            return false;
        } else {
            return isYesResponse("Please, enter Y (yes) or N (no)");
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
