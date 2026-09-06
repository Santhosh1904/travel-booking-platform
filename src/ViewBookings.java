import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ViewBookings {

    public static void viewBookings(int userId) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("===== MY BOOKINGS =====");

        String url = "jdbc:mysql://localhost:3306/travel_booking";
        String user = "root";
        String dbPassword = System.getenv("TRAVEL_DB_PASSWORD");

        String sql =
                "SELECT b.booking_id, d.name, d.country, b.booking_date, " +
                "b.number_of_people, b.total_price, b.status, " +
                "COALESCE(p.payment_status, 'Not Paid') AS payment_status " +
                "FROM bookings b " +
                "JOIN destinations d ON b.destination_id = d.destination_id " +
                "LEFT JOIN payments p ON b.booking_id = p.booking_id " +
                "WHERE b.user_id = ?";

        try {

            Connection connection = DriverManager.getConnection(
                    url, user, dbPassword
            );

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();

            boolean found = false;

            while (result.next()) {

                found = true;

                System.out.println();
                System.out.println("Booking ID: "
                        + result.getInt("booking_id"));

                System.out.println("Destination: "
                        + result.getString("name"));

                System.out.println("Country: "
                        + result.getString("country"));

                System.out.println("Booking Date: "
                        + result.getDate("booking_date"));

                System.out.println("People: "
                        + result.getInt("number_of_people"));

                System.out.println("Total Price: "
                        + result.getDouble("total_price"));

                System.out.println("Status: "
                        + result.getString("status"));

                System.out.println("Payment Status: "
                        + result.getString("payment_status"));

                System.out.println("----------------------------");
            }

            if (!found) {
                System.out.println();
                System.out.println("No bookings found.");
            }

            connection.close();

        } catch (Exception e) {

            System.out.println("Unable to load bookings.");
            e.printStackTrace();
        }

      
    }
}