package com.vua.config;

import com.vua.filter.HiddenHttpMethodFilter;
import com.vua.intercept.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-05 18:25
 */
@Configuration
public class SpringConfig implements WebMvcConfigurer{
    @Autowired
    LoginInterceptor loginInterceptor;
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/","/login","/products","/js/**","/css/**","/image/**","webjars/**");
    }
    @Autowired
    HiddenHttpMethodFilter hiddenHttpMethodFilter;
    @Bean
    public FilterRegistrationBean filterRegist() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(hiddenHttpMethodFilter);
        frBean.addUrlPatterns("/*");
        return frBean;
    }
}
