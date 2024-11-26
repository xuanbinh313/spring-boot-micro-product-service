package com.binhcodev.product_service.dtos.responses;

import java.util.List;

import com.binhcodev.product_service.entities.ProductItem;
import com.binhcodev.product_service.entities.VariationOption;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VariationOptionResponse {
  

    ProductItem productItem;
    List<VariationOption> variationOptions;
}
