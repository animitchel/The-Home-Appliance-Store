package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class RootHandler implements HttpHandler {
     @Override
    public void handle(HttpExchange he) throws IOException {
        // Build HTML response using StringBuilder
        StringBuilder response = new StringBuilder();

        response.append("<html>")
                .append("<head>")
                .append("<title>Home Appliance Store Menu</title>")
                .append("<link rel='stylesheet' type='text/css' href='/style.css'>")
                .append("</head>")
                .append("<body>")
                .append("<div class='menu-container'>")
                .append("<h1>Welcome to The Home Appliance Store</h1>")
                .append("<p>Choose from the following options:</p>")
                .append("<ul>")
                .append("<li><a href='/products'>[1] List all products</a></li>")
                .append("<li><a href='/search'>[2] Search for product by ID</a></li>")
                .append("<li><a href='/add'>[3] Add a new product</a></li>")
                .append("<li><a href='/update'>[4] Update a product by ID</a></li>")
                .append("<li><a href='/delete'>[5] Delete a product by ID</a></li>")
                .append("<li><a href='/exit'>[6] Exit</a></li>")
                .append("</ul>")
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

class CssHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        String css = """
                body {
                    font-family: 'Arial', sans-serif;
                    background-color: #f0f4f7;
                    margin: 0;
                    padding: 0;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                }
                
                .menu-container {
                    background-color: #fff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                    width: 80%;
                    max-width: 600px;
                    text-align: center;
                }

                h1 {
                    color: #2c3e50;
                    font-size: 2.5em;
                }

                p {
                    font-size: 1.2em;
                    margin: 20px 0;
                    color: #7f8c8d;
                }

                ul {
                    list-style-type: none;
                    padding: 0;
                    margin: 0;
                }

                li {
                    margin: 10px 0;
                }

                a {
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


