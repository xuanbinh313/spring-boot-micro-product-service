package com.binhcodev.product_service.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    public List<Product> createVariationFromDocument(Document document) {
        List<Element> colors = document.selectXpath("//div[@class='box-product-variants']/div[2]/ul/li//a");
        // List<Element> volumeElements =
        // document.selectXpath("//div[@class='list-linked']//a");
        List<String> categories = List.of("Điện thoại", "Apple", "IPhone 16 Series");
        // Variation volumeVariation = createVariationFromDocument("Dung lượng");
        Variation colorVariation = createVariationFromDocument("Màu Sắc");
        Category parentCategory = null;
        Category categoryProduct = null;

        for (int i = 0; i < categories.size(); i++) {
            String nameCategory = categories.get(i);
            Category category = categoryService.findCategoryByName(nameCategory)
                    .orElseGet(() -> {
                        Category newCategory = categoryService.save(Category.builder().name(nameCategory).build());
                        return newCategory;
                    });
            if (i == 0) {
                parentCategory = category;
            } else {
                category.setParent(parentCategory);
                categoryService.save(category);
                if (i == categories.size() - 1) {
                    categoryProduct = category;
                }
            }
        }

        List<Product> products = new ArrayList<>();
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
            String variationName = color.getElementsByTag("strong").text();
            String variationPrice = color.getElementsByTag("span").text().replaceAll("[.đ₫]", "");
            List<VariationOption> variationOptions = new ArrayList<>();
            VariationOption variationOption = variationOptionRepository.findVariationOptionByValue(variationName)
                    .orElseGet(() -> variationOptionRepository
                            .save(VariationOption
                                    .builder()
                                    .value(variationName)
                                    .variation(colorVariation)
                                    .build()));
            variationOptions.add(variationOption);
            Product productBuilder = Product
                    .builder()
                    .price(Integer.parseInt(variationPrice))
                    .category(categoryProduct)
                    .variationOptions(variationOptions)
                    .build();
            products.add(productBuilder);
        }
        return products;
    }

    private Variation createVariationFromDocument(String name) {
        return variationRepository.findVariationByName(name).orElseGet(() -> {
            Variation newVariation = variationRepository.save(Variation.builder().name(name).build());
            return newVariation;
        });
    }
}
