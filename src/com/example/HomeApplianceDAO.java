package com.example;

import java.sql.*;
import java.util.ArrayList;

public class HomeApplianceDAO {

    /**
     *
     * @return
     */
    static Connection connectDB() {
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

    /**
     *
     * @param query
     * @return
     * @throws SQLException
     */
    static PreparedStatement preparedStatementMethod(String query) throws SQLException {
        return HomeApplianceDAO.connectDB().prepareStatement(query);
    }

    /**
     *
     * @throws SQLException
     */
    static void closeConnection() throws SQLException {
        connectDB().close();
    }

    /**
     *
     * @param id
     * @param sku
     * @param description
     * @param category
     * @param price
     * @return
     */
    private HomeAppliance homeApplianceAttrs(
            int id, String sku, String description,
            String category, int price) {
        HomeAppliance product = new HomeAppliance(sku, description, category, price);
        product.setId(id);
        return product;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    ArrayList<HomeAppliance> findAllProducts() throws SQLException {
        ArrayList<HomeAppliance> productList = new ArrayList<>();

        String query = "SELECT * FROM appliance";
        PreparedStatement preparedStatement = preparedStatementMethod(query);
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

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    HomeAppliance findProduct(int id) throws SQLException {
        String query = "SELECT * FROM appliance WHERE id = ?";
        PreparedStatement preparedStatement = preparedStatementMethod(query);

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

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    Boolean deleteProduct(int id) throws SQLException {
        String query = "DELETE FROM appliance WHERE id = ?";
        PreparedStatement preparedStatement = preparedStatementMethod(query);
        preparedStatement.setInt(1, id);
        int rs = preparedStatement.executeUpdate();
        System.out.println(rs + " rows affected");
        preparedStatement.close();
        return (rs > 0);
    }

    /**
     *
     * @param homeAppliance
     * @return
     * @throws SQLException
     */
    Boolean updateProduct(HomeAppliance homeAppliance) throws SQLException {
        //String[] productAttribute = {"sku", "description", "category", "price"};
        HomeAppliance currentProduct = findProduct(homeAppliance.getId());
        int rowsUpdated = 0;

        String query = "" +
                "UPDATE appliance " +
                "SET sku = ?, " +
                "description = ?, " +
                "category = ?, " +
                "price = ? " +
                "WHERE id = ?";

        PreparedStatement preparedStatement = preparedStatementMethod(query);

        if (homeAppliance.getSku().isEmpty() || homeAppliance.getSku() == null){
            preparedStatement.setString(1, currentProduct.getSku());
        } else {
            preparedStatement.setString(1, homeAppliance.getSku());
            rowsUpdated += 1;
        }

        if (homeAppliance.getDescription().isEmpty() || homeAppliance.getDescription() == null){
            preparedStatement.setString(2, currentProduct.getDescription());
        } else {
            preparedStatement.setString(2, homeAppliance.getDescription());
            rowsUpdated += 1;
        }

        if (homeAppliance.getCategory().isEmpty() || homeAppliance.getCategory() == null){
            preparedStatement.setString(3, currentProduct.getCategory());
        } else {
            preparedStatement.setString(3, homeAppliance.getCategory());
            rowsUpdated += 1;
        }

        if (homeAppliance.getPrice() == 0){
            preparedStatement.setInt(4, currentProduct.getPrice());
        } else {
            preparedStatement.setInt(4, homeAppliance.getPrice());
            rowsUpdated += 1;
        }


        preparedStatement.setInt(5, homeAppliance.getId());
        rowsUpdated = preparedStatement.executeUpdate();
        preparedStatement.close();

        return (rowsUpdated > 0);
    }

    /**
     *
     * @param homeAppliance
     * @return
     * @throws SQLException
     */
    Boolean addProduct(HomeAppliance homeAppliance) throws SQLException {
        String insertSql = "INSERT INTO appliance (sku, description, category, price) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = preparedStatementMethod(insertSql);

        preparedStatement.setString(1, homeAppliance.getSku().toUpperCase());
        preparedStatement.setString(2, homeAppliance.getDescription());
        preparedStatement.setString(3, homeAppliance.getCategory());
        preparedStatement.setInt(4, homeAppliance.getPrice());
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        return (rs > 0);
    }
}
