package com.vua.repository;

import com.vua.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-02-29 16:10
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
