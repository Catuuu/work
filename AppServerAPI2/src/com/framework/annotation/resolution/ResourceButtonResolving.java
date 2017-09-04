package com.framework.annotation.resolution;

import com.framework.annotation.ResourceButton;
import com.framework.exception.NotAnnotationException;
import com.framework.resource.action.ButtonAction;
import com.framework.resource.interf.ButtonInterface;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.util.*;

import static com.framework.system.SystemConstant.*;

/**
 * 对@ResourceButton注解的解析类
 * User: chenbin
 * Date: 13-3-8
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public class ResourceButtonResolving {
    /**
     * 方法解析
     *
     * @param application
     * @param cls
     * @param method
     * @throws Exception
     */
    public static void methodResolving(ServletContext application, Class cls, Method method) throws Exception {
        ResourceButton rb = method.getAnnotation(ResourceButton.class);
        if (!cls.isAnnotationPresent(RequestMapping.class))
            throw new NotAnnotationException("类\"" + cls.getName() + "\"上，没用配置@RequestMapping 注解 ");
        if (!method.isAnnotationPresent(RequestMapping.class))
            throw new NotAnnotationException("类\"" + cls.getName() + "\"的\"" + method.getName()
                    + "\"方法上，没用配置@RequestMapping 注解 ");
        RequestMapping clsRm = (RequestMapping) cls.getAnnotation(RequestMapping.class);
        RequestMapping methodRm = method.getAnnotation(RequestMapping.class);
        if (methodRm.value().length > 0 && methodRm.value()[0].equals(""))
            throw new NotAnnotationException("类\"" + cls.getName() + "\"的\"" + method.getName()
                    + "\"方法上，@RequestMapping 注解的第一个Value值不能为空！ ");

        //按钮处理
        Map<String, List<ButtonAction>> buttonMap = (Map<String, List<ButtonAction>>) application.getAttribute(SYS_BUTTON_KEY);
        if (null == buttonMap) buttonMap = new HashMap<String, List<ButtonAction>>();
        ButtonAction buttonAction = createResource(clsRm, methodRm, rb);

        Map<String, ButtonInterface> sysButtonMap = (Map<String, ButtonInterface>) application.getAttribute(FRAME_BUTTON_KEY);
        ButtonInterface bi = sysButtonMap.get(rb.button().toString());

        if ("".equalsIgnoreCase(buttonAction.getName()))
            buttonAction.setName(bi.getName());
        buttonAction.setIcon(bi.getIcon());
        buttonAction.setOrder(bi.getOrder());
        buttonAction.setRemark(bi.getRemark());
        buttonAction.setButton(bi);

        String key = buttonAction.getParentid();
        List<ButtonAction> buttonLst = new ArrayList<ButtonAction>();
        if (buttonMap.containsKey(key)) buttonLst = buttonMap.get(key);
        buttonLst.add(buttonAction);

        //集合排序
        Collections.sort(buttonLst, new Comparator() {
            public int compare(Object o1, Object o2) {
                ButtonAction ba1 = (ButtonAction) o1;
                ButtonAction ba2 = (ButtonAction) o2;
                int result = ba1.getOrder() > ba2.getOrder() ? 1 : (ba1.getOrder() == ba2.getOrder() ? 0 : -1);
                if (result == 0) {
                    result = ba1.getName().compareTo(ba2.getName());
                }
                return result;
            }
        });

        buttonMap.put(key, buttonLst);

        application.setAttribute(SYS_BUTTON_KEY, buttonMap);
    }

    /**
     * 实例化资源
     *
     * @param clsRm
     * @param methodRm
     * @param rb
     * @return
     */
    private static ButtonAction createResource(RequestMapping clsRm, RequestMapping methodRm, ResourceButton rb) {
        ButtonAction ba = new ButtonAction();
        String id = clsRm.value()[0] + "-" + methodRm.value()[0];
        String url = "/" + clsRm.value()[0] + "/" + methodRm.value()[0];
        String parentid = clsRm.value()[0] + "-" + rb.menuRequestMapping();
        String name = rb.name();

        ba.setId(id);
        ba.setName(name);
        ba.setParentid(parentid);
        ba.setUrl(url);
        ba.setButtonid(methodRm.value()[0]);

        return ba;
    }
}
