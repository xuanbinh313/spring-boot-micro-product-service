package com.binhcodev.product_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.binhcodev.product_service.dtos.requests.ProductRequest;
import com.binhcodev.product_service.dtos.responses.ProductResponse;
import com.binhcodev.product_service.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getOneProduct(@PathVariable String id) {
        ProductResponse response = productService.getProduct(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/url")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProductByUrl(productRequest);
    }
}
