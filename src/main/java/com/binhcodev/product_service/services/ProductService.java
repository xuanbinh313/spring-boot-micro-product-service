package com.binhcodev.product_service.services;

import java.util.List;

import com.binhcodev.product_service.dtos.requests.ProductRequest;
import com.binhcodev.product_service.dtos.responses.ProductResponse;

public interface ProductService {
    public List<ProductResponse> getProducts();

    public void createProductByUrl(ProductRequest productRequest);

    public ProductResponse getProduct(String id);
}
