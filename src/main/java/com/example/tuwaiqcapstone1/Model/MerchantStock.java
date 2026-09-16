package com.example.tuwaiqcapstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "Id must not be empty")
    @Pattern(regexp = "^MS-.*", message = "Id must start with 'MS-'")
    private String id;

    @NotEmpty(message = "ProductId must not be empty")
    @Pattern(regexp = "^P-.*", message = "ProductId must start with 'P-'")
    private String productId;

    @NotEmpty(message = "MerchantId must not be empty")
    @Pattern(regexp = "^M-.*", message = "MerchantId must start with 'M-'")
    private String merchantId;

    @NotNull(message = "Stock must not be null")
    @Min(value = 10, message = "Stock must be at least 10")
    private int stock;

}