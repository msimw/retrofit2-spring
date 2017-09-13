package com.msimw.httpservice.client.annotation;

import java.lang.annotation.*;

/**
 * Created by msimw on 2017/7/12.
 * 用于标识服务接口类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface HttpApi {
    String value() default "";//通过key获得配置文件中的值,如果没找到以value为根路径

    Class[] interceptor() default {};
}
