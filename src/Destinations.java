import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Destinations {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/travel_booking";
        String user = "root";
        String dbPassword = System.getenv("TRAVEL_DB_PASSWORD");

        String sql = "SELECT * FROM destinations";

        try {
            Connection connection = DriverManager.getConnection(
                    url, user, dbPassword
            );

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            System.out.println();
            System.out.println("===== AVAILABLE DESTINATIONS =====");

            while (result.next()) {

                System.out.println(
                        result.getInt("destination_id")
                        + ". "
                        + result.getString("name")
                        + " - "
                        + result.getString("country")
                        + " - Rs."
                        + result.getDouble("price")
                );

                System.out.println(
                        "   " + result.getString("description")
                );
            }

            connection.close();

        } catch (Exception e) {

            System.out.println("Unable to load destinations.");
            e.printStackTrace();
        }
    }
}