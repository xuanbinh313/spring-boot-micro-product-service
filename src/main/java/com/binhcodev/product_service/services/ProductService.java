package com.binhcodev.product_service.services;

import com.binhcodev.product_service.dtos.requests.ProductRequest;
import com.binhcodev.product_service.dtos.responses.ProductResponse;

public interface ProductService {
    public String getProducts();

    public void createProductByUrl(ProductRequest productRequest);

    public ProductResponse getProduct(String id);
}
