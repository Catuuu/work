package com.framework.annotation;

import java.lang.annotation.*;

/**
 * 方法资源注解.
 * User: chenbin
 * Date: 13-3-8
 * Time: 上午10:30
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ResourceMethod {
    String name() default "";

    String describe() default "";

    CheckType check() default CheckType.CHECK_LOGIN;
}
