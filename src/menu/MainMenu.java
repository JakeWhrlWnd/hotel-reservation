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

public class MainMenu {
    // Main Menu constant
    protected static final String MAIN_MENU_TEXT = """
            Welcome to the Hotel Reservation Application
            -----------------------------------------------
            1. Find and Reserve a room
            2. See my reservations
            3. Create an account
            4. Admin
            5. Exit
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
            4. Exit
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
            4. Exit
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
            2. Exit
            -----------------------------------------------
            Please choose an option from the menu""";

    private static final Scanner scanner = new Scanner(System.in); // Scanner create for input

    private static final Date today = new Date();
    private static final Date oneYearFromToday = Date.from(localDate.now().plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
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

    private static void findAndReserveARoom() {}

    private static void getCustomerReservations() {}

    private static void createAnAccount() {
        String email = "";
        String firstName = "";
        String lastName = "";

        while (!flag){
            // Validate First Name
            System.out.println("""
                Please, enter your First name:
                (Must begin with a capital letter)
                """);
            try {
                firstName = scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Name must begin with a capital letter.");
            } catch (IllegalArgumentException e) {
                System.out.println("Name must consist of letters.");
            }

            // Validate Last Name
            System.out.println("""
                Please, enter your Last name:
                (Must begin with a capital letter)
                """);
            try {
                lastName = scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Name must begin with a capital letter.");
            } catch (IllegalArgumentException e) {
                System.out.println("Name must consist of letters.");
            }

            // Validate Email
            System.out.println("""
                Please, enter your Email:
                (Format must be - name@domain.com
                """);
            try {
                email = scanner.nextLine();
                HotelResource.createACustomer(firstName, lastName, email);
                flag = true;
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
    protected static boolean flag = true;

    private static Customer customer;

    public static void setCustomer(Customer customer) {
        MainMenu.customer = customer;
    }
}
