package com.example.springwebtask.repository;

import com.example.springwebtask.entity.Category;
import com.example.springwebtask.entity.LoginForm;
import com.example.springwebtask.entity.Product;
import com.example.springwebtask.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        try{
            var param = new MapSqlParameterSource();
            param.addValue("login_id",loginForm.getLoginId());
            param.addValue("password",loginForm.getPassword());
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE login_id = :login_id AND password = :password",
                    param,new DataClassRowMapper<>(User.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }


    }

    @Override
    public List<Product> findAll(){
        return jdbcTemplate.query("SElECT products.id, product_id, products.name , price, categories.name AS category_name FROM products INNER JOIN categories ON products.category_id = categories.id ORDER BY products.id",
                new DataClassRowMapper<>(Product.class));
    }

    @Override
    public List<Product> findByKeyword(String keyword){
        var param = new MapSqlParameterSource();
        param.addValue("keyword","%" + keyword + "%");
        return jdbcTemplate.query("SElECT products.id ,product_id ,products.name ,price ,categories.name AS category_name FROM products INNER JOIN categories ON products.category_id = categories.id WHERE products.name LIKE :keyword ORDER BY products.id",
                param,new DataClassRowMapper<>(Product.class));
    }

    @Override
    public Product findById(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id",id);
        return jdbcTemplate.queryForObject("SElECT products.id, product_id, products.name , price, categories.name AS category_name,description FROM products INNER JOIN categories ON products.category_id = categories.id WHERE products.id = :id ORDER BY products.id",
                param,new DataClassRowMapper<>(Product.class));
    }

    @Override
    public List<Category> findCategory(){

        return jdbcTemplate.query("SELECT * FROM categories",new DataClassRowMapper<>(Category.class));
    }

    @Override
    public int insert(Product product){
        var param = new MapSqlParameterSource();
        param.addValue("product_id", product.getProduct_id());
        param.addValue("name", product.getName());
        param.addValue("price", product.getPrice());
        param.addValue("category_id",product.getCategory_name());
        param.addValue("description",product.getDescription());
        return jdbcTemplate.update("INSERT INTO products (product_id,name,price,category_id,description) VALUES(:product_id, :name, :price, CAST(:category_id AS integer), :description)", param);
    }

    @Override
    public int update(Product product){
        var param = new MapSqlParameterSource();
        param.addValue("id", product.getId());
        param.addValue("product_id", product.getProduct_id());
        param.addValue("name", product.getName());
        param.addValue("price", product.getPrice());
        param.addValue("category_id", product.getCategory_name());

        return jdbcTemplate.update("UPDATE products SET product_id = :product_id, name = :name, price = :price, category_id = CAST(:category_id AS integer) WHERE id = :id", param);
    }


}
