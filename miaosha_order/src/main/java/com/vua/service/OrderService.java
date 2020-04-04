package com.vua.service;

import com.vua.entity.Order;
import com.vua.entity.OrderStatus;
import com.vua.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-10 11:45
 */
@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    public void save(Order order) {
        orderRepository.save(order);
    }
    public int[] parseMessage(String message){
        String[] strs=message.split("&");
        int uid=Integer.parseInt(strs[0].split(":")[1]);
        int pid=Integer.parseInt(strs[1].split(":")[1]);
        return  new int[]{uid,pid};
    }
    public Order nonpaymentOrder(String message){
        Order order=new Order();
        int[] ids=parseMessage(message);
        order.setPid(ids[1]);
        order.setUid(ids[2]);
        order.setCreate_time(new Date());
        order.setStatus(OrderStatus.NONPAYMENT.getVal());
        return order;
    }

    public Order paymentOrder(String message){
        Order order=new Order();
        String[] strs=message.split("&");
        int uid=Integer.parseInt(strs[0].split(":")[1]);
        int pid=Integer.parseInt(strs[1].split(":")[1]);
        order.setPid(pid);
        order.setUid(uid);

        order.setStatus(OrderStatus.NONPAYMENT.getVal());
        return order;
    }
}
