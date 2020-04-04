package com.vua.controller;

import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.vua.annotation.LoginRequired;
import com.vua.entity.User;
import com.vua.service.AuthService;
import com.vua.service.UserService;
import com.vua.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-27 19:16
 */
@Controller
public class AuthController {
    @Autowired
    AuthService authService;
    private static final String AUTH_COOKIE_NAME = "access_token";

    @GetMapping("/login")
    public String index() {
        return "index";
    }
    @Autowired
    UserService userService;
    @PostMapping("/login")
    @ResponseBody
    public Map login(HttpServletRequest request, HttpServletResponse response,
            @PathParam("username") String username, @PathParam("password") String password) throws IOException {
        System.out.println(username+" "+password);
        User user=userService.verifyLogin(username,password);
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
        Map<String,String> map=new HashMap<>();
        if(user==null) {
            map.put("result","fail");
            return map;
        }
        String return_url = request.getParameter("return_url");
        String token=authService.getToken(user,request.getRemoteAddr());
        CookieUtils.putCookie(response,AUTH_COOKIE_NAME,token);
        //response.sendRedirect(return_url);
        /*pw.println("登录成功");
        pw.flush();
        pw.close();*/
        if(return_url==null){
            return_url="/products";
        }
        return_url=URLDecoder.decode(return_url,"utf-8");
        map.put("result","success");
        map.put("return_url",return_url);
        return map;
    }
    @PostMapping("get_token")
    @ResponseBody
    public String getToken(@RequestBody User user,
                           @RequestParam("ip") String ip
                           ){
        return authService.getToken(user,ip);
    }
    @GetMapping("verify_token")
    @ResponseBody
    public Map<String,Object> verifyToken(@RequestParam("access_token")String token,@RequestParam("ip") String ip){
        return authService.verifyToken(token,ip);
    }
    @GetMapping("/auth_login")
    @ResponseBody
    public JSONObject auth_login(String code,HttpServletRequest request) throws HttpProcessException {
        return authService.getUserInfo(authService.getAccessToken(code));
    }

    @GetMapping("/random_url")
    @ResponseBody
    @LoginRequired(force = true)
    public String test() {
        return "access_token is valid";
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Class clazz=AuthController.class;
        Method method=clazz.getMethod("test");
        System.out.println(method.getAnnotation(LoginRequired.class));
    }
}
