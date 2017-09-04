package com.framework.annotation;

import java.lang.annotation.*;

/**
 * 树结构属性注解
 * <p/>
 * User: chenbin
 * Date: 12-12-25
 * Time: 下午5:38
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RsTreeNode {
    /**
     * 描述节点标记名称
     *
     * @return
     */
    RsTreeNodeTag tagName();

    /**
     * 节点默认值
     *
     * @return
     */
    String defaultValue() default "";
}
