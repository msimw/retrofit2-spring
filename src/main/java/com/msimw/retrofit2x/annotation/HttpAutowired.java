package com.msimw.retrofit2x.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

/**
 * Created by msimw on 2017/7/12.
 * 用于自动注入服务
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
@Autowired
public @interface HttpAutowired {

}
