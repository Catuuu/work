package com.framework.annotation.resolution;

import com.framework.annotation.ResourceButton;
import com.framework.annotation.ResourceMenu;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-8
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class ResourceNeedResolving {
    /**
     * 查找对应菜单@ResourceMenu注解
     *
     * @param target
     * @param rm
     * @throws Exception
     */
    public static ResourceMenu needMenu(Object target, String rm) throws Exception {
        ResourceMenu menuResource = null;
        Method[] methods = target.getClass().getDeclaredMethods();
        for (Method method : methods) {
            RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
            String requestMapping = methodRequestMapping.value()[0];
            //如果找到对应的reuestMapping
            if (rm.equalsIgnoreCase(requestMapping)) {
                if (method.isAnnotationPresent(ResourceMenu.class))
                    menuResource = method.getAnnotation(ResourceMenu.class);
                else
                    menuResource = needMenu(target, requestMapping);  //继续递归查找
                break;
            }
        }
        return menuResource;
    }

    /**
     * 查找对应菜单@ResourceMenu注解
     *
     * @param target
     * @param rm
     * @throws Exception
     */
    public static ResourceMenu needMenu(Class target, String rm) throws Exception {
        ResourceMenu menuResource = null;
        Method[] methods = target.getDeclaredMethods();
        for (Method method : methods) {
            RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
            String requestMapping = methodRequestMapping.value()[0];
            //如果找到对应的reuestMapping
            if (rm.equalsIgnoreCase(requestMapping)) {
                if (method.isAnnotationPresent(ResourceMenu.class))
                    menuResource = method.getAnnotation(ResourceMenu.class);
                else
                    menuResource = needMenu(target, requestMapping);  //继续递归查找
                break;
            }
        }
        return menuResource;
    }

    /**
     * 查找对应菜单@ResourceMenu注解
     *
     * @param target
     * @param rm
     * @throws Exception
     */
    public static String needMenuId(Object target, String rm) throws Exception {
        String menu_id = "";
        Method[] methods = target.getClass().getDeclaredMethods();
        for (Method method : methods) {
            RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
            String requestMapping = methodRequestMapping.value()[0];
            //如果找到对应的reuestMapping
            if (rm.equalsIgnoreCase(requestMapping)) {
                RequestMapping clsRequestMapping = target.getClass().getAnnotation(RequestMapping.class);
                if (method.isAnnotationPresent(ResourceMenu.class))
                    menu_id = clsRequestMapping.value()[0] + "-" + rm;
                else if (method.isAnnotationPresent(ResourceButton.class))
                    menu_id = clsRequestMapping.value()[0] + "-" + method.getAnnotation(ResourceButton.class).menuRequestMapping();
                break;
            }
        }
        return menu_id;
    }
}
