package com.binhcodev.product_service.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.binhcodev.product_service.entities.Category;
import com.binhcodev.product_service.entities.Product;
import com.binhcodev.product_service.entities.VariationOption;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CellPhoneSService {
    Document document;
    Set<Product> products;
    Product baseProduct;
    List<Category> categories;
    Set<VariationOption> variationOptions;

    public CellPhoneSService(Document doc) {
        document = doc;
        products = new HashSet<>();
        baseProduct = initProductBase(doc);
        categories = initCategories(doc);
        initColorsAndProducts(doc);
    }

    private Product initProductBase(Document doc) {
        // Get Product from request
        String name = document.select("h1").first().text();
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
        Product productBase = Product
                .builder()
                .name(name)
                .promoText(promoText)
                .promoNote(promoNote)
                .images(images)
                .price(Integer.parseInt(currency))
                .build();
        return productBase;
    }

    private List<Category> initCategories(Document doc) {
        Elements categoryElements = document.selectXpath("//div[@id='breadcrumbs']//li//a");
        // List<Element> volumeElements = document.selectXpath("//div[@class='list-linked']//a");
        List<Category> categoriesList = new ArrayList<>();
        for (int i = 1; i < categoryElements.size() - 1; i++) {
            String nameCategory = categoryElements.get(i).text();
            Category category = Category.builder().name(nameCategory).build();
            categoriesList.add(category);
        }
        return categoriesList;
    }

    private void initColorsAndProducts(Document doc) {
        List<Element> colors = document.selectXpath("//div[@class='box-product-variants']/div[2]/ul/li");
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
        for (Element color : colors) {
            String variationName = color.selectXpath("//strong").text();
            String variationPrice = color.selectXpath("//span").text().replaceAll("[.đ]", "");
            Product product = Product
                    .builder()
                    .price(Integer.parseInt(variationPrice))
                    .build();
            products.add(product);
            VariationOption variationOption = VariationOption.builder().value(variationName).build();
            variationOptions.add(variationOption);
        }
    }

}
