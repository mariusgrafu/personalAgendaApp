package com.example.personalagendaapp.controller;

import com.example.personalagendaapp.dto.CategoryRequest;
import com.example.personalagendaapp.mapper.CategoryMapper;
import com.example.personalagendaapp.model.Category;
import com.example.personalagendaapp.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Validated
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping()
    public ResponseEntity<Category> createCategory(
            @Valid
            @RequestBody CategoryRequest categoryRequest
    ) {
        Category createdCategory = categoryService.createCategory(categoryMapper.categoryRequestToCategory(categoryRequest));

        return ResponseEntity.created(URI.create("/categories/" + createdCategory.getName().toLowerCase()))
                .body(createdCategory);
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok()
                .body(categoryService.getAllCategories());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Category> getCategoryByName(
            @PathVariable String name
    ) {
        return ResponseEntity.ok()
                .body(categoryService.getCategoryByName(name));
    }
}
