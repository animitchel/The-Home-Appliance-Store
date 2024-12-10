package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

public class DeleteHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Extract the query string from the URI
        URI uri = he.getRequestURI();
        String productId = getProductId(uri);

        HomeApplianceDAO homeApplianceDAO = new HomeApplianceDAO();
        try {
            homeApplianceDAO.deleteProduct(Integer.parseInt(productId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Send a redirect response to the product page ("/products")
        String redirectUrl = "/products";
        he.getResponseHeaders().set("Location", redirectUrl);
        he.sendResponseHeaders(302, -1); // 302: Found (temporary redirect)

        // Close the response body
        he.getResponseBody().close();
    }

    private static String getProductId(URI uri) {
        String query = uri.getQuery();

        // Extract the "id" parameter from the query string (e.g., id=12)
        String productId = null;
        if (query != null && query.contains("id=")) {
            productId = query.split("id=")[1]; // Assuming the id is the first parameter
        }
        return productId;
    }
}
