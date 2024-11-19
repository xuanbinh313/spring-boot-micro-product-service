package com.binhcodev.product_service.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.binhcodev.product_service.clients.InventoryClient;
import com.binhcodev.product_service.dtos.requests.ProductRequest;
import com.binhcodev.product_service.dtos.responses.ProductResponse;
import com.binhcodev.product_service.entities.Category;
import com.binhcodev.product_service.entities.Product;
import com.binhcodev.product_service.exceptions.CommonException;
import com.binhcodev.product_service.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final InventoryClient inventoryClient;
    private final ProductRepository productRepository;
    private final VariationService variationService;
    private final String url = "https://cellphones.com.vn/iphone-16-pro-max.html";
    private final CategoryService categoryService;

    public String getProducts() {
        return inventoryClient.getInventories();
    }

    public void createProductByUrl(ProductRequest productRequest) {
        Document document;
        try {
            // Fetch and parse the webpage
            document = Jsoup.connect(productRequest.getUrl()).get();
        } catch (IOException e) {
            System.err.println("Error while crawling " + url + ": " + e.getMessage());
            throw new RuntimeException("Error while crawling " + url + ": " + e.getMessage());
        }
        System.out.println("Visiting: " + productRequest.getUrl());

        // Get Product from request
        String name = document.select("h1").first().text();
        List<Product> existProducts = productRepository.findAllByName(name);
        if (!existProducts.isEmpty()) {
            throw new CommonException(HttpStatus.BAD_REQUEST, "Product exist");
        }
        Element containerText = document.select("#cpsContent").first();
        String promoNote = containerText.children().first().select(">div").html();
        String promoText = containerText.children().get(1).html();
        String price = document.select(".tpt---price").first().text();
        String currency = price.replaceAll("[.đ]", "");
        List<Element> imageString = document.selectXpath("//div[@class=\"gallery-product-detail mb-2\"]//a");

        List<String> images = new ArrayList<>();
        for (Element el : imageString) {
            images.add(el.attr("href"));
        }
        Category categoryProduct = categoryService.createAllCategories(productRequest.getCategories());

        Product productBase = Product
                .builder()
                .name(name)
                .promoText(promoText)
                .promoNote(promoNote)
                .images(images)
                .category(categoryProduct)
                .url(productRequest.getUrl())
                .price(Integer.parseInt(currency))
                .build();
        productRepository.save(productBase);
        // Get Variation from request
        List<Product> products = variationService.createVariationFromDocument(document, categoryProduct);
        for (Product item : products) {
            BeanUtils.copyProperties(productBase, item, "id", "price", "variationOptions");
        }
        productRepository.saveAll(products);

    }

    public ProductResponse getProduct(String id) {
        Product baseProduct = productRepository.findById(new ObjectId(id)).orElseThrow();
        Set<Product> children = productRepository.findByParent(baseProduct.getId());
        ProductResponse response = new ProductResponse(baseProduct);
        response.setChildren(children);
        return response;
    }
}
