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
    private static final Scanner scanner = new Scanner(System.in);
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

        while(!isReserved) {
            Collection<IRoom> availableRooms = findARoom();

            Map<Integer, IRoom> countRooms = new HashMap<>();
            int item = 1;
            for (IRoom room : availableRooms) {
                countRooms.put(item, room);
                item++;
            }

            int numberOfRooms = countRooms.size();

            while (availableRooms.isEmpty()) {
                Calendar calendar = Calendar.getInstance();

                calendar.setTime(checkInDate);
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                Date newCheckInDate = Date.from(calendar.toInstant());

                calendar.setTime(checkOutDate);
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                Date newCheckOutDate = Date.from(calendar.toInstant());

                availableRooms = HotelResource.findARoom(newCheckInDate, newCheckOutDate);

                System.out.println("There are no more available rooms from " + standardDate.format(checkInDate) + " to " + standardDate.format(checkOutDate));
                System.out.println("Here are some optional dates available" + standardDate.format(newCheckInDate) + " to " + standardDate.format(newCheckOutDate));
            }

            System.out.println(OPTIONS_MENU_1);
            while (flag) {
                try {
                    int userInput = Integer.parseInt(scanner.nextLine());
                    switch (userInput) {
                        case 1 -> findAndReserveARoom();     // 1. Reserve a room
                        case 2 -> getCustomerReservations(); // 2. Enter new dates
                        case 3 -> MainMenu.showMainMenu(); // 4. Open the Main Menu
                        case 4 -> flag = false;              // 4. Exit application
                        default -> throw new IllegalArgumentException("Invalid input. Please, enter a number between 1 and 4.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Only numbers accepted.");
                } catch (Throwable e) {
                    System.out.println(e.getMessage());
                }
            }

            boolean optionIsValid = false;

            while (!optionIsValid) {
                try {
                    System.out.println("Please choose a number from the list.");
                    option = scanner.nextInt();
                    optionIsValid = (option >= 1) && (option <= numberOfRooms);
                    if (!optionIsValid) {
                        System
                    }
                }
            }
        }
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

    protected static final String DATE_FORMAT = "MM/dd/yyyy";

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

    protected static final String OPTIONS_MENU_1 = """
            What would you like to do?
            -----------------------------------------------
            1. Reserve a room
            2. Enter new dates
            3. Return to the main menu
            4. Exit
            -----------------------------------------------
            Please choose an option from the menu""";

    protected static final String OPTIONS_MENU_2 = """
            What would you like to do?
            -----------------------------------------------
            1. Login to account
            2. Create a new account
            3. Return to the main menu
            4. Exit
            -----------------------------------------------
            Please choose an option from the menu""";
}
