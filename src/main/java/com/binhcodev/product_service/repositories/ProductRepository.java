package com.binhcodev.product_service.repositories;

import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.binhcodev.product_service.entities.Product;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {
    Set<Product> findByParent(ObjectId parentId);

    List<Product> findAllByName(String name);

    List<Product> findAllByIdIn(List<String> ids);
}
