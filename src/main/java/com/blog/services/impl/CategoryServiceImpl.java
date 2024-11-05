package com.blog.services.impl;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.categoryDtoToCategory(categoryDto);
        Category savedCategory = categoryRepo.save(category);
        return this.categoryToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId) {
       Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = categoryRepo.save(category);
        return categoryToCategoryDto(updatedCategory);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        return this.categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> list = categoryRepo.findAll().stream().map(category -> categoryToCategoryDto(category)).collect(Collectors.toList());
        return list;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        this.categoryRepo.deleteById(categoryId);
    }

    private Category categoryDtoToCategory(CategoryDto categoryDto)
    {
        return this.modelMapper.map(categoryDto,Category.class);
    }

    private CategoryDto categoryToCategoryDto(Category category)
    {
        return this.modelMapper.map(category,CategoryDto.class);
    }
}
