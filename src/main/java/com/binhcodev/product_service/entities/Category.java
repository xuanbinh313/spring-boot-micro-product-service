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
public class Category {
    @Id
    private ObjectId id;

    @DBRef
    private Category parent;
    
    private String name;
}
