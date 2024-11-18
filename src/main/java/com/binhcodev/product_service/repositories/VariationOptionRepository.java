package com.binhcodev.product_service.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.binhcodev.product_service.entities.VariationOption;

public interface VariationOptionRepository extends MongoRepository<VariationOption, ObjectId> {
    public Optional<VariationOption> findVariationOptionByValue(String value);

}