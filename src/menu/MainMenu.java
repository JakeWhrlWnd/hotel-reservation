package menu;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    public static void showMainMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (flag) {
                System.out.println(mainMenuTxt);
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
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    protected static void handleUserInput(Scanner scanner, int userInput) {
        switch (userInput) {
            case 1 -> findAndReserveARoom(scanner);     // 1. Find and reserve a room
            case 2 -> seeMyReservations(scanner);       // 2. See my reservations
            case 3 -> createAnAccount(scanner);         // 3. Create an account
            case 4 -> AdminMenu.showAdminMenu(scanner); // 4. Open the Admin Menu
            case 5 -> flag = false;                     // 5. Exit application
            default -> throw new IllegalArgumentException("Input not valid: " + userInput);
        }
    }

    private static void findAndReserveARoom(Scanner scanner) {
        SimpleDateFormat bookingDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("Please enter check-in date as MM/DD/YYYY.");
        Date checkInDate = null;
        try{
            checkInDate = bookingDateFormat.parse(scanner.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date checkOutDate = null;
        try{
            checkOutDate = bookingDateFormat.parse(scanner.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println();
        Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);

        while (availableRooms.isEmpty()) {
            System.out.println("No available rooms for these dates.");
            System.out.println("Here are some recommendations.");

            Calendar calendar = new GregorianCalendar();

            assert checkInDate != null;
            calendar.setTime(checkInDate);
            calendar.add(Calendar.DATE, 7);
            checkInDate = calendar.getTime();

            assert checkOutDate != null;
            calendar.setTime(checkOutDate);
            calendar.add(Calendar.DATE, 7);
            checkOutDate = calendar.getTime();

            availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);
            System.out.println("Current vacancies from" + checkInDate + " to " + checkOutDate);
        }

        for (IRoom room : availableRooms) {
            System.out.println(room);
        }

        String email;
        System.out.println("Do you have an account with us? (y/n)");
        char userChoice = scanner.nextLine().trim().charAt(0);
        if (userChoice == 'y') {
            System.out.println("Welcome back! Enter your email: (name@domain.com)");
            String tempEmail = scanner.nextLine();
            Customer customer = AdminResource.getCustomer(tempEmail);
            if (customer == null) {
                email = createAnAccount(scanner);
            } else {
                email = tempEmail;
            }
        } else if (userChoice == 'n') {
            email = createAnAccount(scanner);
        } else {
            System.out.println("Entry not valid! Please input Y/N.");
            return;
        }

        System.out.println("What room number would you like to reserve?");
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a room number.");
            scanner.next();
        }

        while (true) {
            IRoom theRoom = HotelResource.getRoom(scanner.nextLine());
            if (availableRooms.contains(theRoom)) {
                Reservation theReservation = HotelResource.bookARoom(email, theRoom, checkInDate, checkOutDate);
                System.out.println(theReservation);
                break;
            } else {
                System.out.println("Room number is invalid. Please try again.");
            }
        }
    }

    private static void seeMyReservations(Scanner scanner) {
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        if (HotelResource.getCustomer(email) != null) {
            for (Reservation reservation : HotelResource.getCustomersReservations(email)) {
                System.out.println(reservation);
            }
        } else {
            System.out.println("No reservation found.");
        }
    }

    private static String createAnAccount(Scanner scanner) {
        System.out.println("Please enter your First name:");
        String firstName = scanner.nextLine();

        System.out.println("Please enter your Last name:");
        String lastName = scanner.nextLine();

        System.out.println("Please enter your Email: name@domain.com");
        String email = scanner.nextLine();

        try {
            HotelResource.createACustomer(email, firstName, lastName);
        } catch (IllegalArgumentException e) {
            System.out.println((e.getLocalizedMessage()));
            return null;
        }
        System.out.println();
        System.out.println("Name: " + firstName + " " + lastName + ", Email: " + email);
        System.out.println();
        return email;
    }

    protected static boolean flag = true;

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
