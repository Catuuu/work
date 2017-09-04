package com.framework.annotation.resolution;

import com.framework.controller.BasicController;
import com.framework.annotation.ResourceButton;
import com.framework.annotation.ResourceMenu;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 对@Resource*注解的解析类
 * User: chenbin
 * Date: 13-3-8
 * Time: 下午2:31
 * To change this template use File | Settings | File Templates.
 */
public class ResourceResolving {
    private static ServletContext _application;

    /**
     * 类扫描
     *
     * @return
     * @throws Exception
     */
    public static void resolving(ApplicationContext context, ServletContext application) throws Exception {
        _application = application;

        //得到系统中所有控制器类
        Map controllers = context.getBeansWithAnnotation(Controller.class);
        for (Object obj : controllers.entrySet()) {
            Map.Entry me = (Map.Entry) obj;
            Class cls = me.getValue().getClass();
            while (!cls.equals(BasicController.class)) {
                Method[] methods = cls.getMethods();
                for (Method method : methods) {
                    methodResolving(cls, method);
                }
                cls = cls.getSuperclass();
            }
        }
    }

    /**
     * 方法扫描
     *
     * @param cls
     * @param med
     * @throws Exception
     */
    private static void methodResolving(Class cls, Method med) throws Exception {
        if (med.isAnnotationPresent(ResourceMenu.class))
            ResourceMenuResolving.methodResolving(_application, cls, med);
        else if (med.isAnnotationPresent(ResourceButton.class)) {
            ResourceButtonResolving.methodResolving(_application, cls, med);
        }
    }
}
