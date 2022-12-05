package menu;

import api.AdminResource;
import model.*;

import java.util.*;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getInstance();

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

    private static void seeAllReservations() {
        adminResource.getAllReservations();
    }

    private static void addRoom() {
        final String roomNumber = getRoomNumber();
        final double price = getRoomPrice();
        int roomType = getRoomType();

        adminResource.addRoom(roomNumber, price, roomType);
        System.out.println("Room created: " + "\nRoom number: " + roomNumber + "\nPrice: $" + price + "\nRoom type: " + roomType);

        System.out.println("Would you like to add more rooms? (y/n)");
        String moreRooms = scanner.nextLine();
        if ("y".equals(moreRooms)) {
            addRoom();
        } else {
            showAdminMenu();
        }
    }

    private static String getRoomNumber() {
        String roomNumber;

        do {
            System.out.println("Enter the room number:");
            roomNumber = scanner.nextLine();
        } while (roomNumber.isBlank());

        return roomNumber;
    }

    private static Double getRoomPrice() {
        double price;
        boolean isError ;

        do {
            isError = false;
            System.out.println("Enter the price per night: ");
            while(!scanner.hasNextDouble()) {
                System.out.println("Enter");
                scanner.next();
            }
            price = scanner.nextDouble();
            if (price < 0) {
                isError = true;
                System.out.println("Price can't be less than 0");
            }
        } while (isError);

        return price;
    }

    private static int getRoomType() {
        System.out.println("Enter the room type: (1 for Single, 2 for Double)");
        while (!scanner.hasNext("[12]")) {
            System.out.println("Please enter a 1 (Single) or a 2 (Double)");
            scanner.next();
        }
        return Integer.parseInt(scanner.next());
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
