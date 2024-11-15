package com.binhcodev.product_service.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.binhcodev.product_service.entities.Variation;

public interface VariationRepository extends MongoRepository<Variation, ObjectId> {
    public Optional<Variation> findVariationByName(String name);
}
