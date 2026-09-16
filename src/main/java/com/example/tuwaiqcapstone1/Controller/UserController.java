package com.example.tuwaiqcapstone1.Controller;

import com.example.tuwaiqcapstone1.Api.ApiResponse;
import com.example.tuwaiqcapstone1.Model.User;
import com.example.tuwaiqcapstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers() {
        ArrayList<User> result = userService.getUsers();

        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        userService.addUser(user);

        return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = userService.updateUser(id, user);

        if (!isUpdated) {
            return ResponseEntity.status(400).body(new ApiResponse("User was not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("User has ben updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        boolean isDeleted = userService.deleteUser(id);

        if (!isDeleted) {
            return ResponseEntity.status(400).body(new ApiResponse("User was not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("User has ben deleted successfully"));
    }

    @PutMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {
        int result = userService.buyProduct(userId, productId, merchantId);

        switch (result) {
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("User was not found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("Product was not found"));
            case 3:
                return ResponseEntity.status(400).body(new ApiResponse("Merchant was not found"));
            case 4:
                return ResponseEntity.status(400).body(new ApiResponse("Product is out of stock"));
            case 5:
                return ResponseEntity.status(400).body(new ApiResponse("Balance is insufficient to buy this product"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Purchase has ben completed successfully"));
    }

    @PutMapping("/addBalance/{userId}/{amount}")
    public ResponseEntity<?> addBalance(@PathVariable String userId, @PathVariable double amount) {
        int result = userService.addBalance(userId, amount);

        switch (result) {
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("User was not found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("Invalid amount"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Balance has ben added successfully"));
    }

    @PostMapping("/addMerchant/{userId}/{merchantId}/{merchantName}")
    public ResponseEntity<?> addMerchant(@PathVariable String userId, @PathVariable String merchantId, @PathVariable String merchantName) {
        int result = userService.addMerchant(userId, merchantId, merchantName);

        switch (result) {
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("User was not found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("User is not an Admin"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Merchant has ben added successfully"));
    }

    @PutMapping("/groupBuy/{merchantStockId}")
    public ResponseEntity<ApiResponse> groupBuy(@PathVariable String merchantStockId, @RequestBody ArrayList<Map<String, Object>> buyers) {

        int statusCode = userService.groupBuy(merchantStockId, buyers);

        switch (statusCode) {
            case 0:
                return ResponseEntity.status(200).body(new ApiResponse("Group buy completed successfully!"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("MerchantStock ID must start with MS"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("Group buy requires at least one buyer."));
            case 3:
                return ResponseEntity.status(400).body(new ApiResponse("No merchant stock was found"));
            case 4:
                return ResponseEntity.status(400).body(new ApiResponse("No product was found"));
            case 5:
                return ResponseEntity.status(400).body(new ApiResponse("Quantity field is required for all buyers"));
            case 6:
                return ResponseEntity.status(400).body(new ApiResponse("Quantity must be greater than zero."));
            case 7:
                return ResponseEntity.status(400).body(new ApiResponse("Quantity must be a valid number"));
            case 8:
                return ResponseEntity.status(400).body(new ApiResponse("Insufficient merchant stock for the demanded quantity"));
            case 9:
                return ResponseEntity.status(400).body(new ApiResponse("User ID is missing or must start with U-"));
            case 10:
                return ResponseEntity.status(400).body(new ApiResponse("User not found"));
            case 11:
                return ResponseEntity.status(400).body(new ApiResponse("User has insufficient balance"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("An unexpected error occurred."));
        }
    }

    @PutMapping("/tradeIn/{userId}/{ownedProductId}/{targetMerchantStockId}")
    public ResponseEntity<ApiResponse> tradeIn(@PathVariable String userId, @PathVariable String ownedProductId, @PathVariable String targetMerchantStockId) {

        int statusCode = userService.tradeIn(userId, ownedProductId, targetMerchantStockId);

        switch (statusCode) {
            case 0:
                return ResponseEntity.status(200).body(new ApiResponse("Product trade-in completed successfully!"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("User ID must start with U-"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("Owned Product ID must start with P-"));
            case 3:
                return ResponseEntity.status(400).body(new ApiResponse("Target MerchantStock ID must start with MS-"));
            case 4:
                return ResponseEntity.status(400).body(new ApiResponse("User not found"));
            case 5:
                return ResponseEntity.status(400).body(new ApiResponse("Owned product not found"));
            case 6:
                return ResponseEntity.status(400).body(new ApiResponse("Target merchant stock not found"));
            case 7:
                return ResponseEntity.status(400).body(new ApiResponse("Target item is out of stock"));
            case 8:
                return ResponseEntity.status(400).body(new ApiResponse("Target product record not found"));
            case 9:
                return ResponseEntity.status(400).body(new ApiResponse("User has insufficient balance to pay the price difference"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("An unexpected error occurred."));
        }
    }
}