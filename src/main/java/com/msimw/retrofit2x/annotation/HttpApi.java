package com.msimw.retrofit2x.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

/**
 * Created by msimw on 2017/7/12.
 * 用于标识服务接口类
 */
@Autowired
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface HttpApi {
    String value() default "";//通过key获得配置文件中的值,如果没找到以value为根路径

    Class[] interceptor() default {};
}