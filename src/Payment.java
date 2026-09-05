import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Payment {

    public static void makePayment(int bookingId, double amount) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== PAYMENT =====");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Amount: ₹" + amount);

        System.out.print("Enter payment method (UPI/Card/Cash): ");
        String method = scanner.nextLine();

        String url = "jdbc:mysql://localhost:3306/travel_booking";
        String user = "root";
        String dbPassword = "Travel@12345";

        String sql = "INSERT INTO payments " +
                     "(booking_id, payment_date, amount, payment_method, payment_status) " +
                     "VALUES (?, CURDATE(), ?, ?, 'Paid')";

        try {
            Connection connection =
                    DriverManager.getConnection(url, user, dbPassword);

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, bookingId);
            statement.setDouble(2, amount);
            statement.setString(3, method);

            statement.executeUpdate();

            System.out.println("\nPayment Successful! ✅");
            System.out.println("Payment Status: Paid");

            connection.close();

        } catch (Exception e) {
            System.out.println("Payment Failed!");
            e.printStackTrace();
        }
    }
}