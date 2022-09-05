package menu;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    public static void showAdminMenu(Scanner scanner) {
        flag = true;
        while (flag) {
            System.out.println(adminMenuTxt);
            while (scanner.hasNext()) {
                while (!scanner.hasNextInt()) {
                    System.out.println("Please, pick from 1 to 5.");
                    scanner.next();
                }
                int temp = scanner.nextInt();
                if (temp >= 1 && temp <= 5) {
                    scanner.nextLine();
                    handleUserInput(scanner, temp);
                    break;
                }
                System.out.println("Please, pick from 1 to 5.");
            }
        }
    }

    protected static void handleUserInput(Scanner scanner, int userInput) {
        switch (userInput) {
            case 1 -> seeAllCustomers();                      // 1. See all Customers
            case 2 -> seeAllRooms();                          // 2. See all Rooms
            case 3 -> AdminResource.displayAllReservations(); // 3. See all Reservations
            case 4 -> addARoom(scanner);                      // 4. Add a Room
            case 5 -> flag = false;                           // 5. Exit Application
            default -> throw new IllegalArgumentException("Input not valid: " + userInput);
        }
    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = AdminResource.getAllCustomers();

        if (!customers.isEmpty()) {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
            System.out.println();
        } else {
            System.out.println("No customers added.");
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = AdminResource.getAllRooms();
        if (!rooms.isEmpty()) {
            for (IRoom room : rooms) {
                System.out.println(room);
            }
            System.out.println();
        } else {
            System.out.println("No rooms added.");
        }
    }

    private static void addARoom(Scanner scanner) {
        List<IRoom> rooms = new ArrayList<>();
        RoomType roomType = RoomType.SINGLE;

        String userChoice = "y";
        while (userChoice.equals("y")) {
            System.out.println("Enter room number:");
            String roomNumber = scanner.next().trim();

            System.out.println("Enter price per night:");
            while (!scanner.hasNextDouble()) {
                System.out.println("Please input double...");
                scanner.next();
            }
            double price = scanner.nextDouble();

            System.out.println("Enter room type: 1 for Single bed, 2 for Double bed");
            while (scanner.hasNext()) {
                while (!scanner.hasNextInt()) {
                    System.out.println("Input a 1 or 2");
                    scanner.next();
                }
                int temp = scanner.nextInt();
                if (temp == 1 || temp == 2) {
                    roomType = temp == 1 ? RoomType.SINGLE : RoomType.DOUBLE;
                    break;
                }
                System.out.println("Input a 1 or 2\n");
            }

            Room room = new Room(roomNumber, price, roomType);
            System.out.println("Room created: " + "\nRoom number: " + roomNumber + "\nPrice: $" + price + "\nRoom tye: " + roomType);
            rooms.add(room);

            System.out.println("Would you like to add more rooms? (y/n)");
            userChoice = scanner.next();
        }
        AdminResource.addRoom(rooms);
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
