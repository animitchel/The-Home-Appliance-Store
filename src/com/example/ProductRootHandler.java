package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;

class ProductRootHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Retrieve all products from the DAO
        HomeApplianceDAO homeApplianceDAO = new HomeApplianceDAO();
        ArrayList<HomeAppliance> appliances = null;
        try {
            appliances = homeApplianceDAO.findAllProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Use StringBuilder to build the HTML response
        StringBuilder response = new StringBuilder();
        response.append("<html>")
                .append("<head>")
                .append("<title>Product List</title>")
                .append("<link rel='stylesheet' type='text/css' href='/productstyle.css'>")
                .append("</head>")
                .append("<body>")
                .append("<div class='product-container'>")
                .append("<h1>Appliances in stock!</h1>")
                .append("<table>")
                .append("<thead><tr>" +
                        "<th>ID</th>" +
                        "<th>SKU</th>" +
                        "<th>Description</th>" +
                        "<th>Category</th>" +
                        "<th>Price</th>" +
                        "<th>Edit</th>" +
                        "<th>Delete</th>" +
                        "</tr></thead>")
                .append("<tbody>");


        for (HomeAppliance product : appliances) {
            response.append("<tr>")
                    .append("<td>").append(product.getId()).append("</td>")
                    .append("<td>").append(product.getSku()).append("</td>")
                    .append("<td>").append(product.getDescription()).append("</td>")
                    .append("<td>").append(product.getCategory()).append("</td>")
                    .append("<td>$").append(product.getPrice()).append("</td>")
                    .append("<td><a href='/edit?id=").append(product.getId()).append("'>edit</a></td>")
                    .append("<td><a href='/delete?id=").append(product.getId()).append("'>Delete</a></td>")
                    .append("</tr>");
        }

        response.append("</tbody>")
                .append("</table>")
                .append("<a href='/'>Go Back to Menu</a>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        // Send the response headers and content
        he.sendResponseHeaders(200, response.length());
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        out.write(response.toString());
        out.close();
    }
}

class CssProductHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        String css = """
                body {
                    font-family: 'Arial', sans-serif;
                    background-color: #f4f4f9;
                    margin: 0;
                    padding: 0;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    min-height: 100vh;
                }

                .product-container {
                    background-color: #fff;
                    padding: 30px;
                    border-radius: 10px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                    width: 80%;
                    max-width: 800px;
                    text-align: center;
                    margin: 20px auto;
                }

                h1 {
                    font-size: 2.5em;
                    color: #2c3e50;
                    margin-bottom: 20px;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 20px;
                }

                table, th, td {
                    border: 1px solid #ddd;
                    padding: 8px;
                    text-align: left;
                }

                th {
                    background-color: #3498db;
                    color: white;
                }

                tr:nth-child(even) {
                    background-color: #f2f2f2;
                }

                a {
                    display: inline-block;
                    margin-top: 20px;
                    text-decoration: none;
                    color: #fff;
                    background-color: #3498db;
                    padding: 10px 20px;
                    border-radius: 5px;
                    transition: background-color 0.3s;
                }

                a:hover {
                    background-color: #2980b9;
                }

                a:active {
                    background-color: #1f618d;
                }
                                
                
                """;

        // Send the response headers and CSS content
        he.sendResponseHeaders(200, css.getBytes().length);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        out.write(css);
        out.close();
    }
}
