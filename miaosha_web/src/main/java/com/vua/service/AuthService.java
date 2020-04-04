package com.vua.service;

import com.vua.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-04-03 19:32
 */
@FeignClient(name = "auth-service")
public interface AuthService {
    @PostMapping("/get_token")
    String getToken(@RequestBody User user,
                           @RequestParam("ip") String ip
    );
    @GetMapping("/verify_token")
    Map<String,Object> verifyToken(@RequestParam("access_token") String token,
                                   @RequestParam("ip") String ip);
}
