package com.framework.annotation;

import java.lang.annotation.*;

/**
 * 系统表字段注解.
 * User: chenbin
 * Date: 13-3-8
 * Time: 上午10:17
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface RsTableField {
    /**
     * 字段名称
     *
     * @return
     */
    String name() default "";

    /**
     * 表主键
     *
     * @return
     */
    boolean primaryKey() default false;

    /**
     * 简要描述
     *
     * @return
     */
    String describe() default "";
}
