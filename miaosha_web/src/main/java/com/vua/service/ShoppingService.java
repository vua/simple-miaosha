package com.vua.service;

//import com.alibaba.rocketmq.client.exception.MQBrokerException;
//import com.alibaba.rocketmq.client.exception.MQClientException;
//import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
//import com.alibaba.rocketmq.common.message.Message;
//import com.alibaba.rocketmq.remoting.exception.RemotingException;
//import com.vua.entity.Product;
//import com.vua.entity.User;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.redisson.api.RBucket;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-05 17:10
 */
@Service
public class ShoppingService {
    //    @Autowired
//    RedissonClient client;
//    @Autowired
//    DefaultMQProducer defaultMQProducer;
//    @Autowired
//    RedisTemplate redisTemplate;
    @Resource(name = "redisTemplate")
    HashOperations hashOperations;// = redisTemplate.opsForHash();
//    @Resource(name = "redisTemplate")
//    ValueOperations valueOperations;
//    @Autowired
//    RocketMQTemplate rocketMQTemplate;
    public String tryBuy(int uid, int pid) {
        return "tryBuy";
    }
//    public String tryBuy(int uid, int pid) {
//        //query product's capacity
//        RBucket<Product> bucket = client.getBucket("product::" + pid);
//        RLock lock = client.getLock("redis:lock");
//        try {
//            lock.lock();
//            Product product = bucket.get();
//            int n = product.getNumber();
//            if (n > 0) {
//                String content = "user:" + uid + "&product:" + pid;
//                rocketMQTemplate.convertAndSend("TRYBUY", content);
//                product.setNumber(n - 1);
//                bucket.set(product);
//                return "排队中";
//            } else {
//                return "秒杀结束";
//            }
//        } finally {
//            lock.unlock();
//        }
//    }

    //private static final String salt="annodomini...";
    public String secKillURL(int id) {
        String md5 = DigestUtils.md5DigestAsHex((UUID.randomUUID().toString() + id).getBytes());
        hashOperations.put("miaosha:url", md5, id + "");
        return md5;
    }

    public boolean checkURL(String md5, int id) {

        String o = (String) hashOperations.get("miaosha:url", md5);
        boolean b = o != null && Integer.parseInt(o) == id;
        // 可以使用LUA脚本删除k-v解决同一URL并发请求问题
        if (b) hashOperations.delete("miaosha:url", md5);
        return b;
    }
}
