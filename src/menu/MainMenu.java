package menu;

import api.HotelResource;
import api.AdminResource;
import com.sun.net.httpserver.Authenticator;
import com.sun.tools.javac.Main;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static api.HotelResource.findARoom;
import static api.HotelResource.getCustomer;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    protected static boolean flag = true;
    // Main Menu constant
    protected static final String MAIN_MENU_TEXT = """
            MAIN MENU
            -----------------------------------------------
            1. Find and Reserve a room
            2. See my reservations
            3. Create an account
            4. Admin
            5. EXIT
            -----------------------------------------------
            Please choose an option from the menu""";
    /**
     * Option Menu #1 constant
     * primarily used with the Find & Reserve option
     */
    protected static final String OPTIONS_MENU_1 = """
            What would you like to do?
            -----------------------------------------------
            1. Reserve a room
            2. Enter new dates
            3. Return to the main menu
            4. EXIT
            -----------------------------------------------
            Please choose an option from the menu""";
    /**
     * Option Menu #2 constant
     * primarily reached when accessing Reservations
     */
    protected static final String OPTIONS_MENU_2 = """
            What would you like to do?
            -----------------------------------------------
            1. Login to account
            2. Create a new account
            3. Return to the main menu
            4. EXIT
            -----------------------------------------------
            Please choose an option from the menu""";
    /**
     * Option Menu #3 constant
     * primarily used as a return menu
     */
    protected static final String OPTIONS_MENU_3 = """
            What would you like to do?
            -----------------------------------------------
            1. Return to the main menu
            2. EXIT
            -----------------------------------------------
            Please choose an option from the menu""";

    private static final Date today = new Date();
    private static final Date oneYearFromToday = Date.from(LocalDate.now().plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    protected static final String DATE_FORMAT = "MM/dd/yyyy";
    private static Date checkInDate;
    public static Date getCheckInDate() {
        return checkInDate;
    }
    public static void setCheckInDate(Date checkInDate) {
        MainMenu.checkInDate = checkInDate;
    }

    public static Date checkOutDate;
    public static Date getCheckOutDate() { return checkOutDate; }
    public static void setCheckOutDate(Date checkOutDate) {
        MainMenu.checkOutDate = checkOutDate;
    }




    public static void showMainMenu() {
        System.out.println(MAIN_MENU_TEXT);
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
        boolean isReserved = false;

        while (!isReserved) {
            Collection<IRoom> availableRooms = findARoom();

            Map<Integer, IRoom> roomCount = new HashMap<>();
            int item = 1;
            for (IRoom room : availableRooms) {
                roomCount.put(item, room);
                item++;
            }

            int numberOfRooms = roomCount.size();

            while (availableRooms.isEmpty()) {
                Calendar calendar = Calendar.getInstance();

                calendar.setTime(checkInDate);
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                Date newCheckInDate = Date.from(calendar.toInstant());

                calendar.setTime(checkOutDate);
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                Date newCheckOutDate = Date.from(calendar.toInstant());

                availableRooms = HotelResource.findARoom(newCheckInDate, newCheckOutDate);

                System.out.println("There are no available rooms from "
                        + standardDate.format(checkInDate) + " to " + standardDate.format(checkOutDate));
                System.out.println("We suggest you try booking rooms from "
                        + standardDate.format(newCheckInDate) + " to " + standardDate.format(newCheckOutDate));
            }

            int option;

            for (Map.Entry entry : roomCount.entrySet()) {
                System.out.println(entry);
            }

        }
    }

    private static void getCustomerReservations(String email) {
        if (customer == null) {
            loginOrCreateAccount();
        }
        Collection<Reservation> reservations = HotelResource.getCustomerReservation(email);
        if (reservations == null) {
            System.out.println("No reservations found for this account.");
            takeABreak();
            System.out.println(OPTIONS_MENU_3);
            try {
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1 -> MainMenu.showMainMenu();   // 1. Return to Main Menu
                    case 2 -> flag = false;              // 2. Exit application
                    default -> throw new IllegalArgumentException("Invalid input. Please, enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Only numbers accepted.");
            } catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    private static void createAnAccount() {
        String email = "";
        String firstName = "";
        String lastName = "";
        boolean emailIsValid = false;

        System.out.println("""
            Please, enter your First name:
            (Must begin with a capital letter)
            """);
        firstName = scanner.nextLine();

        System.out.println("""
            Please, enter your Last name:
            (Must begin with a capital letter)
            """);
        lastName = scanner.nextLine();

        // Validate Email
        while (!emailIsValid) {
            try {
                System.out.println("""
            Please, enter your Email:
            (Format must be - name@domain.com
            """);
                email = scanner.nextLine();
                HotelResource.createACustomer(firstName, lastName, email);
                emailIsValid = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Email format must be - name@domain.com");
            }
        }

        setCustomer(HotelResource.getCustomer(email));
        System.out.println("Account creation successful. Welcome " + getCustomer().getFirstName() + "!");
    }

    public static void takeABreak() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static Customer customer;
    public static Customer getCustomer() {
        return customer;
    }
    public static void setCustomer(Customer customer) {
        MainMenu.customer = customer;
    }
    public static void exitApp() {
        System.out.println("Goodbye and have a great day!");
        scanner.close();
        System.exit(0);
    }
}
