package com.vua.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-26 21:22
 */
//消费订单支付消息
@Service
@Slf4j
@RocketMQMessageListener(topic = "ORDER_PAYMENT", consumerGroup = "miaosha")
public class PaymentOrderConsumer implements RocketMQListener<String> {
    @Autowired
    OrderService orderService;

    @Override
    //@Transactional()
    public void onMessage(String message) {
        log.info("update order status: " + message);
        //创建一个未支付的订单
        orderService.save(orderService.paymentOrder(message));
    }
}
