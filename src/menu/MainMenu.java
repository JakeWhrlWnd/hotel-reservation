package menu;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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

    public static void showMainMenu() {
        System.out.println(MAIN_MENU_TEXT);
        while (flag) {
            try {
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1 -> reserveARoom();            // 1. Find and reserve a room
                    case 2 -> getCustomerReservation(customer.getEmail()); // 2. See my reservations
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

    public static void showRoomMenu() {
        System.out.println(OPTIONS_MENU_1);
        while(flag) {
            try {
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1 -> reserveARoom();            // 1. Reserve a room
                    case 2 -> findARoom();               // 2. Enter check-in and check-out dates
                    case 3 -> MainMenu.showMainMenu();   // 3. Return to Main Menu
                    case 4 -> flag = false;              // 4. Exit application
                    default -> throw new IllegalArgumentException("Invalid input. Please, enter a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Only numbers accepted.");
            } catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        }
    }

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

    public static void showAccountMenu() {
        System.out.println(OPTIONS_MENU_2);
        while(flag) {
            try {
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1 -> loginToAccount();          // 1. Login to existing account
                    case 2 -> createAnAccount();         // 2. Create a new account
                    case 3 -> MainMenu.showMainMenu();   // 3. Return to Main Menu
                    case 4 -> flag = false;              // 4. Exit application
                    default -> throw new IllegalArgumentException("Invalid input. Please, enter a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Only numbers accepted.");
            } catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Option Menu #4 constant
     * primarily used as a return menu
     */
    protected static final String OPTIONS_MENU_3 = """
            What would you like to do?
            -----------------------------------------------
            1. Return to the main menu
            2. EXIT
            -----------------------------------------------
            Please choose an option from the menu""";

    public static void showExitMenu() {
        System.out.println(OPTIONS_MENU_3);
        while(flag) {
            try {
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1 -> MainMenu.showMainMenu();   // 1. Return to Main Menu
                    case 2 -> flag = false;              // 2. Exit application
                    default -> throw new IllegalArgumentException("Invalid input. Please, enter a number, 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Only numbers accepted.");
            } catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        }
    }

    protected static final String CHECK_IN_MENU = """
            RESERVATION CHECK-IN DATE:
            -----------------------------------------------
            Please, enter desired check-in date:
            (Format must be MM/DD/YYYY) - example: 11/24/1974
            -----------------------------------------------
            """;
    protected static final String CHECK_OUT_MENU = """
            RESERVATION CHECKOUT DATE:
            -----------------------------------------------
            Please, enter desired checkout date:
            (Format must be MM/DD/YYYY) - example: 11/24/1974
            -----------------------------------------------
            """;

    private static final Date today = new Date();
    private static final Date oneYearFromToday = Date.from(LocalDate.now().plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    private static final SimpleDateFormat standardDate = new SimpleDateFormat("MM/dd/yyyy");
    protected static final String DATE_FORMAT = "MM/dd/yyyy";
    private static Date checkInDate;
    // public static Date getCheckInDate() { return checkInDate; }
    public static void setCheckInDate(Date checkInDate) {
        MainMenu.checkInDate = checkInDate;
    }

    public static Date checkOutDate;
    // public static Date getCheckOutDate() { return checkOutDate; }
    public static void setCheckOutDate(Date checkOutDate) {
        MainMenu.checkOutDate = checkOutDate;
    }

    private static Reservation reserveARoom() {
        boolean isReserved = false;

        while (!isReserved) {
            Collection<IRoom> availableRooms = findARoom();

            Map<Integer, IRoom> roomCount = new HashMap<>();
            int item = 1;
            for (IRoom room : availableRooms) {
                roomCount.put(item, room);
                item++;
            }

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

            for (Map.Entry entry : roomCount.entrySet()) {
                System.out.println(entry);
            }
            showRoomMenu();
            int option = Integer.parseInt(scanner.nextLine());
            chosenRoom = roomCount.get(option);
            System.out.println("""
                    Room reservation successful!
                    You have reserved room""" + chosenRoom.getRoomNumber() + ".");
        }
        return HotelResource.bookARoom(customer.getEmail(), chosenRoom, checkInDate, checkOutDate);
    }

    public static Collection<IRoom> findARoom() {
        String checkIn;
        String checkOut;
        boolean checkInDateIsValid = false;
        boolean checkOutDateIsValid = false;
        do {
            try {
                do {
                    System.out.println(CHECK_IN_MENU);
                    checkIn = scanner.nextLine();
                    setCheckInDate(new SimpleDateFormat(DATE_FORMAT).parse(checkIn));
                    checkInDateIsValid = (today.equals(checkInDate) ||
                            (today.before(checkInDate)) && oneYearFromToday.after(checkInDate));
                    if (!checkInDateIsValid) {
                        System.out.println("Date entered must between " + today + " and " + oneYearFromToday + ".");
                    }
                } while (!checkInDateIsValid);

                do {
                    System.out.println(CHECK_OUT_MENU);
                    checkOut = scanner.nextLine();
                    setCheckOutDate(new SimpleDateFormat(DATE_FORMAT).parse(checkOut));
                    checkOutDateIsValid = checkInDate.before(checkOutDate) && oneYearFromToday.after(checkOutDate);
                    if (!checkOutDateIsValid) {
                        System.out.println("Date entered must between " + checkInDate + " and " + oneYearFromToday + ".");
                    }
                } while (!checkOutDateIsValid);
            } catch (Exception e) {
                System.out.println("Date is invalid.");
                scanner.nextLine();
            }
        } while (!(checkInDateIsValid && checkOutDateIsValid));
        return HotelResource.findARoom(checkInDate, checkOutDate);
    }

    private static void getCustomerReservation(String email) {
        if (customer == null) {
            showAccountMenu();
        }
        Collection<Reservation> reservations = HotelResource.getCustomerReservation(email);
        if (reservations == null) {
            System.out.println("No reservations found for this account.");
            takeABreak();
            showExitMenu();
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    private static void createAnAccount() {
        System.out.println("""
            Please, enter your First name:
            (Must begin with a capital letter)""");
        String firstName = scanner.nextLine();

        System.out.println("""
            Please, enter your Last name:
            (Must begin with a capital letter)""");
        String lastName = scanner.nextLine();

        System.out.println("""
            Please, enter your Email:
            (Format must be - name@domain.com""");
        String email = scanner.nextLine();

        try {
            HotelResource.createACustomer(email, firstName, lastName);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            createAnAccount();
        }

        System.out.println("Account creation successful. Welcome " + firstName + "!");
        takeABreak();
        showExitMenu();
    }

    public static void loginToAccount() {
        System.out.println("Please enter your email address: ");
        String email = scanner.nextLine();
        if (HotelResource.getCustomer(email) == null) {
            System.out.println("Unfortunately, no account was found with this email.");
            takeABreak();
            showAccountMenu();
        }
        setCustomer(HotelResource.getCustomer(email));
        System.out.println("Welcome back " + customer.getFirstName() + "!");
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

    public static IRoom chosenRoom;
}
