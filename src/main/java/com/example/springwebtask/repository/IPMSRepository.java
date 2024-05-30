package com.example.springwebtask.repository;

import com.example.springwebtask.entity.Category;
import com.example.springwebtask.entity.LoginForm;
import com.example.springwebtask.entity.Product;
import com.example.springwebtask.entity.User;

import java.util.List;

public interface IPMSRepository {

    User findAcc(LoginForm loginForm);

    List<Product> findAll();

    List<Product> findByKeyword(String name);

    Product findById(int id);

    List<Category> findCategory();

    int insert(Product product);

    int update(Product product);
}
