package com.vua.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.params.SetParams;

import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-02 15:13
 */

@Component
public class RedisLock implements Lock {

    private static final String key = "redis:lock";
    private static ThreadLocal<String> val = new ThreadLocal<>();
    private String value;
    private long time = 3600 * 5 * 1_000;

//    @Autowired
//    JedisPool jedisPool;

    //    ReentrantLock
    private static final String script =
            "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                    "   return redis.call('del',KEYS[1]) " +
                    "else" +
                    "   return 0 " +
                    "end";

    @Override
    public void lock() {
        /*Jedis resource = jedisPool.getResource();
        //long enter = System.currentTimeMillis();
        for(;;){
            value = UUID.randomUUID().toString();
            val.set(value);
            String response = resource.set(key, value, SetParams.setParams().nx().px(time));

            if ("OK".equals(response)) {
                return;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        //System.out.println(jedisPool);


        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {

        return false;
    }

    @Override
    public void unlock() {
//        Jedis resource = jedisPool.getResource();
//        resource.eval(script, Arrays.asList(key), Arrays.asList(val.get()));
//        resource.close();

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
