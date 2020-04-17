package com.vua.controller;

import com.vua.entity.User;
import com.vua.service.OrderService;
import com.vua.service.ProductService;
import com.vua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-04-16 19:30
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @GetMapping("")
    public ModelAndView admin(ModelAndView modelAndView){
        modelAndView.setViewName("admin");
        modelAndView.getModelMap().put("users",userService.users());
        modelAndView.getModelMap().put("products",productService.products());

        return modelAndView;
    }
    @ResponseBody
    @PostMapping("/user")
    public void updateUser(@RequestParam("id")int id
            ,@RequestParam("name")String name,
                           @RequestParam("password")String password,
                           @RequestParam("email")String email){
        System.out.println(id);
        System.out.println(name);
        System.out.println(password);
        System.out.println(email);
    }
}
