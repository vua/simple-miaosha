package com.vua.service;

import com.vua.entity.Order;
import com.vua.entity.Product;
import com.vua.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Optional;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-02-29 16:11
 */
@Service
@Slf4j
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
    @Transactional(rollbackFor = Exception.class)
    public Product save(Product product){
        return productRepository.save(product);
    }

    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;

    @Transactional
    public void updateCapacity(Order order){
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setName("Tx_updateCapacity");
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = dataSourceTransactionManager.getTransaction(definition);
        try {
            Product product = findById(order.getPid());

            int capacity = product.getNumber();
            if (capacity > 0) {
                product.setNumber(capacity - 1);
                save(product);
            } else {
                log.error("库存不足,出现超卖 uid:{} pid:{}", order.getUid(), order.getPid());
            }
        }catch (Exception e){
            dataSourceTransactionManager.rollback(status);
        }
    }

}
