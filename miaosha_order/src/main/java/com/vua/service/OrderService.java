package com.vua.service;

import com.alibaba.fastjson.JSONObject;
import com.vua.entity.CreOrdMessage;
import com.vua.entity.Order;
import com.vua.entity.OrderStatus;
import com.vua.entity.Payment;
import com.vua.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-10 11:45
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Transactional(rollbackFor = Exception.class)
    public void save(Order order) {
        orderRepository.save(order);
    }
    @Transactional(rollbackFor = Exception.class)
    public int getOrderStatus(int id){
        Optional<Order> order=orderRepository.findById(id);
        return order.get().getId();
    }
    public Order nonpaymentOrder(CreOrdMessage message){
        Order order=new Order();
        order.setPid(message.getPid());
        order.setUid(message.getUid());
        order.setTradeNo(new Date().toString());
        order.setStatus(OrderStatus.NONPAYMENT);
        return order;
    }

   /* public int[] parseMessage(String message){
        String[] strs=message.split("&");
        int uid=Integer.parseInt(strs[0].split(":")[1]);
        int pid=Integer.parseInt(strs[1].split(":")[1]);
        return  new int[]{uid,pid};
    }

    public Order paymentOrder(String message){
        Order order=new Order();
        String[] strs=message.split("&");
        int uid=Integer.parseInt(strs[0].split(":")[1]);
        int pid=Integer.parseInt(strs[1].split(":")[1]);
        order.setPid(pid);
        order.setUid(uid);

        order.setStatus(OrderStatus.NONPAYMENT);
        return order;
    }*/


    @Autowired
    RocketMQTemplate rocketMQTemplate;
    public void updateOrderStatusAndSend2ProductService(Payment payment){
        Order order=orderRepository.findByTradeNo(payment.getOut_trade_no());
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("order",order);
        Message message=MessageBuilder.withPayload(jsonObject.toJSONString()).build();
        TransactionSendResult sendResult=rocketMQTemplate.sendMessageInTransaction("UPDATE_ORDER_STATUS","UPDATE_MYSQL_CAPACITY",message,null);
        log.info("send transcation message body={},result={}",message.getPayload(),sendResult.getSendStatus());
    }

}
