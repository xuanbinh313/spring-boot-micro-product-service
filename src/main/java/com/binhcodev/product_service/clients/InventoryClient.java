package com.binhcodev.product_service.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.binhcodev.product_service.dtos.requests.ProductItemRequest;

@FeignClient(value = "inventory", url = "http://localhost:8082")
public interface InventoryClient {
    @RequestMapping(method = RequestMethod.GET, value = "/products-items")
    String getInventories();

    @RequestMapping(method = RequestMethod.POST, value = "/products-items")
    void saveAll(List<ProductItemRequest> productItemRequests);
}
