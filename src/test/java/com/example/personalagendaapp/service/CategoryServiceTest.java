package com.example.personalagendaapp.service;

import com.example.personalagendaapp.exception.CategoryAlreadyExistsException;
import com.example.personalagendaapp.exception.CategoryNotFoundException;
import com.example.personalagendaapp.model.Category;
import com.example.personalagendaapp.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Creating a new category - happy flow")
    void createCategoryHappyFlow() {
        Category category = new Category("category");
        when(categoryRepository.createCategory(category)).thenReturn(category);
        when(categoryRepository.getCategoryByName(category.getName())).thenReturn(Optional.empty());

        Category result = categoryService.createCategory(category);

        verify(categoryRepository, times(1)).getCategoryByName(any());
        verify(categoryRepository, times(1)).createCategory(any());
        assertEquals(category.getName(), result.getName());
    }

    @Test
    @DisplayName("Creating a new category - category with the same name already exists")
    void createCategoryNegativeFlow() {
        String exceptionMsg = "Category already exists!";
        Category category = new Category("category");
        when(categoryRepository.getCategoryByName(category.getName())).thenReturn(Optional.of(category));

        CategoryAlreadyExistsException result = assertThrows(
                CategoryAlreadyExistsException.class,
                () -> categoryService.createCategory(category)
        );

        verify(categoryRepository, times(0)).createCategory(any());
        verify(categoryRepository, times(1)).getCategoryByName(any());
        assertEquals(exceptionMsg, result.getMessage());
    }

    @Test
    @DisplayName("Returns list of categories from repository")
    void getAllCategories() {
        Category cat1 = new Category("cat1");
        Category cat2 = new Category("cat2");
        List<Category> categories = List.of(cat1, cat2);
        when(categoryRepository.getAllCategories()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        verify(categoryRepository, times(1)).getAllCategories();
        assertEquals(categories.size(), result.size());
        assertEquals(cat1.getName(), result.get(0).getName());
        assertEquals(cat2.getName(), result.get(1).getName());
    }

    @Test
    @DisplayName("Returns found category")
    void getCategoryByNameHappyFlow() {
        String name = "category";
        Category category = new Category(name);
        when(categoryRepository.getCategoryByName(name)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryByName(name);

        verify(categoryRepository, times(1)).getCategoryByName(name);
        assertEquals(category.getName(), result.getName());
    }

    @Test
    @DisplayName("Throws category not found exception")
    void getCategoryByNameNegativeFlow() {
        String exceptionMsg = "Category not found!";
        String name = "category";
        when(categoryRepository.getCategoryByName(name)).thenReturn(Optional.empty());

        CategoryNotFoundException result = assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.getCategoryByName(name)
        );

        verify(categoryRepository, times(1)).getCategoryByName(name);
        assertEquals(exceptionMsg, result.getMessage());
    }
}