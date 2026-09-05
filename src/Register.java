import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Register {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("===== TRAVEL BOOKING REGISTRATION =====");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String url = "jdbc:mysql://localhost:3306/travel_booking";
        String user = "root";
        String dbPassword = "Travel@12345";

        try {

            Connection connection = DriverManager.getConnection(
                    url, user, dbPassword
            );

            // Check whether email already exists
            String checkSql = "SELECT user_id FROM users WHERE email = ?";
            PreparedStatement checkStatement =
                    connection.prepareStatement(checkSql);

            checkStatement.setString(1, email);

            ResultSet result = checkStatement.executeQuery();

            if (result.next()) {

                System.out.println();
                System.out.println("Email already registered!");
                System.out.println("Please use a different email.");

            } else {

                // Create new user
                String insertSql =
                        "INSERT INTO users (name, email, phone, password) " +
                        "VALUES (?, ?, ?, ?)";

                PreparedStatement insertStatement =
                        connection.prepareStatement(insertSql);

                insertStatement.setString(1, name);
                insertStatement.setString(2, email);
                insertStatement.setString(3, phone);
                insertStatement.setString(4, password);

                insertStatement.executeUpdate();

                System.out.println();
                System.out.println("Registration Successful!");
                System.out.println("Welcome, " + name + "!");
            }

            connection.close();

        } catch (Exception e) {

            System.out.println();
            System.out.println("Something went wrong.");
            System.out.println("Please try again.");

        }


    }
}