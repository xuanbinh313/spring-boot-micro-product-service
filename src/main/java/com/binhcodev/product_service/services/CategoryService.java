package com.binhcodev.product_service.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.binhcodev.product_service.entities.Category;
import com.binhcodev.product_service.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }


}