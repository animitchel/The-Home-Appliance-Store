package com.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.HomeApplianceDAO.closeConnection;

public class ControllerCustomer {
    public static void main(String[] args) throws SQLException, NoSuchFieldException {
        CustomerDAO customerDAO = new CustomerDAO();
        String selection1;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("--------------------");
            System.out.println("The Home Appliance Store");
            System.out.println("Choose from these options");
            System.out.println("--------------------");
            System.out.println("[1] List all customers");
            System.out.println("[2] Search for customer by ID");
            System.out.println("[3] Add a new customer");
            System.out.println("[4] Update a customer by ID");
            System.out.println("[5] Delete a customer by ID");
            System.out.println("[6] Exit");
            System.out.println();
            selection1 = in.nextLine();
            switch (selection1) {
                case "1":
                    // insert code to find all appliances
                    System.out.println("Retrieving all Customers ...");

                    ArrayList customers = customerDAO.findAllCustomer();
                    //com.example.DynamicHTMLTable dynamicHTMLTable = new com.example.DynamicHTMLTable();
                    for (int i = 0; i < customers.size(); i++) {
                        System.out.println(customers.get(i));
                    }

                    //dynamicHTMLTable.webView(appliances);
                    break;
                case "2":
                    System.out.println("\nSearch for a customer by ID");
                    String id = in.nextLine();
                    // code to find and display appliances based on ID
                    System.out.println(customerDAO.findCustomer(Integer.valueOf(id)));
                    break;
                case "3":
                    System.out.println("\nAdd a new customer");

                    System.out.println("Enter Customer business Name: ");
                    String customerBusinessName = in.nextLine();

                    System.out.println("Enter Customer Addresssline0: ");
                    String customerAddresssline0 = in.nextLine();

                    System.out.println("Enter Customer Addresssline1: ");
                    String customerAddresssline1 = in.nextLine();

                    System.out.println("Enter Customer Addresssline2: ");
                    String customerAddresssline2 = in.nextLine();

                    System.out.println("Enter Customer country: ");
                    String customerCountry = in.nextLine();

                    System.out.println("Enter Customer postcode: ");
                    String customerPostcode = in.nextLine();

                    System.out.println("Enter Customer telephone number: ");
                    String customerTelephoneNumber = in.nextLine();

                    Customer customer = customerDAO.returnCustomerWithAddress(
                            customerAddresssline0, customerAddresssline1,
                            customerAddresssline2, customerCountry,
                            customerPostcode, customerBusinessName,
                            customerTelephoneNumber);

                    customerDAO.addCustomer(customer);
                    break;
                case "4":
                    System.out.println("\nUpdate a customer by ID");
                    String pk = in.nextLine();
                    customerDAO.updateCustomer(customerDAO.findCustomer(Integer.valueOf(pk)));
                    break;
                case "5":
                    System.out.println("\nDelete a customer by ID");
                    String pk2 = in.nextLine();
                    customerDAO.deleteCustomer(Integer.valueOf(pk2));
                    break;
            }
            System.out.println();
        }while (!selection1.equals("6"));
        closeConnection();
    }
}
