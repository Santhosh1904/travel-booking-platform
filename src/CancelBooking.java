import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class CancelBooking {

    public static void cancelBooking(int userId) {

        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.println("===== CANCEL BOOKING =====");

        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();

        System.out.print("Are you sure you want to cancel this booking? (Y/N): ");
        String confirmation = scanner.next();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Booking cancellation cancelled.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/travel_booking";
        String user = "root";
        String dbPassword = System.getenv("TRAVEL_DB_PASSWORD");

        try {
            Connection connection = java.sql.DriverManager.getConnection(
                    url, user, dbPassword
            );

            String checkSql =
                    "SELECT booking_id, status FROM bookings " +
                    "WHERE booking_id = ? AND user_id = ?";

            PreparedStatement checkStatement =
                    connection.prepareStatement(checkSql);

            checkStatement.setInt(1, bookingId);
            checkStatement.setInt(2, userId);

            ResultSet result = checkStatement.executeQuery();

            if (result.next()) {

                String status = result.getString("status");

                if (status.equalsIgnoreCase("Cancelled")) {
                    System.out.println("Booking is already cancelled.");
                } else {

                    String updateSql =
                            "UPDATE bookings SET status = 'Cancelled' " +
                            "WHERE booking_id = ? AND user_id = ?";

                    PreparedStatement updateStatement =
                            connection.prepareStatement(updateSql);

                    updateStatement.setInt(1, bookingId);
                    updateStatement.setInt(2, userId);

                    updateStatement.executeUpdate();

                    String refundSql =
                            "UPDATE payments SET payment_status = 'Refunded' " +
                            "WHERE booking_id = ?";

                    PreparedStatement refundStatement =
                    connection.prepareStatement(refundSql);

                    refundStatement.setInt(1, bookingId);

                    refundStatement.executeUpdate();

                    System.out.println("Payment status updated to Refunded.");

                    System.out.println("Booking cancelled successfully!");
                }

            } else {
                System.out.println("Booking not found.");
            }

            connection.close();

        } catch (Exception e) {
            System.out.println("Cancellation failed!");
            e.printStackTrace();
        }
    }
}