package com.vua.config;

//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.web.util.matcher.AnyRequestMatcher;


/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-03-24 12:45
 */
public class AuthorizeConfig{}
/*
@Configuration
@EnableWebSecurity
@Slf4j
public class AuthorizeConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    UserService userService;

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //代替登录拦截器UserInterceptor
        http.authorizeRequests().antMatchers("/products/**").authenticated()
                .anyRequest().permitAll().
                and().formLogin().defaultSuccessUrl("/products").permitAll();

    }
}
*/
