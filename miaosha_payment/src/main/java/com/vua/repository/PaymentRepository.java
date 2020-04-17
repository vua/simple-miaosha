package com.vua.repository;

import com.vua.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-04-09 19:15
 */
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
}
