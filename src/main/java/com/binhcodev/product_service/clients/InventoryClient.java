package com.binhcodev.product_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "inventory", url = "http://localhost:8082")
public interface InventoryClient {
    @RequestMapping(method = RequestMethod.GET, value = "/inventories")
    String getInventories();

}
