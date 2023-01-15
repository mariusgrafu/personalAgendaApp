package com.example.personalagendaapp.service;

import com.example.personalagendaapp.exception.CategoryAlreadyExistsException;
import com.example.personalagendaapp.exception.CategoryNotFoundException;
import com.example.personalagendaapp.model.Category;
import com.example.personalagendaapp.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        Optional<Category> categoryWithSameName = categoryRepository.getCategoryByName(category.getName());

        if (categoryWithSameName.isPresent()) {
            throw new CategoryAlreadyExistsException();
        }

        return categoryRepository.createCategory(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public Category getCategoryByName(String name) {
        Optional<Category> optionalCategory = categoryRepository.getCategoryByName(name);

        if (!optionalCategory.isPresent()) {
            throw new CategoryNotFoundException();
        }

        return optionalCategory.get();
    }
}
