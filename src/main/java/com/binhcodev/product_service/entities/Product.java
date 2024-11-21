package com.binhcodev.product_service.entities;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    public Product(Product product) {
        BeanUtils.copyProperties(this, product);
    }

    @Id
    private ObjectId id;

    private String name;

    private String promoText;

    private String promoNote;

    private List<String> images;

    private String url;
    
    @DBRef
    private Product parent;

    @DBRef
    private Category category;

    @DBRef
    private List<VariationOption> variationOptions;

}
