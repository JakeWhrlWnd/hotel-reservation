package menu;

import api.HotelResource;
import api.AdminResource;
import com.sun.net.httpserver.Authenticator;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MainMenu {
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
        Do you have an account?
            If yes, then login with email
                If email is not recognized - create an account
                If email is recognized - Welcome back message
            If no, then create an account

    }

    private static void getCustomerReservations() {
    }

    private static void createAnAccount() {
        String email = "";
        String firstName = "";
        String lastName = "";

        do {
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
        } while (!flag);

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
    public staic Customer getCustomer() {
        return customer;
    }

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
