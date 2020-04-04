package com.vua.controller;

import com.vua.annotation.LoginRequired;
import com.vua.entity.Product;
import com.vua.entity.User;
import com.vua.service.AuthService;
import com.vua.service.OrderService;
import com.vua.service.ProductService;
import com.vua.service.UserService;
import com.vua.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-02-29 13:55
 */
@Controller
@Slf4j
public class WebController {
    static final String AUTH_COOKIE_NAME = "access_token";

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String index() {
        return "index";
    }

    @Autowired
    AuthService authService;


    @PostMapping("/login")
    @ResponseBody
    public Map login(HttpServletRequest request, HttpServletResponse response,
                     @PathParam("username") String username, @PathParam("password") String password) throws IOException {
        System.out.println(username + " " + password);
        User user = userService.verifyLogin(username, password);
        System.out.println(user);
        //response.setCharacterEncoding("utf-8");
        //response.setContentType("text/html;charset=utf-8");
        response.setContentType("text/plain;charset=utf-8");
        /*PrintWriter pw=response.getWriter();
        if(user==null){
            //Ajax返回
            pw.println("用户名或密码错误");
            pw.flush();
            pw.close();
            return ;
        }*/
        Map<String, String> map = new HashMap<>();
        if (user == null) {
            map.put("result", "fail");
            return map;
        }
        String return_url = request.getParameter("return_url");
        String token = authService.getToken(user, request.getRemoteAddr());
        CookieUtils.putCookie(response, AUTH_COOKIE_NAME, token);
        //response.sendRedirect(return_url);
        /*pw.println("登录成功");
        pw.flush();
        pw.close();*/
        if (return_url == null) {
            return_url = "/products";
        }
        return_url = URLDecoder.decode(return_url, "utf-8");
        map.put("result", "success");
        map.put("return_url", return_url);
        return map;
    }

    @Autowired
    ProductService productService;


    @LoginRequired
    @GetMapping("myorder")
    public String myorder(HttpServletRequest request, HttpServletResponse response, ModelMap map) {

        int uid = (int) request.getSession().getAttribute("uid");
        log.info("uid:" + uid);
        map.put("orders", "");
        return "order";
    }
}
