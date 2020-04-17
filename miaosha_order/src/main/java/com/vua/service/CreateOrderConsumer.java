package com.vua.service;

import com.vua.entity.CreOrdMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
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
@RocketMQMessageListener(topic = "CREATE_ORDER", consumerGroup = "create_order")
public class CreateOrderConsumer implements RocketMQListener<CreOrdMessage> {
    @Autowired
    OrderService orderService;
    @Override
    public void onMessage(CreOrdMessage message) {
        log.info("create order: "+ message );
        //创建一个未支付的订单
        orderService.save(orderService.nonpaymentOrder(message));
    }
}
