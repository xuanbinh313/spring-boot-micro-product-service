package com.binhcodev.product_service.services;

import java.util.List;
import java.util.Optional;

import com.binhcodev.product_service.entities.Category;

public interface CategoryService {

    public Category createAllCategories(List<String> categories);

    public Optional<Category> findCategoryByName(String name);

    public Category save(Category category);

}