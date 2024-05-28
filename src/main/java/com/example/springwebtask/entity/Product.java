package com.example.springwebtask.entity;

import lombok.Data;

@Data
public class Product {
    private int id;
    private int product_id;
    private int category_id;
    private String name;
    private int price;

}
