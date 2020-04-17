package com.vua.repository;

import com.vua.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-06 15:59
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order findByTradeNo(String trade_no);
}
