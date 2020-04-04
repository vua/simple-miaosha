package com.vua.service;

import com.vua.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-06 17:08
 */
@Service
@Slf4j
@RocketMQMessageListener(topic = "CREATE_ORDER", consumerGroup = "miaosha")
//
public class CreateOrderConsumer implements RocketMQListener<String> {
    @Autowired
    OrderService orderService;
    @Override
    public void onMessage(String message) {
        log.info("create order: "+message );
        //创建一个未支付的订单
        orderService.save(orderService.nonpaymentOrder(message));
    }
}
