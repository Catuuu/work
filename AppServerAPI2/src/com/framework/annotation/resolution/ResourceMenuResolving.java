package com.framework.annotation.resolution;

import com.framework.annotation.ResourceMenu;
import com.framework.exception.NotAnnotationException;
import com.framework.resource.action.MenuAction;
import com.framework.resource.interf.RuleInterface;
import dataRule.Rule;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.framework.system.SystemConstant.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-8
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class ResourceMenuResolving {
    /**
     * 方法解析
     *
     * @param application
     * @param cls
     * @param method
     * @throws Exception
     */
    public static void methodResolving(ServletContext application, Class cls, Method method) throws Exception {
        ResourceMenu rm = method.getAnnotation(ResourceMenu.class);
        if (!cls.isAnnotationPresent(RequestMapping.class))
            throw new NotAnnotationException("类\"" + cls.getName() + "\"上，没用配置@RequestMapping 注解！");
        if (!method.isAnnotationPresent(RequestMapping.class))
            throw new NotAnnotationException("类\"" + cls.getName() + "\"的\"" + method.getName()
                    + "\"方法上，没用配置@RequestMapping 注解！");
        RequestMapping clsRm = (RequestMapping) cls.getAnnotation(RequestMapping.class);
        RequestMapping methodRm = method.getAnnotation(RequestMapping.class);
        if (methodRm.value().length > 0 && methodRm.value()[0].equals(""))
            throw new NotAnnotationException("类\"" + cls.getName() + "\"的\"" + method.getName()
                    + "\"方法上，@RequestMapping 注解的第一个Value值不能为空！");

        //菜单处理
        Map<String, MenuAction> menuMap = (Map<String, MenuAction>) application.getAttribute(SYS_MENU_KEY);
        if (null == menuMap) menuMap = new HashMap<String, MenuAction>();
        MenuAction menuAction = createResource(clsRm, methodRm, rm);
        menuMap.put(menuAction.getId(), menuAction);

        application.setAttribute(SYS_MENU_KEY, menuMap);
        //规则处理
        Map<String, RuleInterface> frameRules = (Map<String, RuleInterface>) application.getAttribute(FRAME_RULE_KEY);
        Map<String, List<RuleInterface>> ruleMap = (Map<String, List<RuleInterface>>) application.getAttribute(SYS_RULE_KEY);
        if (null == ruleMap) ruleMap = new HashMap<String, List<RuleInterface>>();
        List<RuleInterface> ruleInterfaceList = getRuleList(rm.rule(), frameRules);
        if (!ruleInterfaceList.isEmpty()) {
            ruleMap.put(menuAction.getId(), ruleInterfaceList);
            application.setAttribute(SYS_RULE_KEY, ruleMap);
        }
    }

    /**
     * 实例化资源
     *
     * @param clsRm
     * @param methodRm
     * @param rm
     * @return
     */
    private static MenuAction createResource(RequestMapping clsRm, RequestMapping methodRm, ResourceMenu rm) {
        MenuAction ma = new MenuAction();

        String id = clsRm.value()[0] + "-" + methodRm.value()[0];
        String url = "/" + clsRm.value()[0] + "/" + methodRm.value()[0];

        ma.setId(id);
        ma.setName(rm.name());
        ma.setUrl(url);
        ma.setIcon(rm.iconCls());
        ma.setRemark(rm.describe());

        return ma;
    }

    /**
     * 获取菜单对应规则
     *
     * @param rules
     * @return
     */
    private static List<RuleInterface> getRuleList(Rule[] rules, Map<String, RuleInterface> frameRules) {
        List<RuleInterface> list = new ArrayList<RuleInterface>();
        for (Rule rule : rules) {
            RuleInterface dataRule = frameRules.get(rule.toString());
            if (null != dataRule) list.add(dataRule);
        }
        return list;
    }
}
