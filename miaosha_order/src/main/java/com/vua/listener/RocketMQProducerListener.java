package com.vua.listener;


import com.alibaba.fastjson.JSONObject;
import com.vua.entity.Order;

import com.vua.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-04-09 16:48
 */
@Component
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "UPDATE_ORDER_STATUS")
public class RocketMQProducerListener implements RocketMQLocalTransactionListener {
    @Autowired
    OrderService orderService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        Order order =null;
        try {
            String jsonString = new String((byte[]) msg.getPayload());

            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            order=JSONObject.parseObject(jsonObject.getString("order"), Order.class);

            order.setStatus(1);
            orderService.save(order);

        } catch (Exception e) {
            e.printStackTrace();
            if(order!=null)
                log.error("update order 执行失败 order id:{}",order.getId());
            else
                log.error("order is null");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String jsonString = new String((byte[]) msg.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        Order order=JSONObject.parseObject(jsonObject.getString("payment"), Order.class);
        int id=order.getId();
        int status=orderService.getOrderStatus(id);
        log.info("回查update order payment id:{} 查询结果:{}",id,status==1?"commit":"unknown");
        return status==1?RocketMQLocalTransactionState.COMMIT:RocketMQLocalTransactionState.UNKNOWN;
    }
}
