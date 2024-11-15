package com.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.HomeApplianceDAO.preparedStatementMethod;



public class CustomerDAO {

    private Customer customer;

    private Customer customerAttrs(
            int id, int addressId, String business,
            String telephoneNumber) throws SQLException {
        AddressDAO addressDAO = new AddressDAO();

        Address address = addressDAO.findAddress(addressId);

        Customer customer = new Customer(business, address, telephoneNumber);
        customer.setCustomerID(id);
        return customer;
    }

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

    Boolean updateCustomer(Customer customer) throws SQLException {
        String[] updateCustomerPrompt = {
                "Please enter the businessName",
                "Please enter the telephoneNumber",
                "Please enter the addressline0",
                "Please enter the addressline1",
                "Please enter the addressline2",
                "Please enter the postcode",
                "Please enter the country"
                };
        String[] customerAttribute = {
                "businessName",
                "telephoneNumber",
                "addressLine0",
                "addressLine1",
                "addressLine2",
                "postCode",
                "country"
                };
        int rowsUpdated = 0;


        Scanner scanner = new Scanner(System.in);
        System.out.println(customer);

        AddressDAO addressDAO = new AddressDAO();

        for (int i = 0; i < updateCustomerPrompt.length; i++) {

            System.out.println(updateCustomerPrompt[i]);
            String res = scanner.nextLine();

            // When there's no input, the system loops back to the next prompt
            if (res.length() == 0 && i < updateCustomerPrompt.length - 1){
                continue;
            }

            // The loop stops after the last prompt
            if (res.length() == 0 && i == updateCustomerPrompt.length - 1){
                break;
            }

            // Ensures only address attrs are used in the updateAddress method below
            if (i > 1){

                addressDAO.updateAddress(customer.getAddress(), customerAttribute[i],res);

            } else {

                String query = "UPDATE Customer SET " + customerAttribute[i] +" = ? WHERE customerID = ?";

                PreparedStatement preparedStatement = preparedStatementMethod(query);
                preparedStatement.setString(1, res);
                preparedStatement.setInt(2, customer.getCustomerID());
                rowsUpdated = preparedStatement.executeUpdate();
                preparedStatement.close();

            }



        }
        return (rowsUpdated > 0);
    }

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
}
