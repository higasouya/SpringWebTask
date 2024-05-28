package com.example.springwebtask.service;

import com.example.springwebtask.entity.LoginForm;
import com.example.springwebtask.entity.Product;
import com.example.springwebtask.entity.User;

import java.util.List;

public interface IPMSService {
    User findAcc(LoginForm loginForm);

    List<Product> findAll();

    List<Product> findByKeyword(String name);
}
