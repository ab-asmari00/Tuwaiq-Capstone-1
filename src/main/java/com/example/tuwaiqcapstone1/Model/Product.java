package com.example.tuwaiqcapstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "Id must not be empty")
    @Pattern(regexp = "^P-.*", message = "Id must start with 'P-'")
    private String id;

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 4, message = "Name must be at least 4 characters long")
    private String name;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be a positive number")
    private double price;

    @NotEmpty(message = "CategoryId must not be empty")
    @Pattern(regexp = "^C-.*", message = "CategoryId must start with 'C-'")
    private String categoryId;

}