package com.example.tuwaiqcapstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty
    private String id;

    @NotEmpty
    @Size(min = 5)
    private String username;

    @NotEmpty
    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$")
    private String password;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "Admin|Customer")
    private String role;

    @NotNull
    @Positive
    private double balance;

}
