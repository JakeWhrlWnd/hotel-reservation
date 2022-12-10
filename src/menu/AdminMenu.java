package menu;

import api.AdminResource;
import model.*;

import java.util.*;

public class AdminMenu {

    private static Collection<Customer> allCustomers = AdminResource.getAllCustomers();
    private static final Scanner scanner = new Scanner(System.in);

    public static void showAdminMenu() {
        System.out.println(adminMenuTxt);
        while (flag) {
            try {
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1 -> seeAllCustomers();        // 1. See all Customers
                    case 2 -> seeAllRooms();            // 2. See all Rooms
                    case 3 -> seeAllReservations();     // 3. See all Reservations
                    case 4 -> addRoom();                // 4. Add a Room
                    case 5 -> MainMenu.showMainMenu();  // 5. Exit Application
                    default -> throw new IllegalArgumentException("Input not valid: " + userInput);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Only numbers accepted.");
            } catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();

        if (!customers.isEmpty()) {
            adminResource.getAllCustomers().forEach(System.out::println);
        } else {
            System.out.println("No customers were found.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            showAdminMenu();
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (!rooms.isEmpty()) {
            adminResource.getAllRooms().forEach(System.out::println);
        } else {
            System.out.println("No rooms were found.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            showAdminMenu();
        }
    }

    //Option #3
    private static void seeAllReservations() {
        AdminResource.displayAllReservations();
    }

    private static void addRoom() {
        List<IRoom> rooms = new LinkedList<>();

        while (!flag) {
            IRoom room;

            //Room Number
            String roomNumber = "";
            boolean hasRoomNumber = false;
            while (!hasRoomNumber) {
                System.out.println("Enter the Room number: (Must be greater than 0)");
                try {
                    roomNumber = scanner.next();
                    hasRoomNumber = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Negative numbers and 0 not acceptable Room numbers");
                }
            }
            //Room Price
            Double price = "0.0";
            boolean hasPrice = false;
            while (!hasPrice) {
                System.out.println("Enter the Room price: ");
                try {
                    price = scanner.nextDouble();
                    hasPrice = price > 0;
                } catch (InputMismatchException e) {

                }
            }
            //Room Type
            RoomType roomType = null;
            boolean hasRoomType = false;
            while (!hasRoomType) {

            }
            room = new Room(roomNumber, price, roomType);
            rooms.add(room);
            System.out.println("Success! Room " + roomNumber + "was created.");
            takeABreak();
            System.out.println("Would you like to add another room? (Y(es)/N(o))");
            String answer = scanner.next().substring(0,1).toLowerCase();
            try {
                switch (answer) {
                    case "y":
                        scanner.nextLine();
                        addRoom();
                    case "n":
                        flag = true;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Please, enter a Y(es) or N(o).");
            }
        }

        AdminResource.addRoom(rooms);
    }

    public static void takeABreak() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected static boolean flag = true;

    protected static final String adminMenuTxt = """
            Admin Menu
            -----------------------------------------------
            1. See all Customers
            2. See all Rooms
            3. See all Reservations
            4. Add a Room
            5. Back to Main Menu
            -----------------------------------------------
            Please choose an option from the menu""";
}
