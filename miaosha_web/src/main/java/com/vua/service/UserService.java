package com.vua.service;

import com.vua.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "user-service")

public interface UserService {
    @PostMapping("verify_login")
    User verifyLogin(@RequestParam("username") String username, @RequestParam("password") String password);
    @GetMapping("users")
    List<User> users();
}
