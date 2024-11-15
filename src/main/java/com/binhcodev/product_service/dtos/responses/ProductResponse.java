package com.binhcodev.product_service.dtos.responses;

import java.util.Set;

import com.binhcodev.product_service.entities.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse extends Product {
    
    public ProductResponse(Product product) {
        super(product);
    }
    private Set<Product> children;
}
