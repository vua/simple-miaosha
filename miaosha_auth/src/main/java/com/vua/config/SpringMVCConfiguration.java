package com.vua.config;

import com.vua.intercept.LoginIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-28 18:59
 */
@Configuration
public class SpringMVCConfiguration implements WebMvcConfigurer {
    @Autowired
    LoginIntercept loginIntercept;
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginIntercept).addPathPatterns("/**")
                .excludePathPatterns("/js/**","/image/**","/css/**")
                .excludePathPatterns("/auth/**","/index/**");
    }
    //public void
}
