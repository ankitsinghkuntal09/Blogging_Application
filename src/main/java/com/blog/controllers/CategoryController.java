package com.blog.controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId)
    {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto,categoryId));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId)
    {
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories()
    {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId)
    {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category is deleted successfully",true),HttpStatus.OK);
    }

}
