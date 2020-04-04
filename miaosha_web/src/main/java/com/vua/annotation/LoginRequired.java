package com.vua.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
public @interface LoginRequired {
    /*
     * force:
     *   true 必须登陆后访问
     *   false 只做登陆验证
     */
    boolean force() default true;
}
