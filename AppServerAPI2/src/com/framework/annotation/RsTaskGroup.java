package com.framework.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 任务调度注解
 * User: chenbin
 * Date: 13-4-12
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RsTaskGroup {
    /**
     * 任务组名称
     *
     * @return
     */
    String name() default "DEFAULT";

    /**
     * 任务组描述
     *
     * @return
     */
    String describe() default "";
}
