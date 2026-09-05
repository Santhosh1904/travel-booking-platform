import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Login {

    public static int loginUser(Scanner scanner) {


        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.println();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String url = "jdbc:mysql://localhost:3306/travel_booking";
        String user = "root";
        String dbPassword = "Travel@12345";

        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try {
            Connection connection = DriverManager.getConnection(
                    url, user, dbPassword
            );

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

           if (result.next()) {

                int userId = result.getInt("user_id");

                System.out.println("Login Successful!");
                System.out.println("Welcome, " + result.getString("name") + "!");

                connection.close();

                return userId;

            } else {

                System.out.println("Invalid email or password.");

            }

            connection.close();

        } catch (Exception e) {
            System.out.println("Login Failed!");
            e.printStackTrace();
        }

        return  -1;
    }
}