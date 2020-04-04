package com.vua.intercept;


import com.vua.annotation.LoginRequired;
import com.vua.service.AuthService;
import com.vua.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-28 18:54
 */
@Component
@Slf4j
public class LoginIntercept implements HandlerInterceptor {
    @Autowired
    AuthService authService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof ResourceHttpRequestHandler) return true;
        HandlerMethod hm = (HandlerMethod) handler;

        LoginRequired l = hm.getMethodAnnotation(LoginRequired.class);

        if (l != null) {
            String token = null;
            boolean login_status = false;
            String old_token = CookieUtils.getCookie(request, "access_token");
            if (old_token != null) token = old_token;
            String new_token = request.getParameter("token");
            if (new_token != null) token = new_token;
            if (token != null) {
                //验证
                log.info("验证token");
                if (authService.verifyToken(token, request.getRemoteAddr())!=null) login_status = true;
            }
            // token更新
            if (new_token != null && login_status) CookieUtils.putCookie(response, "access_token", new_token);
            // 没有携带token或者token校验失败
            if (l.force() && !login_status) {
                String return_url = request.getRequestURI();
                log.info("登陆重定向");
                response.sendRedirect("login?return_url=" + URLEncoder.encode(return_url, "utf-8"));
                return false;
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("/random_url", "utf-8"));
    }
}
