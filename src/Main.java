import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/travel_booking";
        String user = "root";
        String password = "Travel@12345";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            System.out.println("MySQL Connected Successfully!");

            connection.close();

        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
    }
}