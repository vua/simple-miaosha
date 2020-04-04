package com.vua.service;

import com.vua.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value="product-service")
public interface ProductService {
    @GetMapping("/products")
    List<Product> products();
    @GetMapping("/products/{id}")
    Product findById(@PathVariable("id") int id);
}
