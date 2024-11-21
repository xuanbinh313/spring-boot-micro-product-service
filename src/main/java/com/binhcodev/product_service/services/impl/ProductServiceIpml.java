package com.binhcodev.product_service.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.binhcodev.product_service.clients.InventoryClient;
import com.binhcodev.product_service.dtos.requests.ProductItemRequest;
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
    private final InventoryClient inventoryClient;

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final VariationService variationService;

    public void createProductByUrl(ProductRequest productRequest) {
        CrawlerService service = factory.getCrawlerService(productRequest.getType());
        String name = service.createProductByUrl(productRequest);
        List<Product> existProducts = productRepository.findAllByName(name);
        if (!existProducts.isEmpty()) {
            throw new CommonException(HttpStatus.BAD_REQUEST, "Product exist");
        }
        Category categoryProduct = categoryService.createAllCategories(productRequest.getCategories());
        Variation colorVariation = variationService.createAutoVariationByName("Màu Sắc");
        Map<String, BigDecimal> colorPrices = service.createVariationFromDocument();
        Product productBase = service.getProductBase();
        productBase.setCategory(categoryProduct);
        Product productParent = productRepository.save(productBase);
        List<ProductItemRequest> productItemRequests = new ArrayList<>();
        Map<String, BigDecimal> variationOptionsPrices = new HashMap<>();
        for (Map.Entry<String, BigDecimal> colorPrice : colorPrices.entrySet()) {
            VariationOption variationOption = variationService
                    .createAutoVariationOption(colorPrice.getKey(), colorVariation);
            variationOptionsPrices.put(variationOption.getId().toString(), colorPrice.getValue());
        }

        ProductItemRequest productItemRequest = ProductItemRequest
                .builder()
                .productId(productParent.getId().toString())
                .variationOptionsPrices(variationOptionsPrices)
                .build();
        productItemRequests.add(productItemRequest);
        inventoryClient.saveAll(productItemRequests);
    }

    @Override
    public String getProducts() {
        return inventoryClient.getInventories();
    }

    @Override
    public ProductResponse getProduct(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProduct'");
    }

}
