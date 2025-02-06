package com.shop.ease.product_service.service;

import com.shop.ease.product_service.dto.CategoryDto;
import com.shop.ease.product_service.model.Category;
import com.shop.ease.product_service.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Override
    public List<CategoryDto> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(()->new EntityNotFoundException("Category not found"));
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
//        Category newCategory= convertToEntity(categoryDto);
//        Category saveCategory= categoryRepository.save(newCategory);
//
//        return convertToDto(saveCategory);
        return convertToDto(categoryRepository.save(convertToEntity(categoryDto)));
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryDto.getName());
                    return categoryRepository.save(category);
                })
                .map(this::convertToDto)
                .orElseThrow(()->new EntityNotFoundException("Not Found"));
//        Category oldCategory = categoryRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Not found"));
//        oldCategory.setName(categoryDto.getName());
//        Category updatedCategory= categoryRepository.save(oldCategory);
//        return convertToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDto convertToDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    private Category convertToEntity(CategoryDto categoryDto){
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }

}
