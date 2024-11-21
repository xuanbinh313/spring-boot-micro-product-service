package com.binhcodev.product_service.services.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.binhcodev.product_service.dtos.requests.ProductRequest;
import com.binhcodev.product_service.entities.Product;
import com.binhcodev.product_service.services.CrawlerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DMXCrawlerServiceImpl implements CrawlerService {

    @Override
    public String createProductByUrl(ProductRequest productRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProductByUrl'");
    }

    @Override
    public Map<String, BigDecimal> createVariationFromDocument() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createVariationFromDocument'");
    }

    @Override
    public Product getProductBase() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductBase'");
    }

}
