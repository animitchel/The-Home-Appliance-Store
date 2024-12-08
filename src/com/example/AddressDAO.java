package com.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.HomeApplianceDAO.preparedStatementMethod;

public class AddressDAO {
    // javadoc -d doc AddressDAO.java
    /**
     * This a helper method that creates an address object from query result
     *
     * @param addressID the address id
     * @param addressLine0 address line 0
     * @param addressLine1 address line 0
     * @param addressLine2 address line 0
     * @param country address country
     * @param postCode address line 0
     * @return the instance of the address class
     */
    private Address addressAttrs(
            int addressID, String addressLine0, String addressLine1,
            String addressLine2, String country, String postCode) throws SQLException {
        Address address = new Address(addressLine0, addressLine1, addressLine2,
                country, postCode);
        address.setAddressID(addressID);
        return address;
    }

    /**
     * Finds the address record with sql code from the addressID
     *
     * @param id the id of the address record
     * @return an address object or null if not found
     * @throws SQLException
     */
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


    /**
     *
     * @param address
     * @throws SQLException
     */
    int updateAddress(Address address) throws SQLException {
        Address currentAddress = findAddress(address.getAddressID());
        int rowsUpdated = 0;

        String query = "" +
            "UPDATE address " +
            "SET addressLine0 = ?, " +
            "addressLine1 = ?, " +
            "addressLine2 = ?, " +
            "postCode = ?, " +
            "country = ? " +
            "WHERE addressID = ?";

        PreparedStatement preparedStatement = preparedStatementMethod(query);
        if (address.getAddressLine0().isEmpty() || address.getAddressLine0() == null) {
            preparedStatement.setString(1, currentAddress.getAddressLine0());
        } else {
            preparedStatement.setString(1, address.getAddressLine0());
            rowsUpdated += 1;
        }

        if (address.getAddressLine1().isEmpty() || address.getAddressLine1() == null) {
            preparedStatement.setString(2, currentAddress.getAddressLine1());
        } else {
            preparedStatement.setString(2, address.getAddressLine1());
            rowsUpdated += 1;
        }

        if (address.getAddressLine2().isEmpty() || address.getAddressLine2() == null) {
            preparedStatement.setString(3, currentAddress.getAddressLine2());
        } else {
            preparedStatement.setString(3, address.getAddressLine2());
            rowsUpdated += 1;
        }

        if (address.getPostCode().isEmpty() || address.getPostCode() == null) {
            preparedStatement.setString(4, currentAddress.getPostCode());
        } else {
            preparedStatement.setString(4, address.getPostCode());
            rowsUpdated += 1;
        }

        if (address.getCountry().isEmpty() || address.getCountry() == null) {
            preparedStatement.setString(5, currentAddress.getCountry());
        } else {
            preparedStatement.setString(5, address.getCountry());
            rowsUpdated += 1;
        }

        preparedStatement.setInt(6, address.getAddressID());
        rowsUpdated = preparedStatement.executeUpdate();
        preparedStatement.close();

        return rowsUpdated;
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    Boolean deleteAddress(int id) throws SQLException {
        String query = "DELETE FROM address WHERE addressID = ?";
        PreparedStatement preparedStatement = preparedStatementMethod(query);
        preparedStatement.setInt(1, id);
        int rs = preparedStatement.executeUpdate();
        System.out.println(rs + " rows affected");
        preparedStatement.close();
        return (rs > 0);
    }

    /**
     *
     * @param address
     * @return
     * @throws SQLException
     */
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

    /**
     *
     * @return
     * @throws SQLException
     */
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
