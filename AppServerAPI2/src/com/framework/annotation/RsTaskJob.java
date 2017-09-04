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
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RsTaskJob {
    /**
     * 任务名称
     *
     * @return
     */
    String name();

    /**
     * 服务被中止后，再次启动任务时会尝试恢复执行之前未完成的所有任务
     *
     * @return
     */
    boolean shouldRecover() default false;

    /**
     * 任务描述
     *
     * @return
     */
    String describe() default "";
}
