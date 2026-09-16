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

    @PutMapping("/buy")
    public ResponseEntity<?> buyProduct(@RequestParam String userId, @RequestParam String productId, @RequestParam String merchantId) {
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

    @PutMapping("/addBalance")
    public ResponseEntity<?> addBalance(@RequestParam String userId, @RequestParam double amount) {
        int result = userService.addBalance(userId, amount);

        switch (result) {
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("User was not found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("Invalid amount"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Balance has ben added successfully"));
    }

    @PostMapping("/addMerchant")
    public ResponseEntity<?> addMerchant(@RequestParam String userId, @RequestParam String merchantId, @RequestParam String merchantName) {
        int result = userService.addMerchant(userId, merchantId, merchantName);

        switch (result) {
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("User was not found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("User is not an Admin"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Merchant has ben added successfully"));
    }

}