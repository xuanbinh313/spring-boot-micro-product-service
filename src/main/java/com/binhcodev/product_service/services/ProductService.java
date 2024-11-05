package com.binhcodev.product_service.services;

import org.springframework.stereotype.Service;

import com.binhcodev.product_service.clients.InventoryClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final InventoryClient inventoryClient;

    public String getProducts() {
        return inventoryClient.getInventories();
    }
}
