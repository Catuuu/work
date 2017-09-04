package com.framework.annotation;

import java.lang.annotation.*;

/**
 * 系统对象属性注解
 * User: Administrator
 * Date: 13-3-8
 * Time: 上午10:54
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface RsBeanField {
    /**
     * 简要描述
     *
     * @return
     */
    String describe() default "";
}
