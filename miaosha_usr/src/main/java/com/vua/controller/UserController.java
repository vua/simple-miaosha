package com.vua.controller;

import com.vua.entity.User;
import com.vua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-04-03 19:46
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("verify_login")
    public User verifyLogin(@PathParam("username") String username, @PathParam("password") String password){
        return userService.verifyLogin(username,password);
    }
    @GetMapping("users")
    public List<User> users(){
        return userService.findAll();
    }
}
