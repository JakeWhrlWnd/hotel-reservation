package menu;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String RESPONSE_REGEX = "^[YNyn]$";
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
                    case 1 -> findAndReserveRoom();      // 1. Find and reserve a room
                    case 2 -> getCustomerReservation();  // 2. See my reservations
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
            2. Return to the main menu
            3. EXIT
            -----------------------------------------------
            Please choose an option from the menu""";

    public static void showRoomMenu() {
        System.out.println(OPTIONS_MENU_1);
        while(flag) {
            try {
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1 -> findAndReserveRoom();      // 1. Reserve a room
                    case 2 -> MainMenu.showMainMenu();   // 2. Return to Main Menu
                    case 3 -> flag = false;              // 3. Exit application
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
            1. Create a new account
            2. Return to the main menu
            3. EXIT
            -----------------------------------------------
            Please choose an option from the menu""";

    public static void showAccountMenu() {
        System.out.println(OPTIONS_MENU_2);
        while(flag) {
            try {
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1 -> createAnAccount();         // 1. Create a new account
                    case 2 -> MainMenu.showMainMenu();   // 2. Return to Main Menu
                    case 3 -> flag = false;              // 3. Exit application
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
            (Format must be MM/DD/YYYY) - example: 07/24/1986
            -----------------------------------------------""";
    protected static final String CHECK_OUT_MENU = """
            RESERVATION CHECKOUT DATE:
            -----------------------------------------------
            Please, enter desired checkout date:
            (Format must be MM/DD/YYYY) - example: 07/24/1986
            -----------------------------------------------""";
    private static void findAndReserveRoom() {
        boolean isValidResponse;

        System.out.println(CHECK_IN_MENU);
        String strCheckInDate = scanner.nextLine();
        Date checkInDate = isValidDate(strCheckInDate);

        System.out.println(CHECK_OUT_MENU);
        String strCheckOutDate = scanner.nextLine();
        Date checkOutDate = isValidDate(strCheckOutDate);

        Collection<IRoom> requestedRooms = HotelResource.findARoom(checkInDate, checkOutDate);
        if (requestedRooms.size() == 0) {
            Collection<IRoom> recommendedRooms = HotelResource.findAlternativeRooms(checkInDate, checkOutDate);
            if (recommendedRooms.size() == 0) {
                System.out.println("""
            Sorry, no rooms are available for your dates.
            Please, try different dates.""");
                findAndReserveRoom();
            } else {
                Date alternativeCheckIn = addAdditionalDays(checkInDate);
                Date alternativeCheckOut = addAdditionalDays(checkOutDate);
                System.out.println("Sorry, no rooms available for your dates."
                        + "\nAvailable check-in dates: " + alternativeCheckIn
                        + "\nAvailable checkout dates: " + alternativeCheckOut);
                showRooms(recommendedRooms);
                do {
                    System.out.println("Would you like to book these dates? (Y(es) or N(o))");
                    String bookNewDates = scanner.nextLine();
                    isValidResponse = bookNewDates.matches(RESPONSE_REGEX);
                    if (isValidResponse) {
                        if (bookNewDates.equalsIgnoreCase("y")) {
                            reserveARoom(alternativeCheckIn, alternativeCheckOut, recommendedRooms);
                        } else {
                            System.out.println("Sorry for the inconvenience.");
                            showExitMenu();
                        }
                    } else {
                        System.out.println("Sorry, input is invalid.");
                    }
                } while (!isValidResponse);
            }
        } else {
            showRooms(requestedRooms);
            System.out.println("Would you like to book room for these dates? (Y(es) or N(0))");
            String bookRoom = scanner.nextLine();
            isValidResponse = bookRoom.matches(RESPONSE_REGEX);
            if (isValidResponse) {
                if (bookRoom.equalsIgnoreCase("y")) {
                    reserveARoom(checkInDate, checkOutDate, requestedRooms);
                } else {
                    showExitMenu();
                }
            } else {
                System.out.println("Sorry, input is invalid.");
                showExitMenu();
            }
        }
    }

    private static void reserveARoom(Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        boolean isValidAccount;
        String roomAvailable = "false";

        System.out.println("Do you have an account with us? (Y(es) or N(o))");
        String hasAccount = scanner.nextLine();
        isValidAccount = hasAccount.matches(RESPONSE_REGEX);

        if (hasAccount.equalsIgnoreCase("y")) {
            System.out.println("Please, enter email associated with your account. (format: name@domain.com");
            String customerEmail = scanner.nextLine();
            if (HotelResource.getCustomer(customerEmail) == null) {
                System.out.println("No account found. Please, create a new account.");
                showAccountMenu();
            } else {
                System.out.println("Please, enter the room number you want to reserve.");
                String roomNumber = scanner.nextLine();
                for (IRoom eachRoom : rooms) {
                    if (eachRoom.getRoomNumber().equals(roomNumber)) {
                        roomAvailable = "true";
                        IRoom room = HotelResource.getRoom(roomNumber);
                        final Reservation reservation = HotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
                        System.out.println("Room reservation successful!");
                        System.out.println("Reservation: ");
                        System.out.println(HotelResource.getCustomer(customerEmail).getFirstName()
                                + " " + HotelResource.getCustomer(customerEmail).getLastName());
                        System.out.println("Room: " + room.getRoomNumber()
                                + "\nRoom type: " + room.getRoomType()
                                + "\nPrice per night: " + room.getRoomPrice());
                        System.out.println("Check-in date: " + checkInDate);
                        System.out.println("Checkout date: " + checkOutDate);
                        break;
                    }
                }
                if (roomAvailable.equals("false")) {
                    System.out.println("Unfortunately, room is not available.");
                }
                takeABreak();
                showExitMenu();
            }
        } else {
            System.out.println("Please, create an account and try booking again.");
            showAccountMenu();
        }
    }

    private static void getCustomerReservation() {
        System.out.println("Please, enter your email. (format: name@domain.com)");
        final String customerEmail = scanner.nextLine();
        int count = 0;
        if (HotelResource.getCustomerReservations(customerEmail) == null || HotelResource.getCustomerReservations(customerEmail).isEmpty()) {
            System.out.println("Sorry, no reservations found for email: " + customerEmail);
        } else {
            for (Reservation reservation : HotelResource.getCustomerReservations(customerEmail)) {
                count++;
                System.out.println("Reservation(" + count +")");
                System.out.println("Customer: " + reservation.getCustomer().getFirstName() + " " + reservation.getCustomer().getLastName());
                System.out.println("Room: " + reservation.getRoom().getRoomNumber()
                        + "\nRoom type: " + reservation.getRoom().getRoomType()
                        + "\nPrice per night: " + reservation.getRoom().getRoomPrice());
                System.out.println("Check-in date: " + reservation.getCheckInDate());
                System.out.println("Checkout date: " + reservation.getCheckOutDate());
            }
        }
        takeABreak();
        showExitMenu();
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

    public static void takeABreak() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date addAdditionalDays(Date date) {
        int addedDays = 7;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, addedDays);
        return c.getTime();
    }

    public static Date isValidDate(String strDate) {
        SimpleDateFormat defaultDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        defaultDateFormat.setLenient(false);
        try {
            Date formattedDate = defaultDateFormat.parse(strDate);
            return formattedDate;
        } catch (ParseException e) {
            System.out.println(strDate + " is an invalid date format. (format: MM/DD/YYYY)");
            findAndReserveRoom();
        }
        return null;
    }

    public static void showRooms(final Collection<IRoom> rooms) {
        int count = 0;
        for (IRoom room : rooms) {
            count++;
            System.out.println("Available rooms:");
            System.out.println("(" + count + ")");
            System.out.println("Room number: " + room.getRoomNumber()
                    + "\nRoom type: " + room.getRoomType()
                    + "\nPrice per night: " + room.getRoomPrice());
        }
    }
}
