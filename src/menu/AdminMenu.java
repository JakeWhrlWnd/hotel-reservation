package menu;

import api.AdminResource;
import model.*;

import java.util.*;

public class AdminMenu {
    private static final Scanner scanner = new Scanner(System.in);
    protected static boolean flag = true;
    protected static final String ADMIN_MENU = """
            ADMIN MENU
            -----------------------------------------------
            1. See all Customers
            2. See all Rooms
            3. See all Reservations
            4. Add a Room
            5. Back to Main Menu
            6. EXIT
            -----------------------------------------------
            Please choose an option from the menu""";

    public static void showAdminMenu() {
        System.out.println(ADMIN_MENU);
        int userInput = Integer.parseInt(scanner.nextLine());
        try {
            switch (userInput) {
                case 1 -> seeAllCustomers();        // 1. See all Customers
                case 2 -> seeAllRooms();            // 2. See all Rooms
                case 3 -> seeAllReservations();     // 3. See all Reservations
                case 4 -> addARoom();                // 4. Add a Room
                case 5 -> MainMenu.showMainMenu();  // 5. Return to Main Menu
                case 6 -> flag = false;               // 6. Exit App
                default -> throw new IllegalArgumentException("Input not valid: " + userInput);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Only numbers accepted.");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    protected static final String ROOM_NUMBER_MENU = """
            ROOM NUMBER MENU
            -----------------------------------------------
            Enter the Room number:
            (Room number must be greater than 0) - example 144
            -----------------------------------------------""";
    protected static final String ROOM_PRICE_MENU = """
            ROOM PRICE MENU
            -----------------------------------------------
            Enter the Room price:
            (Format must be X.XX) - example 123.00
            -----------------------------------------------""";
    protected static final String ROOM_TYPE_MENU = """
            ROOM TYPE MENU
            -----------------------------------------------
            Select the type of room.
            Enter 1 for a Single Room
            Enter 2 for a Double Room
            -----------------------------------------------""";

    protected static final String OPTION_MENU = """
            What would you like to do?
            -----------------------------------------------
            1. Return to the Admin Menu
            2. Return to the Main Menu
            3. EXIT""";

    public static void showOptionMenu() {
        System.out.println(OPTION_MENU);
        int userInput = Integer.parseInt(scanner.nextLine());
        try {
            switch (userInput) {
                case 1 -> AdminMenu.showAdminMenu(); // 1. Return to Admin menu
                case 2 -> MainMenu.showMainMenu();   // 2. Return to Main menu
                case 3 -> exitApp();                 // 3. Exit App
                default -> throw new IllegalArgumentException("Input not valid: " + userInput);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Only numbers accepted.");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    // Option #1
    private static void seeAllCustomers() {
        int count = 0;
        Collection<Customer> customers = AdminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers were found.");
        } else {
            for (Customer customer : customers) {
                count++;
                System.out.println("(" + count + ")");
                System.out.println("First name: " + customer.getFirstName()
                        + "\nLast name: " + customer.getLastName()
                        + "\nEmail: " + customer.getEmail() + "\n");
            }
        }
        takeABreak();
        showOptionMenu();
    }

    // Option #2
    private static void seeAllRooms() {
        Collection<IRoom> allRooms = AdminResource.getAllRooms();
        int count = 0;
        if (allRooms.isEmpty()) {
            System.out.println("No rooms were found. Please, add available rooms.");
        } else {
            for (IRoom room : allRooms) {
                count++;
                System.out.println("("+ count +")");
                System.out.println("Room number: " + room.getRoomNumber()
                        + "\nRoom type: " + room.getRoomType()
                        + "\nPrice per night: " + room.getRoomPrice() + "\n");
            }
        }
        takeABreak();
        showOptionMenu();
    }

    //Option #3
    private static void seeAllReservations() {
        AdminResource.displayAllReservations();
        takeABreak();
        showOptionMenu();
    }

    private static void addARoom() {
        // Room Number
        System.out.println(ROOM_NUMBER_MENU);
        String roomNumber = enterRoomNumber();

        // Room Price
        System.out.println(ROOM_PRICE_MENU);
        double price = enterPrice();

        // Room Type
        System.out.println(ROOM_TYPE_MENU);
        RoomType roomType = enterRoomType();

        Room room = new Room(roomNumber, price, roomType);
        AdminResource.addRoom(Collections.singletonList(room));
        System.out.println("Success! Room " + roomNumber + " was created.");
        takeABreak();
        addAnotherRoom();
    }


    private static void addAnotherRoom() {
        System.out.println("Would you like to add another room? Y(es) or N(o)");
        String anotherRoom = scanner.nextLine();
        try {
            if (anotherRoom.equalsIgnoreCase("y")) {
                addARoom();
            } else if (anotherRoom.equalsIgnoreCase("n")) {
                showOptionMenu();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println(e + "Input is invalid");
            showAdminMenu();
        }
    }

    private static String enterRoomNumber() {
        try {
            return scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("""
                    Invalid room number format.
                    Room number must be greater than 0.
                    Room number can not be a negative number.
                    Please, enter room number again.""");
            return enterRoomNumber();
        }
    }

    private static double enterPrice() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("""
                    Invalid price format.
                    Price format must be X.XX.
                    Please, enter price again.""");
            return enterPrice();
        }
    }

    private static RoomType enterRoomType() {
        String roomType = scanner.nextLine();

        try {
            return roomType.equals("1") ? RoomType.SINGLE : RoomType.DOUBLE;
        } catch (IllegalArgumentException e) {
            System.out.println("""
                    Invalid room type.
                    Please, choose a 1 for a Single Bed or 2 for a Double Bed.""");
            return enterRoomType();
        }
    }

    public static void takeABreak() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void exitApp() {
        System.out.println("Goodbye and have a great day!");
        scanner.close();
        System.exit(0);
    }
}
