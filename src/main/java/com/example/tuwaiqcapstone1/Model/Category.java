package com.example.tuwaiqcapstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty
    private String id;

    @NotEmpty
    @Size(min = 3)
    private String name;
}
