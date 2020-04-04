package com.vua.service;

import com.vua.entity.Product;
import com.vua.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-02-29 16:11
 */
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Cacheable(value = "products")
    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    @Cacheable(value = "product",key="#id")
    public Product findById(int id){
        Optional<Product> o = productRepository.findById(id);
        return o.get();
    }

    public Product save(Product product){
        return productRepository.save(product);
    }


}
