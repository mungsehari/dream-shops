package com.hari.controller;

import com.hari.exception.AlreadyExistsException;
import com.hari.model.Category;
import com.hari.response.ApiResponse;
import com.hari.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> category = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse(" found!", category));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));

        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
        try {
            Category theCategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Category added!", theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            Category theCategory = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse(" Found!", theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category theCategory = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse(" Found!", theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse(" Found!", null));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category theCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse(" Update success!", theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
