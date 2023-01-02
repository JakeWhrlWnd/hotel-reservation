package menu;

import api.AdminResource;
import model.*;

import java.util.*;

public class AdminMenu {
    private static Collection<Customer> allCustomers = AdminResource.getAllCustomers();
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

    public static void showAdminMenu() {
        System.out.println(ADMIN_MENU);
        try {
            int userInput = Integer.parseInt(scanner.nextLine());
            switch (userInput) {
                case 1 -> seeAllCustomers();        // 1. See all Customers
                case 2 -> seeAllRooms();            // 2. See all Rooms
                case 3 -> seeAllReservations();     // 3. See all Reservations
                case 4 -> addRoom();                // 4. Add a Room
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

    // Option #1
    private static void seeAllCustomers() {
        String firstNameHeader = String.format("%-12s", "First Name:");
        String lastNameHeader = String.format("%-12s", "Last Name:");
        String emailHeader = String.format("%-12s", "Email:");

        System.out.println(firstNameHeader + "|" + lastNameHeader + "|" + emailHeader);

        for (Customer customer: allCustomers) {
            System.out.println(customer);
        }
    }
    // Option #2
    private static void seeAllRooms() {
        String roomNumberHeader = String.format("%-12s", "Room Number:");
        String roomTypeHeader = String.format("%-12s", "Room Type:");
        String roomPriceHeader = String.format("%-12s", "Room Price:");

        System.out.println(roomNumberHeader + "|" + roomTypeHeader + "|" + roomPriceHeader);

        Collection<IRoom> allRooms = AdminResource.getAllRooms();
        for (IRoom room : allRooms) {
            System.out.println(room);
        }
    }

    //Option #3
    private static void seeAllReservations() {
        AdminResource.displayAllReservations();
    }

    private static void addRoom() {
        List<IRoom> rooms = new LinkedList<>();

        while (flag) {
            IRoom room;

            //Room Number
            int roomNumber = 0;
            boolean hasRoomNumber = false;
            while (!hasRoomNumber) {
                System.out.println(ROOM_NUMBER_MENU);
                try {
                    roomNumber = scanner.nextInt();
                    hasRoomNumber = roomNumber > 0;
                } catch (IllegalArgumentException e) {
                    System.out.println("Negative numbers and 0 not acceptable Room numbers");
                    scanner.nextLine();
                }
            }
            //Room Price
            double price = 0.00;
            boolean hasPrice = false;
            while (!hasPrice) {
                System.out.println(ROOM_PRICE_MENU);
                try {
                    price = scanner.nextDouble();
                    hasPrice = price > 0;
                } catch (InputMismatchException e) {
                    System.out.println("Price must be in format X.XX");
                    scanner.nextLine();
                }
            }
            //Room Type
            RoomType roomType = null;
            boolean hasRoomType = false;
            while (!hasRoomType) {
                System.out.println(ROOM_TYPE_MENU);
                int userInput = Integer.parseInt(scanner.nextLine());
                hasRoomType = (userInput == 1 || userInput == 2);
                try {
                    if (hasRoomType) {
                        switch (userInput) {
                            case 1 -> roomType = RoomType.SINGLE;
                            case 2 -> roomType = RoomType.DOUBLE;
                            default -> throw new IllegalArgumentException("Input not valid: " + userInput);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Select 1 or 2.");
                }
            }

            String roomNumberString = String.valueOf(roomNumber);
            room = new Room(roomNumberString, price, roomType);
            rooms.add(room);
            System.out.println("Success! Room " + roomNumberString + " was created.");
            takeABreak();
            System.out.println("Would you like to add another room? (Y(es) or N(o))");
            String answer = scanner.next().toLowerCase();
            try {
                switch (answer) {
                    case "y":
                        scanner.nextLine();
                        addRoom();
                    case "n":
                        System.out.println(OPTION_MENU);
                        try {
                            int userInput = Integer.parseInt(scanner.nextLine());
                            switch (userInput) {
                                case 1 -> MainMenu.showMainMenu();
                                case 2 -> AdminMenu.showAdminMenu();
                                case 3 -> exitApp();
                                default -> throw new IllegalArgumentException("Input not valid: " + userInput);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Select 1 or 2.");
                        } catch (Throwable e) {
                            System.out.println(e.getMessage());
                        }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Please, enter a Y(es) or N(o).");
            }
        }

        AdminResource.addRoom(rooms);
        takeABreak();
        showAdminMenu();
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
