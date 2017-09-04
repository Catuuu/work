package com.framework.annotation;

import java.lang.annotation.*;

/**
 * 应用资源注解
 * User: chenbin
 * Date: 13-3-8
 * Time: 上午10:36
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ResourceNeed {
    String requestMapping() default "";

    String describe() default "";

    CheckType check() default CheckType.CHECK_LOGIN_POWERS;
}
