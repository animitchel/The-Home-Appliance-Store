import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ControllerHomeAppliance {
    public static void main(String[] args) throws SQLException {
        HomeApplianceDAO homeApplianceDAO = new HomeApplianceDAO();
        String selection;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("--------------------");
            System.out.println("The Home Appliance Store");
            System.out.println("Choose from these options");
            System.out.println("--------------------");
            System.out.println("[1] List all products");
            System.out.println("[2] Search for product by ID");
            System.out.println("[3] Add a new product");
            System.out.println("[4] Update a product by ID");
            System.out.println("[5] Delete a product by ID");
            System.out.println("[6] Exit");
            System.out.println();
            selection = in.nextLine();
            switch (selection) {
                case "1":
                    // insert code to find all appliances
                    System.out.println("Retrieving all products ...");

                    ArrayList appliances = homeApplianceDAO.findAllProducts();
                    // DynamicHTMLTable dynamicHTMLTable = new DynamicHTMLTable();
                    for (int i = 0; i < appliances.size(); i++) {
                        System.out.println(appliances.get(i));
                    }

                    // dynamicHTMLTable.webView(appliances);
                    break;
                case "2":
                    System.out.println("\nSearch for a product by ID");
                    String id = in.nextLine();
                    // code to find and display appliances based on ID
                    System.out.println(homeApplianceDAO.findProduct(Integer.valueOf(id)));
                    break;
                case "3":
                    System.out.println("\nAdd a new product");

                    System.out.println("Enter product sku: ");
                    String productSku = in.nextLine();
                    System.out.println("Enter product description: ");
                    String productDescription = in.nextLine();
                    System.out.println("Enter product category: ");
                    String productCategory = in.nextLine();
                    System.out.println("Enter product price: ");
                    int productPrice = Integer.valueOf(in.nextLine());


                    HomeAppliance homeAppliance = new HomeAppliance(
                            productSku.toUpperCase(), productDescription,
                            productCategory, productPrice);
                    homeApplianceDAO.addProduct(homeAppliance);
                    break;
                case "4":
                    System.out.println("\nUpdate a product by ID");
                    String pk = in.nextLine();
                    homeApplianceDAO.updateProduct(homeApplianceDAO.findProduct(Integer.valueOf(pk)));
                    break;
                case "5":
                    System.out.println("\nDelete a product by ID");
                    String pk2 = in.nextLine();
                    homeApplianceDAO.deleteProduct(Integer.valueOf(pk2));
                    break;
            }
            System.out.println();
        }while (!selection.equals("6"));
        HomeApplianceDAO.closeConnection();
    }
}
