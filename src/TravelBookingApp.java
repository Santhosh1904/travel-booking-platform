import java.util.Scanner;

public class TravelBookingApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int loggedInUserId = -1;

        while (true) {

            System.out.println();
            System.out.println("================================");
            System.out.println("     TRAVEL BOOKING PLATFORM");
            System.out.println("================================");
            if (loggedInUserId == -1) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. View Destinations");
                System.out.println("7. Exit");
            } else {
                System.out.println("3. View Destinations");
                System.out.println("4. Book a Trip");
                System.out.println("5. View My Bookings");
                System.out.println("6. Cancel Booking");
                System.out.println("7. Logout");
                System.out.println("8. Exit");
            }
            System.out.println("================================");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    Register.main(new String[]{});
                    break;

                case 2:
                    loggedInUserId = Login.loginUser(scanner);
                    break;

                case 3:
                    Destinations.main(new String[]{});
                    break;

                case 4:
                    if (loggedInUserId == -1) {
                        System.out.println("Please login first.");
                    } else {
                        BookTrip.bookTrip(loggedInUserId);
                    }
                    break;

                case 5:
                    if (loggedInUserId == -1) {
                        System.out.println("Please login first.");
                    } else {
                        ViewBookings.viewBookings(loggedInUserId);
                    }
                    break;

                case 6:
                    CancelBooking.cancelBooking(loggedInUserId);
                    break;

                case 7:
                    loggedInUserId = -1;
                    System.out.println("Logout successful!");
                    break;

                case 8:
                    System.out.println("Thank you for using Travel Booking Platform!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}