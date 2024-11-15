package com.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.HomeApplianceDAO.preparedStatementMethod;

public class AddressDAO {

    private Address addressAttrs(
            int addressID, String addressLine0, String addressLine1,
            String addressLine2, String country, String postCode) throws SQLException {
        Address address = new Address(addressLine0, addressLine1, addressLine2,
                country, postCode);
        address.setAddressID(addressID);
        return address;
    }

    Address findAddress(int id) throws SQLException {
        String query = "SELECT * FROM address WHERE addressID = ?";
        PreparedStatement preparedStatement = preparedStatementMethod(query);

        // Set the parameter for id
        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Address address = addressAttrs(
                    rs.getInt("addressID"),
                    rs.getString("addressLine0"),
                    rs.getString("addressLine1"),
                    rs.getString("addressLine2"),
                    rs.getString("country"),
                    rs.getString("postCode"));

            rs.close();
            preparedStatement.close();
            return address;
        }
        return null;

    }

    void updateAddress(Address address,
                          String addressAttribute,
                          String res
                          ) throws SQLException {
        String query = "UPDATE address SET " + addressAttribute +" = ? WHERE addressID = ?";
        PreparedStatement preparedStatement = preparedStatementMethod(query);
        preparedStatement.setString(1, res);
        preparedStatement.setInt(2, address.getAddressID());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    Boolean deleteAddress(int id) throws SQLException {
        String query = "DELETE FROM address WHERE addressID = ?";
        PreparedStatement preparedStatement = preparedStatementMethod(query);
        preparedStatement.setInt(1, id);
        int rs = preparedStatement.executeUpdate();
        System.out.println(rs + " rows affected");
        preparedStatement.close();
        return (rs > 0);
    }

    Boolean addAddress(Address address) throws SQLException {
        String insertSql = "" +
                "INSERT INTO address (addressLine0, " +
                "addressLine1, addressLine2, " +
                "country, postCode) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = preparedStatementMethod(insertSql);

        preparedStatement.setString(1, address.getAddressLine0());
        preparedStatement.setString(2, address.getAddressLine1());
        preparedStatement.setString(3, address.getAddressLine2());
        preparedStatement.setString(4, address.getCountry());
        preparedStatement.setString(5, address.getPostCode());
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();

        return (rs > 0);
    }

    Address returnLastAddedAddressObj() throws SQLException {
        String query = "SELECT * FROM address ORDER BY addressID DESC LIMIT 1";
        PreparedStatement preparedStatement = preparedStatementMethod(query);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Address address = new Address(rs.getString("addressLine0"),
                    rs.getString("addressLine0"),
                    rs.getString("addressLine0"),
                    rs.getString("country"),
                    rs.getString("postCode"));

            address.setAddressID(rs.getInt("addressID"));
            preparedStatement.close();
            return address;
        }
        return null;
    }
}
