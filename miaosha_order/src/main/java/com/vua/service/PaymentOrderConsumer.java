package com.vua.service;

import com.alibaba.fastjson.JSONObject;
import com.vua.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
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
@RocketMQMessageListener(topic = "UPDATE_ORDER_STATUS", consumerGroup = "update_order_status")
public class PaymentOrderConsumer implements RocketMQListener<String> {
    @Autowired
    OrderService orderService;
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Override
    //@Transactional()
    public void onMessage(String message) {
        log.info("update order status: " + message);
        //更新订单状态

        JSONObject jsonObject = JSONObject.parseObject(message);
        Payment payment = JSONObject.parseObject(jsonObject.getString("payment"), Payment.class);
//        log.info(payment.toString());
        orderService.updateOrderStatusAndSend2ProductService(payment);
        //发送消息到库存服务更新库存
        //rocketMQTemplate.syncSendOrderly("CAPACITY_UPDATE");
    }
}
