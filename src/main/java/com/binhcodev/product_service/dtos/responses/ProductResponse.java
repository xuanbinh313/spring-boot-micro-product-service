package com.binhcodev.product_service.dtos.responses;

import java.util.List;
import java.util.Locale.Category;

import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.binhcodev.product_service.entities.Product;
import com.binhcodev.product_service.entities.ProductItem;
import com.binhcodev.product_service.entities.VariationOption;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    public ProductResponse(Product product) {
        BeanUtils.copyProperties(product, this);

    }

    private ProductItem productItem;

    private ObjectId id;

    private String name;

    private String promoText;

    private String promoNote;

    private List<String> images;

    private String url;

    private Product parent;

    private Category category;

    private List<VariationOption> variationOptions;
}
