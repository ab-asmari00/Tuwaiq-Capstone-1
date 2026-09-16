package com.example.tuwaiqcapstone1.Controller;

import com.example.tuwaiqcapstone1.Api.ApiResponse;
import com.example.tuwaiqcapstone1.Model.Merchant;
import com.example.tuwaiqcapstone1.Model.MerchantStock;
import com.example.tuwaiqcapstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchants() {
        ArrayList<Merchant> result = merchantService.getMerchants();

        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        merchantService.addMerchant(merchant);

        return ResponseEntity.status(200).body(new ApiResponse("Merchant added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = merchantService.updateMerchant(id, merchant);

        if (!isUpdated) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant was not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Merchant has ben updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id) {
        boolean isDeleted = merchantService.deleteMerchant(id);

        if (!isDeleted) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant was not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Merchant has ben deleted successfully"));
    }

    @GetMapping("/getMerchantStocks/{merchantId}")
    public ResponseEntity<?> getMerchantStocks(@PathVariable String merchantId) {
        ArrayList<MerchantStock> result = merchantService.getMerchantStocks(merchantId);

        return ResponseEntity.status(200).body(result);
    }

}