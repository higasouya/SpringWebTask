package com.example.springwebtask.service;

import com.example.springwebtask.entity.LoginForm;
import com.example.springwebtask.entity.Product;
import com.example.springwebtask.entity.User;
import com.example.springwebtask.repository.PMSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PMSService implements IPMSService{

    @Autowired
    private PMSRepository pmsRepository;

    @Override
    public User findAcc(LoginForm loginForm){
        return pmsRepository.findAcc(loginForm);
    }

    @Override
    public List<Product> findAll(){ return pmsRepository.findAll();}

    @Override
    public List<Product> findByKeyword(String keyword){ return pmsRepository.findByKeyword(keyword);}
}
