package com.binhcodev.product_service.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document
@Builder
@Getter
@Setter
public class ProductCategory {
    @Id
    private ObjectId id;
    
    @DBRef
    private Product product;

    @DBRef
    private Category category;

    private String name;
}
