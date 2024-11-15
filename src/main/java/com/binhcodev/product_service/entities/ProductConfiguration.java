package com.binhcodev.product_service.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ProductConfiguration {
    @Id
    private ObjectId id;

    @DBRef
    private Product product;

    @DBRef
    private VariationOption variationOption;
}
