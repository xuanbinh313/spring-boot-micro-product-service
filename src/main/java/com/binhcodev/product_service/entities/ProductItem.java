package com.binhcodev.product_service.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductItem {
    private Long id;
    private String productId;
    private String SKU;
    private int qtyInStock;
    private BigDecimal price;
}
