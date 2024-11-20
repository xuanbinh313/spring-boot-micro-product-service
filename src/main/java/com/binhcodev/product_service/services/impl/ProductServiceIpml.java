package com.binhcodev.product_service.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.binhcodev.product_service.dtos.requests.ProductRequest;
import com.binhcodev.product_service.dtos.responses.ProductResponse;
import com.binhcodev.product_service.entities.Category;
import com.binhcodev.product_service.entities.Product;
import com.binhcodev.product_service.entities.Variation;
import com.binhcodev.product_service.entities.VariationOption;
import com.binhcodev.product_service.exceptions.CommonException;
import com.binhcodev.product_service.factories.CrawlerServiceFactory;
import com.binhcodev.product_service.repositories.ProductRepository;
import com.binhcodev.product_service.services.CategoryService;
import com.binhcodev.product_service.services.CrawlerService;
import com.binhcodev.product_service.services.ProductService;
import com.binhcodev.product_service.services.VariationService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ProductServiceIpml implements ProductService {

    private final CrawlerServiceFactory factory;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final VariationService variationService;
    private List<Product> products;

    public void createProductByUrl(ProductRequest productRequest) {
        CrawlerService service = factory.getCrawlerService(productRequest.getType());
        String name = service.createProductByUrl(productRequest);
        List<Product> existProducts = productRepository.findAllByName(name);
        if (!existProducts.isEmpty()) {
            throw new CommonException(HttpStatus.BAD_REQUEST, "Product exist");
        }
        Category categoryProduct = categoryService.createAllCategories(productRequest.getCategories());
        Variation colorVariation = variationService.createAutoVariationByName("Màu Sắc");
        Map<String, Integer> colorPrices = service.createVariationFromDocument();
        Product productBase = service.getProductBase();
        productBase.setCategory(categoryProduct);
        Product productParent = productRepository.save(productBase);
        for (Map.Entry<String, Integer> colorPrice : colorPrices.entrySet()) {
            List<VariationOption> variationOptions = new ArrayList<>();
            VariationOption variationOption = variationService.createAutoVariationOption(colorPrice.getKey(),
                    colorVariation);
            variationOptions.add(variationOption);
            Product productBuilder = Product
                    .builder()
                    .price(colorPrice.getValue())
                    .variationOptions(variationOptions)
                    .parent(productParent)
                    .build();
            BeanUtils.copyProperties(productBase, productBuilder, "id", "price", "variationOptions");
            products.add(productBuilder);
        }

        productRepository.saveAll(products);
    }

    @Override
    public String getProducts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProducts'");
    }

    @Override
    public ProductResponse getProduct(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProduct'");
    }

}
