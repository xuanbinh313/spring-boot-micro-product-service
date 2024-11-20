package com.binhcodev.product_service.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.binhcodev.product_service.entities.Category;
import com.binhcodev.product_service.repositories.CategoryRepository;
import com.binhcodev.product_service.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createAllCategories(List<String> categories) {
        Category parentCategory = null;
        Category categoryProduct = null;
        for (int i = 0; i < categories.size(); i++) {
            String nameCategory = categories.get(i);
            Category category = findCategoryByName(nameCategory)
                    .orElseGet(() -> {
                        Category newCategory = save(Category.builder().name(nameCategory).build());
                        return newCategory;
                    });
            if (parentCategory != null) {
                category.setParent(parentCategory);
            }
            save(category);
            parentCategory = category;
            if (i == categories.size() - 1) {
                categoryProduct = category;
            }
        }
        return categoryProduct;
    }

    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

}