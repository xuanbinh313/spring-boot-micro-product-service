package com.binhcodev.product_service.services;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.binhcodev.product_service.clients.InventoryClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final InventoryClient inventoryClient;
    private final String url = "https://cellphones.com.vn/iphone-16-pro-max.html";

    public String getProducts() {
        return inventoryClient.getInventories();
    }

    public String createProduct() {
        try {
            // Fetch and parse the webpage
            Document document = Jsoup.connect(url).get();
            System.out.println("Visiting: " + url);

            // Example: extract title and body text
            String title = document.title();
            String bodyText = document.body().text();
            System.out.println("Title: " + title);
            System.out.println("Body: " + bodyText);
            return bodyText;
        } catch (IOException e) {
            System.err.println("Error while crawling " + url + ": " + e.getMessage());
        }
        return "Failed to create product with id: ";
    }
}
