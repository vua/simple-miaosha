package com.vua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-02-29 13:15
 */
@SpringBootApplication
@EnableEurekaClient
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
