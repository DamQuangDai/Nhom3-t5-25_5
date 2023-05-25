package com.example.t5255.services;

import com.example.t5255.entity.Product;
import com.example.t5255.repository.ProductRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> getAll(){
        return productRepository.findAll();
    }


    public void add(Product newProduct){
        productRepository.save(newProduct);
    }
}
