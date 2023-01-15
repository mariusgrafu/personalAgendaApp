package com.example.personalagendaapp.controller;

import com.example.personalagendaapp.dto.CategoryRequest;
import com.example.personalagendaapp.exception.CategoryAlreadyExistsException;
import com.example.personalagendaapp.exception.CategoryNotFoundException;
import com.example.personalagendaapp.mapper.CategoryMapper;
import com.example.personalagendaapp.model.Category;
import com.example.personalagendaapp.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private CategoryMapper categoryMapper;
    @MockBean
    private CategoryService categoryService;

    @Test
    @DisplayName("createCategory - Creates new category")
    void createCategoryHappyFlow() throws Exception {
        String name = "category";
        CategoryRequest categoryRequest = new CategoryRequest(name);
        when(categoryService.createCategory(any())).thenReturn(new Category(1, name));

        mockMvc.perform(post("/categories/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(name));
    }
    @Test
    @DisplayName("createCategory - Throws error")
    void createCategoryNegativeFlow() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("category");
        when(categoryService.createCategory(any())).thenThrow(new CategoryAlreadyExistsException());

        mockMvc.perform(post("/categories/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Category already exists!"));
    }

    @Test
    @DisplayName("getAllCategories - Returns all categories")
    void getAllCategories() throws Exception {
        Category cat1 = new Category("cat1");
        Category cat2 = new Category("cat2");
        List<Category> categories = List.of(cat1, cat2);
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/categories/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(cat1.getName()))
                .andExpect(jsonPath("$[1].name").value(cat2.getName()));
    }

    @Test
    @DisplayName("getCategoryByName - Returns category")
    void getCategoryByNameHappyFlow() throws Exception {
        String name = "category";
        Category category = new Category(name);
        when(categoryService.getCategoryByName(name)).thenReturn(category);

        mockMvc.perform(get("/categories/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    @DisplayName("getCategoryByName - Error message")
    void getCategoryByNameNegativeFlow() throws Exception {
        String exceptionMsg = "Category not found!";
        String name = "category";
        when(categoryService.getCategoryByName(name)).thenThrow(new CategoryNotFoundException());

        mockMvc.perform(get("/categories/{name}", name))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exceptionMsg));
    }
}