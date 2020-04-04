package com.vua.controller;

import com.vua.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.vua.service.ProductService;

import java.util.List;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-02-29 16:13
 */
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public List<Product> products(){
        return productService.findAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product selectProduct(@PathVariable("id") int id){
        return productService.findById(id);
    }

    @PostMapping("/product")
    public Product insertProduct(Product product){
        return productService.save(product);
    }


    @DeleteMapping("/product")
    public void deleteProduct(Product product){

    }
    @PutMapping("/product")
    public void updateProduct(Product product){

    }
}
