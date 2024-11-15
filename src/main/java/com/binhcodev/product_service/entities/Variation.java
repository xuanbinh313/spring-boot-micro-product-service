package com.binhcodev.product_service.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
@Builder
public class Variation {
    @Id
    private ObjectId id;

    private String name;
}
