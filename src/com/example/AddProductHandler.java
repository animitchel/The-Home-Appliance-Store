package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AddProductHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().equalsIgnoreCase("GET")) {
            String formHtml = "<html>" +
                    "<head>" +
                    "<title>Product Form</title>" +
                    "<link rel='stylesheet' type='text/css' href='/product-form.css'>" + // Link to CSS
                    "</head>" +
                    "<body>" +
                    "<div class='form-container'>" +
                    "<h1>Product Form</h1>" +
                    "<form method='POST' action='/add'>" +
                    "SKU: <input type='text' name='sku' class='input-field'><br>" +
                    "Description: <input type='text' name='description' class='input-field'><br>" +
                    "Category: <input type='text' name='category' class='input-field'><br>" +
                    "Price: <input type='number' step='0.01' name='price' class='input-field'><br>" +
                    "<button type='submit' class='submit-button'>Submit</button>" +
                    "</form>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            he.sendResponseHeaders(200, formHtml.getBytes().length);
            OutputStream os = he.getResponseBody();
            os.write(formHtml.getBytes());
            os.close();
        } else if (he.getRequestMethod().equalsIgnoreCase("POST")) {
            // Process the form submission
            InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder formData = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                formData.append(line);
            }

            Map<String, String> formParams = Util.requestStringToMap(formData.toString());

            // Extract product details from the form parameters
            String sku = formParams.get("sku");
            String description = formParams.get("description");
            String category = formParams.get("category");
            int price = Integer.parseInt(formParams.get("price"));

            HomeApplianceDAO dao = new HomeApplianceDAO();
            HomeAppliance homeAppliance = new HomeAppliance(sku, description, category, price);
            try {
                dao.addProduct(homeAppliance);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Redirect back to the product list
            he.getResponseHeaders().set("Location", "/products");
            he.sendResponseHeaders(302, -1); // 302: Found (redirect)
            he.getResponseBody().close();
        }
    }

}

class Util {
    public static HashMap<String, String> requestStringToMap(String request) {
        HashMap<String, String> map = new HashMap<>();
        String[] pairs = request.split("&");
        for (String pair : pairs) {
            try {
                String[] kv = pair.split("=");
                if (kv.length == 2) { // Ensure the key-value pair is valid
                    String key = URLDecoder.decode(kv[0], "UTF-8");
                    String value = URLDecoder.decode(kv[1], "UTF-8");
                    map.put(key, value);
                }
            } catch (UnsupportedEncodingException e) {
                System.err.println(e.getMessage());
            }
        }
        return map;
    }
}

class AddProductCSSHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Define the CSS styles
        String css = "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f9; }" +
                ".form-container " +
                "{ max-width: 400px; " +
                "margin: 50px auto; " +
                "padding: 20px; background: #fff; " +
                "border-radius: 8px; " +
                "box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                "h1 { text-align: center; color: #333; }" +
                ".input-field " +
                "{ width: 100%; " +
                "padding: 10px; " +
                "margin: 10px 0; " +
                "border: 1px solid #ccc; border-radius: 4px; }" +
                ".submit-button " +
                "{ background-color: #007bff; " +
                "color: white; " +
                "padding: 10px; " +
                "border: none; " +
                "border-radius: 4px; cursor: pointer; }" +
                ".submit-button:hover { background-color: #0056b3; }";

        he.getResponseHeaders().set("Content-Type", "text/css");
        he.sendResponseHeaders(200, css.getBytes().length);
        OutputStream os = he.getResponseBody();
        os.write(css.getBytes());
        os.close();
    }
}


