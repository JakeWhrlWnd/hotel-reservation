package menu;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.util.*;

public class AdminMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showAdminMenu() {
        System.out.println(adminMenuTxt);
        while (flag) {
            try {
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1 -> seeAllCustomers();                      // 1. See all Customers
                    case 2 -> seeAllRooms();                          // 2. See all Rooms
                    case 3 -> seeAllReservations(); // 3. See all Reservations
                    case 4 -> addRoom();                      // 4. Add a Room
                    case 5 -> MainMenu.showMainMenu();                           // 5. Exit Application
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
        Collection<Customer> customers = AdminResource.getAllCustomers();

        if (!customers.isEmpty()) {
            AdminResource.getAllCustomers().forEach(System.out::println);
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
        Collection<IRoom> rooms = AdminResource.getAllRooms();

        if (!rooms.isEmpty()) {
            AdminResource.getAllRooms().forEach(System.out::println);
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
        Collection<Reservation> allReservations = AdminResource.getAllReservations();

        if (!allReservations.isEmpty()) {
            AdminResource.getAllReservations().forEach(System.out::println);
        }
        else {
            System.out.println("No reservations were found.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            showAdminMenu();
        }
    }

    private static void addRoom() {
        String roomNumber = null;
        boolean validRoomNumber = false;
        while (!validRoomNumber) {
            System.out.println("Enter room number:");
            roomNumber = scanner.nextLine();
            IRoom roomExists = HotelResource.getRoom(roomNumber);
            if (roomExists == null) {
                validRoomNumber = true;
            } else {
                System.out.println("That room already exists. Enter y or yes to update.");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                    validRoomNumber = true;
                }
            }
        }

        double price = 0.00;
        boolean validPrice = false;
        while (!validPrice) {
            try {
                System.out.println("Enter price per night:");
                price = Double.parseDouble(scanner.nextLine());
                if (price < 0) {
                    System.out.println("Price must be greater or equal to 0.00");
                } else {
                    validPrice = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid price.");
            }
        }

        RoomType roomType = null;
        boolean validRoomType = false;
        while (!validRoomType) {
            try {
                System.out.println("Enter room type: 1 for Single bed, 2 for Double bed");
                roomType = RoomType.convertIntToRoomType(scanner.nextInt());
                if (roomType == null) {
                    System.out.println("Please enter a valid room type.");
                } else {
                    validRoomType = true;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid room type.");
            }
        }

        Room newRoom = new Room(roomNumber, price, roomType);
        AdminResource.addRoom(newRoom);
        System.out.println("Room created: " + "\nRoom number: " + roomNumber + "\nPrice: $" + price + "\nRoom type: " + roomType);
        System.out.println("Would you like to add more rooms? (y/n)");
        addAnotherRoom();
    }

    private static void addAnotherRoom() {
        try {
            String anotherRoom = scanner.nextLine();

            while ((anotherRoom.charAt(0) != 'y' && anotherRoom.charAt(0) != 'n') || anotherRoom.length() != 1) {
                System.out.println("Please enter y (yes) / n (no).");
                anotherRoom = scanner.nextLine();
            }

            if (anotherRoom.charAt(0) == 'y') {
                addRoom();
            } else if (anotherRoom.charAt(0) == 'n') {
                showAdminMenu();
            } else {
                addAnotherRoom();
            }
        } catch (StringIndexOutOfBoundsException e) {
            addAnotherRoom();
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
