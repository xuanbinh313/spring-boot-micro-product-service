package com.binhcodev.product_service.services;

import java.util.Map;

import com.binhcodev.product_service.dtos.requests.ProductRequest;
import com.binhcodev.product_service.entities.Product;

public interface CrawlerService {

    public Product getProductBase();

    public String createProductByUrl(ProductRequest productRequest);

    public Map<String,Integer> createVariationFromDocument();

}
