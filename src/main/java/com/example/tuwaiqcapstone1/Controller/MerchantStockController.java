package com.example.tuwaiqcapstone1.Controller;

import com.example.tuwaiqcapstone1.Api.ApiResponse;
import com.example.tuwaiqcapstone1.Model.MerchantStock;
import com.example.tuwaiqcapstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStocks() {
        ArrayList<MerchantStock> result = merchantStockService.getMerchantStocks();

        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        merchantStockService.addMerchantStock(merchantStock);

        return ResponseEntity.status(200).body(new ApiResponse("MerchantStock added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = merchantStockService.updateMerchantStock(id, merchantStock);

        if (!isUpdated) {
            return ResponseEntity.status(400).body(new ApiResponse("MerchantStock was not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("MerchantStock has ben updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id) {
        boolean isDeleted = merchantStockService.deleteMerchantStock(id);

        if (!isDeleted) {
            return ResponseEntity.status(400).body(new ApiResponse("MerchantStock was not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("MerchantStock has ben deleted successfully"));
    }

    @PutMapping("/addStock/{productId}/{merchantId}/{stock}")
    public ResponseEntity<?> addStock(@PathVariable String productId, @PathVariable String merchantId, @PathVariable int stock) {
        boolean isAdded = merchantStockService.addStock(productId, merchantId, stock);

        if (!isAdded) {
            return ResponseEntity.status(400).body(new ApiResponse("MerchantStock was not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Stock has ben added successfully"));
    }

}