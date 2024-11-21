package com.binhcodev.product_service.dtos.requests;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductItemRequest {
    private String productId;
    private Map<String, BigDecimal> variationOptionsPrices;
}
