package com.binhcodev.product_service.dtos.responses;

import java.util.List;
import java.util.Locale.Category;

import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;

import com.binhcodev.product_service.entities.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    public ProductResponse(Product product) {
        BeanUtils.copyProperties(product, this);

    }

    private List<VariationOptionResponse> children;

    private ObjectId id;

    private String name;

    private String promoText;

    private String promoNote;

    private List<String> images;

    private String url;

    private Product parent;

    private Category category;
}
