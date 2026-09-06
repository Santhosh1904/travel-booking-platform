import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class BookTrip {

    public static void bookTrip(int userId) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("===== BOOK A TRIP =====");

        System.out.print("Enter Destination ID: ");
        int destinationId = scanner.nextInt();

        System.out.print("Enter number of people: ");
        int numberOfPeople = scanner.nextInt();

        String url = "jdbc:mysql://localhost:3306/travel_booking";
        String user = "root";
        String dbPassword = System.getenv("TRAVEL_DB_PASSWORD");

        try {

            Connection connection = DriverManager.getConnection(
                    url, user, dbPassword
            );

            // Get destination price
            String priceSql =
                    "SELECT name, price FROM destinations WHERE destination_id = ?";

            PreparedStatement priceStatement =
                    connection.prepareStatement(priceSql);

            priceStatement.setInt(1, destinationId);

            ResultSet result = priceStatement.executeQuery();

            if (result.next()) {

                String destinationName = result.getString("name");
                double price = result.getDouble("price");

                double totalPrice = price * numberOfPeople;

                // Save booking
                String bookingSql =
                        "INSERT INTO bookings " +
                        "(user_id, destination_id, booking_date, " +
                        "number_of_people, total_price, status) " +
                        "VALUES (?, ?, CURDATE(), ?, ?, 'Confirmed')";

                PreparedStatement bookingStatement =
                        connection.prepareStatement(bookingSql);

                bookingStatement.setInt(1, userId);
                bookingStatement.setInt(2, destinationId);
                bookingStatement.setInt(3, numberOfPeople);
                bookingStatement.setDouble(4, totalPrice);

                bookingStatement.executeUpdate();

                int bookingId = 0;

                String idSql = "SELECT LAST_INSERT_ID()";
                PreparedStatement idStatement = connection.prepareStatement(idSql);
                ResultSet idResult = idStatement.executeQuery();

                if (idResult.next()) {
                    bookingId = idResult.getInt(1);
                }

                System.out.println();
                System.out.println("Proceeding to payment...");

                Payment.makePayment(bookingId, totalPrice);

                System.out.println();
                System.out.println("Booking Successful!");
                System.out.println("Destination: " + destinationName);
                System.out.println("People: " + numberOfPeople);
                System.out.println("Total Price: Rs." + totalPrice);
                System.out.println("Status: Confirmed");

            } else {

                System.out.println("Destination not found.");

            }

            connection.close();

        } catch (Exception e) {

            System.out.println("Booking Failed!");
            e.printStackTrace();
        }


    }
}