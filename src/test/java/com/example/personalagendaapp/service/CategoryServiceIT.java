package com.example.personalagendaapp.service;

import com.example.personalagendaapp.exception.CategoryAlreadyExistsException;
import com.example.personalagendaapp.exception.CategoryNotFoundException;
import com.example.personalagendaapp.model.Category;
import com.example.personalagendaapp.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CategoryServiceIT {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    @DisplayName("Create new category")
    public void createCategoryHappyFlow() {
        Category category = new Category("category");

        Category result = categoryService.createCategory(category);

        assertEquals(category.getName(), result.getName());
        assertNotEquals(0, result.getId());
    }

    @Test
    @DisplayName("Cannot create category if another with same name exists")
    public void createCategoryNegativeFlow() {
        Category category = new Category("category");
        categoryRepository.createCategory(category);
        String exceptionMsg = "Category already exists!";

        CategoryAlreadyExistsException result = assertThrows(CategoryAlreadyExistsException.class, () -> categoryService.createCategory(category));

        assertEquals(exceptionMsg, result.getMessage());
    }

    @Test
    @DisplayName("Returns empty list if no there are no categories in the db")
    public void getAllCategoriesEmptyList() {
        List<Category> result = categoryService.getAllCategories();

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Returns list of categories from Database")
    public void getAllCategories() {
        Category cat1 = new Category("category 1");
        Category cat2 = new Category("category 2");
        categoryRepository.createCategory(cat1);
        categoryRepository.createCategory(cat2);
        List<Category> expectedCategories = List.of(cat1, cat2);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(expectedCategories.size(), result.size());
        assertEquals(cat1.getName(), result.get(0).getName());
        assertEquals(cat2.getName(), result.get(1).getName());
    }

    @Test
    @DisplayName("Returns category with the given name")
    public void getCategoryByNameHappyFlow() {
        String name = "category";
        Category category = new Category(name);
        categoryRepository.createCategory(category);

        Category result = categoryService.getCategoryByName(name);

        assertEquals(category.getName(), category.getName());
        assertNotEquals(0, result.getId());
    }

    @Test
    @DisplayName("Throws an exception if no category with given name exists")
    public void getCategoryByNameNegativeFlow() {
        String exceptionMsg = "Category not found!";
        String name = "category";

        CategoryNotFoundException result = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.getCategoryByName(name)
        );

        assertEquals(exceptionMsg, result.getMessage());
    }
}
