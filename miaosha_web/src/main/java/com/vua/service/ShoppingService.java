package com.vua.service;


import com.vua.entity.CreOrdMessage;
import com.vua.entity.Product;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
    @Autowired
    RedissonClient client;

    @Autowired
    RedisTemplate redisTemplate;
    @Resource(name = "redisTemplate")
    HashOperations hashOperations;

    @Resource(name = "redisTemplate")
    ValueOperations<String, Product> valueOperations;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    public String tryBuy(int uid, int pid) {
        //query product's capacity
        RBucket<Product> bucket = client.getBucket("product::" + pid);

        RLock lock = client.getLock("redis:lock");
        try {
            lock.lock();
            Product product = valueOperations.get("product::" + pid);

            int n = product.getNumber();
            if (n > 0) {
                try {
                    //redisTemplate.multi();
                    valueOperations.set("product::" + pid, product);
                    //redisTemplate.exec();
                } catch (Exception e) {
                    //redisTemplate.discard();
                }
                String content = "user:" + uid + "&product:" + pid;
                CreOrdMessage message=new CreOrdMessage();
                message.setPid(pid);
                message.setUid(uid);

                SendResult result = rocketMQTemplate.syncSend("CREATE_ORDER", message);

                SendStatus status = result.getSendStatus();
                if (status == SendStatus.SEND_OK) {
                    product.setNumber(n - 1);
                    bucket.set(product);

                    return "抢购成功,请支付订单";
                } else {
                    return "抢购失败";
                }
            } else {
                return "秒杀结束";
            }
        } finally {
            lock.unlock();
        }
    }

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
