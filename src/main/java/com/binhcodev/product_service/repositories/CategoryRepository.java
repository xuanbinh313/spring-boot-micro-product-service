package com.binhcodev.product_service.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.binhcodev.product_service.entities.Category;

public interface CategoryRepository extends MongoRepository<Category, ObjectId> {
    public Optional<Category> findCategoryByName(String name);
}
