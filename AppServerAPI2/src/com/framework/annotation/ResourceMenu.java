package com.framework.annotation;

import dataRule.Rule;

import java.lang.annotation.*;

/**
 * 菜单资源注解
 * User: chenbin
 * Date: 13-3-8
 * Time: 上午10:31
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ResourceMenu {
    String name() default "";

    String iconCls() default "";

    String describe() default "";

    Rule[] rule() default Rule.NULL;

    CheckType check() default CheckType.CHECK_LOGIN_POWERS;
}
