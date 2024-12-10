
package com.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        // Create an HTTP server listening on the specified port.
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Register the root ("/") context with the RootHandler.
        server.createContext("/", new RootHandler());
        server.createContext("/style.css", new CssHandler());

        server.createContext("/products", new ProductRootHandler());
        server.createContext("/productstyle.css", new CssProductHandler());
        server.createContext("/add", new AddProductHandler());
        server.createContext("/product-form.css", new AddProductCSSHandler());

        server.createContext("/delete", new DeleteHandler());

        // Use the default executor (null).
        server.setExecutor(null);

        // Start the server.
        server.start();

        // Print a message indicating the server is running.
        System.out.println("The server is listening on port " + PORT);
    }
}
