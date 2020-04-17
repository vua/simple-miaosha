package com.vua.service;

import com.alibaba.fastjson.JSONObject;
import com.vua.entity.Payment;
import com.vua.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import javax.sql.DataSource;
import java.util.Optional;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-04-09 17:54
 */
@Slf4j
@Service
public class PaymentService {

    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    PaymentRepository paymentRepository;

    //自动提交,手动回滚
    @Transactional
    public void insertPayment(Payment payment) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setName("Tx_insertPayment");
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = dataSourceTransactionManager.getTransaction(definition);
        try {
            paymentRepository.save(payment);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("提交失败");
            dataSourceTransactionManager.rollback(status);
        }
    }

    @Transactional
    public boolean findById(int id) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setName("Tx_findPayment");
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = dataSourceTransactionManager.getTransaction(definition);
        Optional<Payment> payment = Optional.ofNullable(null);
        try {
            payment = paymentRepository.findById(id);
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(status);
        }
        return payment.isPresent();
    }

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    public void insertPaymentAndSend2OrderService(Payment payment) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("payment", payment);
        Message message = MessageBuilder.withPayload(jsonObject.toJSONString()).build();

        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("CREATE_PAYMENT_RECORD", "UPDATE_ORDER_STATUS", message, null);
        log.info("send transcation message body={},result={}",message.getPayload(),sendResult.getSendStatus());
    }
}
