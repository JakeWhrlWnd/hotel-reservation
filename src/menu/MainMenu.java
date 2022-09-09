package menu;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {

    private final static Scanner scanner = new Scanner(System.in);
    private final static SimpleDateFormat bookingDateFormat = new SimpleDateFormat("MM/dd/yyyy");
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
        try {
            Date checkIn = getCheckInDate();
            Date checkOut = getCheckOutDate();
            String email = getEmail();

            if (checkIn != null && checkOut != null) {
                Collection<IRoom> availableRooms = HotelResource.findARoom(checkIn, checkOut);
            }
        }

        while (availableRooms.isEmpty()) {
            System.out.println("No available rooms for these dates.");
            System.out.println("Here are some recommendations.");

            Calendar calendar = new GregorianCalendar();

            assert checkInDate != null;
            calendar.setTime(checkInDate);
            calendar.add(Calendar.DATE, 7);
            checkInDate = calendar.getTime();

            assert checkOutDate != null;
            calendar.setTime(checkOutDate);
            calendar.add(Calendar.DATE, 7);
            checkOutDate = calendar.getTime();

            availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);
            System.out.println("Current vacancies from" + checkInDate + " to " + checkOutDate);
        }

        for (IRoom room : availableRooms) {
            System.out.println(room);
        }

        String email;
        System.out.println("Do you have an account with us? (y/n)");
        char userChoice = scanner.nextLine().trim().charAt(0);
        if (userChoice == 'y') {
            System.out.println("Welcome back! Enter your email: (name@domain.com)");
            String tempEmail = scanner.nextLine();
            Customer customer = AdminResource.getCustomer(tempEmail);
            if (customer == null) {
                email = createAnAccount();
            } else {
                email = tempEmail;
            }
        } else if (userChoice == 'n') {
            email = createAnAccount();
        } else {
            System.out.println("Entry not valid! Please input y/n.");
            return;
        }

        System.out.println("What room number would you like to reserve?");
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a room number.");
            scanner.next();
        }

        while (true) {
            IRoom theRoom = HotelResource.getRoom(scanner.nextLine());
            if (availableRooms.contains(theRoom)) {
                Reservation theReservation = HotelResource.bookARoom(email, theRoom, checkInDate, checkOutDate);
                System.out.println(theReservation);
                break;
            } else {
                System.out.println("Room number is invalid. Please try again.");
            }
        }
    }

    private static Date getCheckInDate() {
        System.out.println("Enter check-in date as MM/DD/YYYY.");
        String checkInDateStr = scanner.nextLine();
        Date checkInDate = null;
        try{
            return checkInDate = bookingDateFormat.parse(checkInDateStr);
        } catch (Exception e) {
            System.out.println("Date format invalid, MM/DD/YYYY");
        }
        return checkInDate;
    }

    private static Date getCheckOutDate() {
        System.out.println("Enter check-out date as MM/DD/YYYY.");
        String checkOutDateStr = scanner.nextLine();
        Date checkOutDate = null;
        try{
            checkOutDate = bookingDateFormat.parse(checkOutDateStr);
        } catch (Exception e) {
            System.out.println("Date format invalid, MM/DD/YYYY");
        }
        return checkOutDate;
    }

    private static void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        while (isNotValidEmail(email)) {
            System.out.println("Please enter a valid email.");
            email = scanner.nextLine();
        }

        if (HotelResource.getCustomer(email) != null) {
            for (Reservation reservation : HotelResource.getCustomersReservations(email)) {
                System.out.println(reservation);
            }
        } else {
            System.out.println("No reservation found.");
            System.out.println("Would you like to book a room? (y/n)");
            String userChoice = scanner.nextLine();
            if (userChoice.toLowerCase().charAt(0) == 'y') {
                findAndReserveARoom();
            } else {
                showMainMenu();
            }
        }
    }

    private static String createAnAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your First name:");
        String firstName = scanner.nextLine();
        while (isNotValidName(firstName)) {
            System.out.println("Name should begin with a letter.");
            firstName = scanner.nextLine();
        }

        System.out.println("Please enter your Last name:");
        String lastName = scanner.nextLine();
        while (isNotValidName(lastName)) {
            System.out.println("Name should begin with a letter.");
            lastName = scanner.nextLine();
        }

        System.out.println("Please enter your Email: name@domain.com");
        String email = scanner.nextLine();
        while (isNotValidEmail(email)) {
            System.out.println("Please enter a valid email.");
            email = scanner.nextLine();
        }

        try {
            HotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Welcome, account created successfully!");
            showMainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return email;
    }

    public static boolean isNotValidName(String name) {
        String REGEX = "^[A-Za-z]*$";
        Pattern NAME = Pattern.compile(REGEX);
        return !NAME.matcher(name).matches();
    }
    public static boolean isNotValidEmail(String email) {
        String REGEX = "^(.+)@(.+).com$";
        Pattern EMAIL = Pattern.compile(REGEX);
        return !EMAIL.matcher(email).matches();
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
