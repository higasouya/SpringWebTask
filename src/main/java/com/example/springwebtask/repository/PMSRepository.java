package com.example.springwebtask.repository;

import com.example.springwebtask.entity.LoginForm;
import com.example.springwebtask.entity.Product;
import com.example.springwebtask.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PMSRepository implements IPMSRepository{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public User findAcc(LoginForm loginForm){
        var param = new MapSqlParameterSource();
        param.addValue("login_id",loginForm.getLoginId());
        param.addValue("password",loginForm.getPassword());
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE login_id = :login_id AND password = :password",
                param,new DataClassRowMapper<>(User.class));

    }

    @Override
    public List<Product> findAll(){
        return jdbcTemplate.query("SELECT * FROM products ORDER BY id",
                new DataClassRowMapper<>(Product.class));
    }

    @Override
    public List<Product> findByKeyword(String keyword){
        var param = new MapSqlParameterSource();
        param.addValue("keyword",keyword);
        return jdbcTemplate.query("SElECT * FROM products WHERE name LIKE '%:keyword%'",param,new DataClassRowMapper<>(Product.class));
    }
}
