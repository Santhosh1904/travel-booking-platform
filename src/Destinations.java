import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Destinations {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/travel_booking";
        String user = "root";
        String dbPassword = System.getenv("TRAVEL_DB_PASSWORD");

        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.println("===== DESTINATION SEARCH =====");

        System.out.print(
                "Enter destination name (or press Enter to view all): "
        );

        String searchTerm = scanner.nextLine().trim();

        System.out.print(
                "Enter maximum price (or press Enter for no limit): "
        );

        String priceInput = scanner.nextLine().trim();

        Double maxPrice = null;

        if (!priceInput.isEmpty()) {
            try {
                maxPrice = Double.parseDouble(priceInput);

                if (maxPrice < 0) {
                    System.out.println("Price cannot be negative.");
                    scanner.close();
                    return;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a number.");
                scanner.close();
                return;
            }
        }

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM destinations WHERE 1=1"
        );

        if (!searchTerm.isEmpty()) {
            sql.append(" AND (name LIKE ? OR country LIKE ?)");
        }

        if (maxPrice != null) {
            sql.append(" AND price <= ?");
        }

        try {
            Connection connection = DriverManager.getConnection(
                    url, user, dbPassword
            );

            PreparedStatement statement =
                    connection.prepareStatement(sql.toString());

            int parameterIndex = 1;

            if (!searchTerm.isEmpty()) {

                String searchPattern = "%" + searchTerm + "%";

                statement.setString(
                        parameterIndex++,
                        searchPattern
                );

                statement.setString(
                        parameterIndex++,
                        searchPattern
                );
            }

            if (maxPrice != null) {
                statement.setDouble(
                        parameterIndex,
                        maxPrice
                );
            }

            ResultSet result = statement.executeQuery();

            boolean found = false;

            System.out.println();
            System.out.println("===== AVAILABLE DESTINATIONS =====");

            while (result.next()) {

                found = true;

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

                System.out.println();
            }

            if (!found) {

                System.out.println(
                        "No destinations found matching your criteria."
                );
            }

            result.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println("Unable to load destinations.");
            e.printStackTrace();

        } finally {

            scanner.close();
        }
    }
}