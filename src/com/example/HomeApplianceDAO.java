import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HomeApplianceDAO {

    public static Connection connectDB() {
        String url = "jdbc:sqlite:homeApplianceDB.sqlite";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.getMessage();

        }
        try {Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                return conn;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static PreparedStatement preparedStatementMethod(String query) throws SQLException {
        return HomeApplianceDAO.connectDB().prepareStatement(query);
    }

    public static void closeConnection() throws SQLException {
        connectDB().close();
    }

    private HomeAppliance homeApplianceAttrs(
            int id, String sku, String description,
            String category, int price) throws SQLException {
        HomeAppliance product = new HomeAppliance(sku, description, category, price);
        product.setId(id);
        return product;
    }

    public ArrayList findAllProducts() throws SQLException {
        ArrayList<HomeAppliance> productList = new ArrayList<>();

        String query = "SELECT * FROM appliance";
        PreparedStatement preparedStatement = connectDB().prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            HomeAppliance product = homeApplianceAttrs(
                    rs.getInt("id"),
                    rs.getString("sku"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getInt("price"));
            productList.add(product);

        }
        // Close resources
        rs.close();
        preparedStatement.close();
        return productList;

    }

    public HomeAppliance findProduct(int id) throws SQLException {
        String query = "SELECT * FROM appliance WHERE id = ?";
        PreparedStatement preparedStatement = connectDB().prepareStatement(query);

        // Set the parameter for id
        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            HomeAppliance product = homeApplianceAttrs(
                    rs.getInt("id"),
                    rs.getString("sku"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getInt("price"));

            rs.close();
            preparedStatement.close();
            return product;
        }
        return null;

    }

    public Boolean deleteProduct(int id) throws SQLException {
        String query = "DELETE FROM appliance WHERE id = ?";
        PreparedStatement preparedStatement = connectDB().prepareStatement(query);
        preparedStatement.setInt(1, id);
        int rs = preparedStatement.executeUpdate();
        System.out.println(rs + " rows affected");
        preparedStatement.close();
        return (rs > 0);
    }

    public Boolean updateProduct(HomeAppliance homeAppliance) throws SQLException {
        String[] updateProduct = {
                "Please enter the sku",
                "Please enter the description",
                "Please enter the category",
                "Please enter the price"};
        String[] productAttribute = {"sku", "description", "category", "price"};
        int rowsUpdated = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println(homeAppliance);
        for (int i = 0; i < updateProduct.length; i++) {

            System.out.println(updateProduct[i]);
            String res = scanner.nextLine();

            if (res.length() == 0 && i < updateProduct.length - 1){
                continue;
            }
            if (res.length() == 0 && i == updateProduct.length - 1){
                break;
            }
            String query = "UPDATE appliance SET " + productAttribute[i] +" = ? WHERE id = ?";
            PreparedStatement preparedStatement = connectDB().prepareStatement(query);
            preparedStatement.setString(1, (i == 0 ? res.toUpperCase() : res));
            preparedStatement.setInt(2, homeAppliance.getId());
            rowsUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();

        }
        return (rowsUpdated > 0);
    }

    public Boolean addProduct(HomeAppliance homeAppliance) throws SQLException {
        String insertSql = "INSERT INTO appliance (sku, description, category, price) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connectDB().prepareStatement(insertSql);

        preparedStatement.setString(1, homeAppliance.getSku());
        preparedStatement.setString(2, homeAppliance.getDescription());
        preparedStatement.setString(3, homeAppliance.getCategory());
        preparedStatement.setInt(4, homeAppliance.getPrice());
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        return (rs > 0);
    }
}
