package com.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.HomeApplianceDAO.preparedStatementMethod;


public class CustomerDAO {

    /**
     *
     * @param id
     * @param addressId
     * @param business
     * @param telephoneNumber
     * @return
     * @throws SQLException
     */
    private Customer customerAttrs(
            int id, int addressId, String business,
            String telephoneNumber) throws SQLException {
        AddressDAO addressDAO = new AddressDAO();

        Address address = addressDAO.findAddress(addressId);

        Customer customer = new Customer(business, address, telephoneNumber);
        customer.setCustomerID(id);
        return customer;
    }

    /**
     *
     * @return
     * @throws SQLException
     * @throws NoSuchFieldException
     */
    ArrayList findAllCustomer() throws SQLException, NoSuchFieldException {
        ArrayList<Customer> customerList = new ArrayList<>();

        String query = "SELECT * FROM Customer";
        PreparedStatement preparedStatement = preparedStatementMethod(query);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Customer customer = customerAttrs(
                    rs.getInt("customerID"),
                    rs.getInt("addressID"),
                    rs.getString("businessName"),
                    rs.getString("telephoneNumber"));
            customerList.add(customer);

        }
        // Close resources
        rs.close();
        preparedStatement.close();
        return customerList;

    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    Customer findCustomer(int id) throws SQLException {
        String query = "SELECT * FROM Customer WHERE customerID = ?";
        PreparedStatement preparedStatement = preparedStatementMethod(query);

        // Set the parameter for id
        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            Customer customer = customerAttrs(
                    rs.getInt("customerID"),
                    rs.getInt("addressID"),
                    rs.getString("businessName"),
                    rs.getString("telephoneNumber"));

            rs.close();
            preparedStatement.close();
            return customer;
        }
        return null;

    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    Boolean deleteCustomer(int id) throws SQLException {
        String query = "DELETE FROM Customer WHERE customerID = ?";

        AddressDAO addressDAO = new AddressDAO();

        Customer customer = findCustomer(id);
        if (customer == null) {
            System.out.println("*Customer not found*");
            return false;
        }
        int addressToBeDeletedId = customer.getAddress().getAddressID();

        if (addressDAO.deleteAddress(addressToBeDeletedId)){
            PreparedStatement preparedStatement = preparedStatementMethod(query);
            preparedStatement.setInt(1, id);
            int rs = preparedStatement.executeUpdate();
            System.out.println(rs + " rows affected");
            preparedStatement.close();
            return (rs > 0);
        }

        return false;
    }

    /**
     *
     * @param customer
     * @return
     * @throws SQLException
     */
    Boolean updateCustomer(Customer customer) throws SQLException {
        int rowsUpdated;
        AddressDAO addressDAO = new AddressDAO();
        // This updates the address table in the database and returns the numbers of rows updated
        rowsUpdated = addressDAO.updateAddress(customer.getAddress());

        Customer currentCustomer = findCustomer(customer.getCustomerID());
        System.out.println(currentCustomer);


        String query = "" +
            "UPDATE Customer " +
            "SET businessName = ?, " +
            "telephoneNumber = ? " +
            "WHERE customerID = ?";

        PreparedStatement preparedStatement = preparedStatementMethod(query);
        if (customer.getBusinessName().isEmpty() || customer.getBusinessName() == null) {
            preparedStatement.setString(1, currentCustomer.getBusinessName());
        } else {
            preparedStatement.setString(1, customer.getBusinessName());
        }

        if (customer.getTelephoneNumber().isEmpty() || customer.getTelephoneNumber() == null) {
            preparedStatement.setString(2, currentCustomer.getTelephoneNumber());
        } else {
            preparedStatement.setString(2, customer.getTelephoneNumber());
        }

        preparedStatement.setInt(3, customer.getCustomerID());
        rowsUpdated += preparedStatement.executeUpdate();
        preparedStatement.close();

        return (rowsUpdated > 0);

    }

    /**
     *
     * @param customer
     * @return
     * @throws SQLException
     */
    Boolean addCustomer(Customer customer) throws SQLException {
        String insertSql = "INSERT INTO Customer (addressID, businessName, telephoneNumber) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = preparedStatementMethod(insertSql);

        preparedStatement.setInt(1, customer.getAddress().getAddressID());
        preparedStatement.setString(2, customer.getBusinessName());
        preparedStatement.setString(3, customer.getTelephoneNumber());
        int rs = preparedStatement.executeUpdate();
        preparedStatement.close();
        return (rs > 0);
    }

    /**
     *
     * @param customerAddresssline0
     * @param customerAddresssline1
     * @param customerAddresssline2
     * @param customerCountry
     * @param customerPostcode
     * @param customerBusinessName
     * @param customerTelephoneNumber
     * @return
     * @throws SQLException
     */
    Customer returnCustomerWithAddress(
            String customerAddresssline0,
            String customerAddresssline1,
            String customerAddresssline2,
            String customerCountry,
            String customerPostcode,
            String customerBusinessName,
            String customerTelephoneNumber) throws SQLException {
        Address address = new Address(
                customerAddresssline0,
                customerAddresssline1,
                customerAddresssline2,
                customerCountry,
                customerPostcode);
        AddressDAO addressDAO = new AddressDAO();

        addressDAO.addAddress(address);
        Address lastInsertedAddressRecordObj = addressDAO.returnLastAddedAddressObj();
        return new Customer(customerBusinessName,
                lastInsertedAddressRecordObj,
                customerTelephoneNumber);
    }

    /**
     *
     * @param customerAddressId
     * @param customerId
     * @param customerAddresssline0
     * @param customerAddresssline1
     * @param customerAddresssline2
     * @param customerCountry
     * @param customerPostcode
     * @param customerBusinessName
     * @param customerTelephoneNumber
     * @return
     * @throws SQLException
     */
    Customer returnUpdatedCustomerWithAddress(
            int customerAddressId,
            int customerId,
            String customerAddresssline0,
            String customerAddresssline1,
            String customerAddresssline2,
            String customerCountry,
            String customerPostcode,
            String customerBusinessName,
            String customerTelephoneNumber) throws SQLException {

        Address address = new Address(
                customerAddresssline0,
                customerAddresssline1,
                customerAddresssline2,
                customerCountry,
                customerPostcode);
        address.setAddressID(customerAddressId);

        Customer customer = new Customer(
                customerBusinessName,
                address,
                customerTelephoneNumber);
        customer.setCustomerID(customerId);

        return customer;
    }


}






















