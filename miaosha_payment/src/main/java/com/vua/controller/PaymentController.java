package com.vua.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import com.alipay.api.request.AlipayAcquireCreateandpayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.vua.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-04-01 13:19
 */
@Controller
public class PaymentController {
    @Autowired
    AlipayClient alipayClient;
    @GetMapping("/")
    public String test(){
        return "test";
    }
    @PostMapping("/alipay")
    @ResponseBody
    public String alipay(String outTradeNumber, BigDecimal totalAmount, HttpServletRequest request, ModelMap modelMap){
        AlipayTradePagePayRequest alipayRequest=new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_payment_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_payment_url);
        AlipayResponse form= null;
        Map<String, Object> map = new HashMap<>();
        map.put("out_trade_no", System.currentTimeMillis()+"");
        map.put("product_code", "FAST_INSTANT_TRADE_PAY");
        map.put("total_amount", totalAmount);
        map.put("subject", "秒杀测试商品");
        String param = JSON.toJSONString(map);
        alipayRequest.setBizContent(param);
        try {
            form = alipayClient.pageExecute(alipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return form.getBody();
    }
    @GetMapping("/alipay/callback/return")
    @ResponseBody
    public String paymentReturn(){
        return "支付成功";
    }

}
