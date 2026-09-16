package com.example.tuwaiqcapstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "Id must not be empty")
    @Pattern(regexp = "^M-.*", message = "Id must start with 'M-'")
    private String id;

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;
}