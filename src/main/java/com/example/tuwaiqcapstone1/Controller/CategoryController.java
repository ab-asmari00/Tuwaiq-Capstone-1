package com.example.tuwaiqcapstone1.Controller;

import com.example.tuwaiqcapstone1.Api.ApiResponse;
import com.example.tuwaiqcapstone1.Model.Category;
import com.example.tuwaiqcapstone1.Model.Product;
import com.example.tuwaiqcapstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?> getCategories() {
        ArrayList<Category> result = categoryService.getCategories();

        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        categoryService.addCategory(category);

        return ResponseEntity.status(200).body(new ApiResponse("Category added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = categoryService.updateCategory(id, category);

        if (!isUpdated) {
            return ResponseEntity.status(400).body(new ApiResponse("Category was not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Category has ben updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        boolean isDeleted = categoryService.deleteCategory(id);

        if (!isDeleted) {
            return ResponseEntity.status(400).body(new ApiResponse("Category was not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Category has ben deleted successfully"));
    }

    @GetMapping("/getCategoryProducts/{id}")
    public ResponseEntity<?> getCategoryProducts(@PathVariable String id) {
        ArrayList<Product> result = categoryService.GetCategoryProducts(id);

        return ResponseEntity.status(200).body(result);
    }

}