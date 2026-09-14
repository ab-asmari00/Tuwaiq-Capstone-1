package com.example.tuwaiqcapstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty
    private String id;

    @NotEmpty
    private String productId;

    @NotEmpty
    private String merchantId;

    @NotNull
    @Min(10)
    private int stock;

}
