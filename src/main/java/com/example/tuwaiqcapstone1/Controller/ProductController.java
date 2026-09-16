package com.example.tuwaiqcapstone1.Controller;

import com.example.tuwaiqcapstone1.Api.ApiResponse;
import com.example.tuwaiqcapstone1.Model.Product;
import com.example.tuwaiqcapstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProducts() {
        ArrayList<Product> result = productService.getProducts();

        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        productService.addProduct(product);

        return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = productService.updateProduct(id, product);

        if (!isUpdated) {
            return ResponseEntity.status(400).body(new ApiResponse("Product was not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Product has ben updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        boolean isDeleted = productService.deleteProduct(id);

        if (!isDeleted) {
            return ResponseEntity.status(400).body(new ApiResponse("Product was not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Product has ben deleted successfully"));
    }

    @PutMapping("/discount")
    public ResponseEntity<?> discount(@RequestParam String productId, @RequestParam double percentage) {
        int result = productService.discount(productId, percentage);

        switch (result) {
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("Product was not found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("Invalid discount percentage"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Discount has ben applied successfully"));
    }

}