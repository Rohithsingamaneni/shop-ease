package com.shop.ease.product_service.service;

import com.shop.ease.product_service.dto.CategoryDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Long id);

    CategoryDto addCategory(@Valid CategoryDto categoryDto);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    void deleteCategory(Long id);
}
