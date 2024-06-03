package com.example.springwebtask.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Product {
    private int id;
    @NotEmpty(message = "入力必須です")
    private int product_id;
    @NotEmpty(message = "入力必須です")
    private String name;
    @NotNull(message = "入力必須です")
    private int price;
    private String category_name;
    private String description;
}
