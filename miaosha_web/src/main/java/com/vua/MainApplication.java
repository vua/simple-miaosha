package com.vua;

import com.vua.service.AuthService;
import com.vua.service.ProductService;
import com.vua.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-02-29 13:15
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(clients = {UserService.class, AuthService.class, ProductService.class})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
