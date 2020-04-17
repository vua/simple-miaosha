package com.vua.listener;


import com.alibaba.fastjson.JSONObject;
import com.vua.entity.Payment;
import com.vua.service.PaymentService;
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
@RocketMQTransactionListener(txProducerGroup = "CREATE_PAYMENT_RECORD")
public class RocketMQProducerListener implements RocketMQLocalTransactionListener {
    @Autowired
    PaymentService paymentService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        Payment payment =null;
        try {
            String jsonString = new String((byte[]) msg.getPayload());

            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            payment=JSONObject.parseObject(jsonObject.getString("payment"), Payment.class);
            paymentService.insertPayment(payment);

        } catch (Exception e) {
            if(payment!=null)
                log.error("insert payment 执行失败 payment id:{}",payment.getId());
            else
                log.error("payment is null");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String jsonString = new String((byte[]) msg.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        Payment payment = JSONObject.parseObject(jsonObject.getString("payment"), Payment.class);
        int id=payment.getId();
        boolean b=paymentService.findById(id);
        log.info("回查insert payment payment id:{} 查询结果:{}",id,b?"commit":"unknown");
        return b?RocketMQLocalTransactionState.COMMIT:RocketMQLocalTransactionState.UNKNOWN;
    }
}
