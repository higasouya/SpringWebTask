package com.example.springwebtask.entity;

import lombok.Data;

@Data
public class Product {
    private int id;
    private int product_id;
    private String name;
    private int price;
    private String category_name;
    private String description;
}
