package com.vua.service;

import com.alibaba.fastjson.JSONObject;
import com.vua.entity.Order;
import lombok.extern.slf4j.Slf4j;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.json.JSONString;
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
@RocketMQMessageListener(topic = "UPDATE_MYSQL_CAPACITY", consumerGroup = "update_mysql_capacity")
public class UpdateCapacityConsumer implements RocketMQListener<String> {
    @Autowired
    ProductService productService;
    @Override
    public void onMessage(String message) {
        log.info("update product capacity: "+message );
        //创建一个未支付的订单
        JSONObject jsonObject=JSONObject.parseObject(message);
        Order order=JSONObject.parseObject(jsonObject.getString("order"),Order.class);
        productService.updateCapacity(order);
    }
}
