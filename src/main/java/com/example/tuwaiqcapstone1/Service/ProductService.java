package com.example.tuwaiqcapstone1.Service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductService {

    // Don't forget!! Add validation messages =======#%
    @NotEmpty
    private String id;

    @NotEmpty
    @Size(min = 4)
    private String name;

    @NotEmpty
    @Positive
    private double price;

    @NotEmpty
    private String categoryID;

}
