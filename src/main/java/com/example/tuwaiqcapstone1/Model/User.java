package com.example.tuwaiqcapstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "Id must not be empty")
    @Pattern(regexp = "^U-.*", message = "Id must start with 'U-'")
    private String id;

    @NotEmpty(message = "Username must not be empty")
    @Size(min = 5, message = "Username must be at least 5 characters long")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password must contain at least one letter and one number")
    private String password;

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotEmpty(message = "Role must not be empty")
    @Pattern(regexp = "Admin|Customer", message = "Role must be either 'Admin' or 'Customer'")
    private String role;

    @NotNull(message = "Balance must not be null")
    @Positive(message = "Balance must be a positive number")
    private double balance;

}