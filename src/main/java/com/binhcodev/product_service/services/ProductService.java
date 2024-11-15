package com.binhcodev.product_service.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.binhcodev.product_service.clients.InventoryClient;
import com.binhcodev.product_service.dtos.requests.ProductRequest;
import com.binhcodev.product_service.dtos.responses.ProductResponse;
import com.binhcodev.product_service.entities.Product;
import com.binhcodev.product_service.entities.Variation;
import com.binhcodev.product_service.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final InventoryClient inventoryClient;
    private final ProductRepository productRepository;
    private final VariationService variationService;
    private final String url = "https://cellphones.com.vn/iphone-16-pro-max.html";

    public String getProducts() {
        return inventoryClient.getInventories();
    }

    public void createProductByUrl(ProductRequest productRequest) {
        try {
            // Fetch and parse the webpage
            Document document = Jsoup.connect(productRequest.getUrl()).get();
            System.out.println("Visiting: " + url);

            // Get Product from request
            String name = document.select("h1").first().text();
            Element containerText = document.select("#cpsContent").first();
            String promoNote = containerText.children().first().select(">div").html();
            String promoText = containerText.children().get(1).html();
            String price = document.select(".tpt---price").first().text();
            String currency = price.replaceAll("[.đ]", "");
            List<Element> imageString = document.selectXpath("//div[@class=\"gallery-product-detail mb-2\"]//a");
            // Get Variation from request
            Set<Product> products = variationService.createVariationFromDocument(document);

            List<String> images = new ArrayList<>();
            for (Element el : imageString) {
                images.add(el.attr("href"));
            }
            Product productBase = Product
                    .builder()
                    .name(name)
                    .promoText(promoText)
                    .promoNote(promoNote)
                    .images(images)
                    .price(Integer.parseInt(currency))
                    .build();
            productRepository.save(productBase);
            for (Product item : products) {
                BeanUtils.copyProperties(productBase, item,"");
            }
            productRepository.saveAll(products);

        } catch (IOException e) {
            System.err.println("Error while crawling " + url + ": " + e.getMessage());
            throw new RuntimeException("Error while crawling " + url + ": " + e.getMessage());
        }
    }

    public ProductResponse getProduct(String id) {
        Product baseProduct = productRepository.findById(new ObjectId(id)).orElseThrow();
        Set<Product> children = productRepository.findByParent(baseProduct.getId());
        ProductResponse response = new ProductResponse(baseProduct);
        response.setChildren(children);
        return response;
    }
}
