package com.framework.annotation;

import java.lang.annotation.*;

/**
 * 系统表注解.
 * User: chenbin
 * Date: 13-3-8
 * Time: 上午10:17
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RsTable {
    /**
     * 系统表名
     *
     * @return
     */
    String name() default "";

    /**
     * 简要描述
     *
     * @return
     */
    String describe() default "";
}
