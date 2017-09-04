package com.framework.annotation;

import java.lang.annotation.*;

/**
 * 系统对象注解.
 * User: Administrator
 * Date: 13-3-8
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RsBean {
    /**
     * 简要描述
     *
     * @return
     */
    String describe() default "";
}
