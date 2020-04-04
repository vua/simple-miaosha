package com.vua.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-28 20:35
 */

public class CookieUtils {

    public static String getCookie(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        if(cookies==null) return null;
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return null;
    }
    public static void putCookie(HttpServletResponse response,String cookieName,String cookieValue){
        response.addCookie(new Cookie(cookieName,cookieValue));
    }
}
