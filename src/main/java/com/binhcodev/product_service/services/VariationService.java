package com.binhcodev.product_service.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.binhcodev.product_service.entities.Category;
import com.binhcodev.product_service.entities.Product;
import com.binhcodev.product_service.entities.Variation;
import com.binhcodev.product_service.entities.VariationOption;
import com.binhcodev.product_service.repositories.VariationOptionRepository;
import com.binhcodev.product_service.repositories.VariationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VariationService {
    private final VariationOptionRepository variationOptionRepository;
    private final VariationRepository variationRepository;
    private final CategoryService categoryService;

    public Set<Product> createVariationFromDocument(Document document) {
        List<Element> colors = document.selectXpath("//div[@class='box-product-variants']/div[2]/ul/li");
        // List<Element> volumeElements = document.selectXpath("//div[@class='list-linked']//a");
        Elements categories = document.selectXpath("//div[@id='breadcrumbs']//li//a");
        // Variation volumeVariation = createVariationFromDocument("Dung lượng");
        Variation colorVariation = createVariationFromDocument("Màu Sắc");
        Category parentCategory = null;
        Category categoryProduct = null;

        for (int i = 1; i < categories.size() - 1; i++) {
            String nameCategory = categories.get(i).text();
            Category category = categoryService.findCategoryByName(nameCategory)
                    .orElseGet(() -> {
                        Category newCategory = Category.builder().name(nameCategory).build();
                        categoryService.save(newCategory);
                        return newCategory;
                    });
            if (i == 1) {
                parentCategory = category;
            } else {
                category.setParent(parentCategory);
                categoryService.save(category);
                if (i == categories.size() - 2) {
                    categoryProduct = category;
                }
            }
        }

        Set<VariationOption> variationOptions = new HashSet<>();
        Set<Product> products = new HashSet<>();
        // for (Element volume : volumeElements) {
        //     String variationName = volume.selectXpath("//strong").text();
        //     String variationPrice = volume.selectXpath("//span").text().replaceAll("[.đ]", "");
        //     VariationOption variationOption = VariationOption.builder().value(variationName).variation(volumeVariation).build();
        //     Product product = Product
        //             .builder()
        //             .price(Integer.parseInt(variationPrice))
        //             .category(categoryProduct)
        //             .build();
        //     products.add(product);
        //     variationOptions.add(variationOption);
        // }
        for (Element color : colors) {
            String variationName = color.selectXpath("//strong").text();
            String variationPrice = color.selectXpath("//span").text().replaceAll("[.đ]", "");
            VariationOption variationOption = VariationOption.builder().value(variationName).variation(colorVariation).build();
            Product product = Product
                    .builder()
                    .price(Integer.parseInt(variationPrice))
                    .category(categoryProduct)
                    .build();
            products.add(product);
            variationOptions.add(variationOption);
        }
        variationOptionRepository.saveAll(variationOptions);
        return products;
    }

    private Variation createVariationFromDocument(String name) {
        return variationRepository.findVariationByName(name).orElseGet(() -> {
            Variation newVariation = variationRepository.save(Variation.builder().name(name).build());
            return newVariation;
        });
    }
}
