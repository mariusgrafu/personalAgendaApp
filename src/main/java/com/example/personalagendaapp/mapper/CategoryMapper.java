package com.example.personalagendaapp.mapper;

import com.example.personalagendaapp.dto.CategoryRequest;
import com.example.personalagendaapp.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category categoryRequestToCategory(CategoryRequest categoryRequest) {
        return new Category(categoryRequest.getName());
    }
}
