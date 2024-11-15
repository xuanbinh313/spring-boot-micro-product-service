package com.binhcodev.product_service.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.binhcodev.product_service.entities.ProductConfiguration;

public interface ProductConfigurationRepository extends MongoRepository<ProductConfiguration,ObjectId> {
    
}
