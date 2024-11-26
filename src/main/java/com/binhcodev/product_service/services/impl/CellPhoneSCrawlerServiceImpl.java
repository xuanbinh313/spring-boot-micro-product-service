package com.binhcodev.product_service.services.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.binhcodev.product_service.dtos.requests.ProductRequest;
import com.binhcodev.product_service.entities.Product;
import com.binhcodev.product_service.services.CrawlerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CellPhoneSCrawlerServiceImpl implements CrawlerService {
    private Product productBase;
    private BigDecimal basePrice;
    Document document;

    @Override
    public String createProductByUrl(ProductRequest productRequest) {
        try {
            // Fetch and parse the webpage
            document = Jsoup.connect(productRequest.getUrl()).get();
        } catch (IOException e) {
            System.err.println("Error while crawling " + productRequest.getUrl() + ": " + e.getMessage());
            throw new RuntimeException("Error while crawling " + productRequest.getUrl() + ": " + e.getMessage());
        }
        System.out.println("Visiting: " + productRequest.getUrl());

        // Get Product from request
        String name = document.select("h1").first().text();
        Element containerText = document.select("#cpsContent").first();
        String promoNote = containerText.children().first().select(">div").html();
        String promoText = containerText.children().get(1).html();
        String price = document.select(".tpt---price").first().text();
        String currency = price.replaceAll("[.đ]", "");
        basePrice = new BigDecimal(currency);
        List<Element> imageString = document.selectXpath("//div[@class=\"gallery-product-detail mb-2\"]//a");

        List<String> images = new ArrayList<>();
        for (Element el : imageString) {
            images.add(el.attr("href"));
        }

        productBase = Product
                .builder()
                .name(name)
                .promoText(promoText)
                .promoNote(promoNote)
                .images(images)
                .url(productRequest.getUrl())
                .build();
        return name;
    }

    public Map<String, BigDecimal> createVariationFromDocument() {
        List<Element> colors = document.selectXpath("//div[@class='box-product-variants']/div[2]/ul/li//a");
        // List<Element> volumeElements =
        // document.selectXpath("//div[@class='list-linked']//a");
        // Variation volumeVariation = createVariationFromDocument("Dung lượng");

        // for (Element volume : volumeElements) {
        // String variationName = volume.selectXpath("//strong").text();
        // String variationPrice =
        // volume.selectXpath("//span").text().replaceAll("[.đ]", "");
        // VariationOption variationOption =
        // VariationOption.builder().value(variationName).variation(volumeVariation).build();
        // Product product = Product
        // .builder()
        // .price(Integer.parseInt(variationPrice))
        // .category(categoryProduct)
        // .build();
        // products.add(product);
        // variationOptions.add(variationOption);
        // }
        Map<String, BigDecimal> colorPrices = new HashMap<>();

        for (Element color : colors) {
            String variationName = color.getElementsByTag("strong").text();
            String variationPrice = color.getElementsByTag("span").text().replaceAll("[.đ₫]", "");
            colorPrices.put(variationName,new BigDecimal(variationPrice));
        }
        return colorPrices;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    @Override
    public Product getProductBase() {
        return productBase;
    }

}
