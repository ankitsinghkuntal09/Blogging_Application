package com.blog.services;

import com.blog.payloads.CategoryDto;

import java.util.List;

//we are creating interface here just for loose coupling so that we can change its implementation when and where required.
public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

    public CategoryDto getCategory(Integer categoryId);

    public List<CategoryDto> getAllCategories();

    public void deleteCategory(Integer categoryId);

}
