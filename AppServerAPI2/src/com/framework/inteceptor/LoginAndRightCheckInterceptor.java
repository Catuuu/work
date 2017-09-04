package com.framework.inteceptor;

import com.framework.annotation.*;
import com.framework.exception.NotAccessException;
import com.framework.exception.NotLoginException;
import com.framework.resource.DataResource;
import com.framework.resource.action.ButtonAction;
import com.framework.resource.interf.RuleInterface;
import com.framework.resource.rule.RuleItem;
import com.framework.resource.rule.RuleType;
import com.framework.system.SystemConfig;
import com.framework.util.BeanUtil;
import com.framework.util.WebUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.framework.annotation.CheckType.*;
import static com.framework.system.SystemConstant.*;

/**
 * 登录及权限验证拦截器
 * User: chenbin
 * Date: 13-1-6
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 */
public class LoginAndRightCheckInterceptor extends HandlerInterceptorAdapter {

    protected final Log logger = LogFactory.getLog(getClass());
    private HandlerMethod handlerMethod;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        this.handlerMethod = ((HandlerMethod) handler);

        ResourceMethod methodAnnotation = handlerMethod.getMethodAnnotation(ResourceMethod.class);
        if (null != methodAnnotation) {
            if (CHECK_MUITUAN.equals(methodAnnotation.check())) {
                Map params =  BeanUtil.createBean(request,HashMap.class);
                if(params.size()==0){
                    PrintWriter out = null; // 输出对象
                    try {
                        response.reset();
                        response.setContentType("application/json; charset=UTF-8");
                        out = response.getWriter();
                        out.write("{\"data\": \"ok\"}");
                        out.flush();
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                    return false;
                }

                boolean bln = com.opensdk.meituan.util.SignGenerator.getSigPush(params, SystemConfig.GetSystemParamMeituan().getAppSecret(),WebUtil.getRequest().getRequestURI());
                if(!bln){
                    throw new NotLoginException("美团推送验证失败");
                }else{
                    return super.preHandle(request, response, handler);
                }
            }else if(CHECK_UDESK.equals(methodAnnotation.check())){
                Map params =  BeanUtil.createBean(request,HashMap.class);
                if(params.size()==0){
                    PrintWriter out = null; // 输出对象
                    try {
                        response.reset();
                        response.setContentType("application/json; charset=UTF-8");
                        out = response.getWriter();
                        out.write("{\"data\": \"ok\"}");
                        out.flush();
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                    return false;
                }
                boolean bln = com.opensdk.udesk.util.SignGenerator.getSigPush(params,"de73f1d954a6ea58f9fad220086b1ee4");
                if(!bln){
                    throw new NotLoginException("udesk验证失败");
                }else{
                    return super.preHandle(request, response, handler);
                }
            }else if(CHECK_CHUFAN.equals(methodAnnotation.check())){//厨房签名验证
                Map params =  BeanUtil.createBean(request,HashMap.class);
                if(params.size()==0){
                    PrintWriter out = null; // 输出对象
                    try {
                        response.reset();
                        response.setContentType("application/json; charset=UTF-8");
                        out = response.getWriter();
                        out.write("{\"data\": \"签名验证失败\"}");
                        out.flush();
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                    return false;
                }

                boolean bln = com.opensdk.chufan.util.SignGenerator.isSign(params);
                if(!bln){
                    PrintWriter out = null; // 输出对象
                    try {
                        response.reset();
                        response.setContentType("application/json; charset=UTF-8");
                        out = response.getWriter();
                        out.write("{\"data\": \"签名验证失败\"}");
                        out.flush();
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                }else{
                    return super.preHandle(request, response, handler);
                }
            }
        }



            if (!this.resourceMethodCheck())
                if (!this.resourceMenuCheck())
                    if (!this.resourceButtonCheck())
                        if (!this.resourceNeedCheck())
                            throw new NotAccessException("您的请求为非法路径,请重新确认");
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        this.handlerMethod = ((HandlerMethod) handler);
        if (null != modelAndView)
            if (!this.resourceMenuPageParam(modelAndView))
                this.resourceButtonPageParam(modelAndView);
    }


    /**
     * MethodAnnotation注释监测
     *
     * @return
     * @throws Exception
     */
    private boolean meiTuanMethodCheck() throws Exception {
        boolean bln = false;
        ResourceMethod methodAnnotation = handlerMethod.getMethodAnnotation(ResourceMethod.class);
        if (null != methodAnnotation) {
            if (CHECK_MUITUAN.equals(methodAnnotation.check())) {
                bln = true;
                Map params =  BeanUtil.createBean(WebUtil.getRequest(),HashMap.class);
                if(params.size()==0){
                    HttpServletResponse response = WebUtil.getResponse();
                    PrintWriter out = null; // 输出对象
                    try {
                        response.reset();
                        response.setContentType("application/json; charset=UTF-8");
                        out = response.getWriter();
                        out.write("{\"data\": \"ok\"}");
                        out.flush();
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                    return bln;
                }

                bln = com.opensdk.meituan.util.SignGenerator.getSigPush(params,SystemConfig.GetSystemParamMeituan().getAppSecret(),WebUtil.getRequest().getRequestURI());
                if(!bln){
                    throw new NotLoginException("美团推送验证失败");
                }
            }
        }
        return bln;
    }

    /**
     * MethodAnnotation注释监测
     *
     * @return
     * @throws Exception
     */
    private boolean resourceMethodCheck() throws Exception {
        boolean bln = false;
        ResourceMethod methodAnnotation = handlerMethod.getMethodAnnotation(ResourceMethod.class);
        if (null != methodAnnotation) {
            bln = true;
            checkLogin(methodAnnotation.check(), LOGIN_USER_KEY);
        }
        return bln;
    }

    /**
     * MenuResource注释监测
     *
     * @return
     * @throws Exception
     */
    private boolean resourceMenuCheck() throws Exception {
        boolean bln = false;
        ResourceMenu menuResource = handlerMethod.getMethodAnnotation(ResourceMenu.class);
        if (null != menuResource) {
            bln = true;
            checkLogin(menuResource.check(), LOGIN_USER_KEY);
            RequestMapping methodRequestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
            checkPowers(menuResource.check(), methodRequestMapping.value()[0]);
        }
        return bln;
    }

    /**
     * ButtonResource注释监测
     *
     * @return
     * @throws Exception
     */
    private boolean resourceButtonCheck() throws Exception {
        boolean bln = false;
        ResourceButton buttonResource = handlerMethod.getMethodAnnotation(ResourceButton.class);
        if (null != buttonResource) {
            bln = true;
            checkLogin(buttonResource.check(), LOGIN_USER_KEY);
            RequestMapping methodRequestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
            checkPowers(buttonResource.check(), methodRequestMapping.value()[0]);
        }
        return bln;
    }

    /**
     * NeedResource注释监测
     *
     * @return
     * @throws Exception
     */
    private boolean resourceNeedCheck() throws Exception {
        boolean bln = false;
        ResourceNeed needResource = handlerMethod.getMethodAnnotation(ResourceNeed.class);
        if (null != needResource) {
            bln = true;
            checkLogin(needResource.check(), LOGIN_USER_KEY);
            checkPowers(needResource.check(), needResource.requestMapping());
        }
        return bln;
    }

    /**
     * 登录检测
     *
     * @param check
     * @param key
     * @throws Exception
     */
    private void checkLogin(CheckType check, String key) throws Exception {
        if (!(NO_CHECK).equals(check)) {
            if (null == WebUtil.getSession(key))
                throw new NotLoginException("您未登录系统或者登录已超时,请重新登录");
        }
    }

    /**
     * 权限检测
     *
     * @param check
     * @param postAction
     * @throws Exception
     */
    private void checkPowers(CheckType check, String postAction) throws Exception {
        //是否需要权限判断
        if ((CHECK_LOGIN_POWERS).equals(check)) {
            RequestMapping clsRequestMapping = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
            String action = clsRequestMapping.value()[0] + "-" + postAction;
            //用户拥有功能集
            Map<String, String> sroList = WebUtil.getSession(USER_OPERATIONS_KEY);
            if (null != sroList.get(action)) {
                return;
            }
            throw new NotAccessException("您无权访问此资源,请获取授权或联系管理员");
        }
    }

    /**
     * MenuResource注解 页面参数输出
     *
     * @param modelAndView
     * @return
     * @throws Exception
     */
    private boolean resourceMenuPageParam(ModelAndView modelAndView) throws Exception {
        boolean bln = false;
        ResourceMenu menuResource = handlerMethod.getMethodAnnotation(ResourceMenu.class);
        if (null != menuResource) {
            ModelMap modelMap = modelAndView.getModelMap();
            bln = true;
            RequestMapping clsRequestMapping = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
            RequestMapping methodRequestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
            String menu_id = clsRequestMapping.value()[0] + "-" + methodRequestMapping.value()[0];

            modelMap.put("_pageButtons", this.getUserButton(menu_id));

            Map<String, List<RuleInterface>> ruleMap = WebUtil.getApplication(SYS_RULE_KEY);

            if (null == ruleMap) return bln;

            //获取规则数组
            List<RuleInterface> ruleList = ruleMap.get(menu_id);
            if (null != ruleList) {
                modelMap.putAll(this.getPageControls(menu_id, ruleList));
            }
        }
        return bln;
    }

    /**
     * ButtonResource注解 页面参数输出
     *
     * @param modelAndView
     * @return
     * @throws Exception
     */
    private boolean resourceButtonPageParam(ModelAndView modelAndView) throws Exception {
        boolean bln = false;
        ResourceButton buttonResource = handlerMethod.getMethodAnnotation(ResourceButton.class);
        if (null != buttonResource) {
            ModelMap modelMap = modelAndView.getModelMap();
            bln = true;
            RequestMapping clsRequestMapping = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
            String menu_id = clsRequestMapping.value()[0] + "-" + buttonResource.menuRequestMapping();

            /*List<ButtonAction> buttonActions = this.getUserButton(menu_id);
            modelMap.put("_pageButtons", buttonActions);*/

            Map<String, List<RuleInterface>> ruleMap = WebUtil.getApplication(SYS_RULE_KEY);

            if (null == ruleMap) return bln;

            //获取规则数组
            List<RuleInterface> ruleList = ruleMap.get(menu_id);
            if (null != ruleList) {
                modelMap.putAll(this.getPageControls(menu_id, ruleList));
            }
        }
        return bln;
    }

    /**
     * 获取登录用户对应此菜单的功能按钮
     *
     * @param menu_id
     * @return
     */
    private List<ButtonAction> getUserButton(String menu_id) {
        //用户对应功能
        Map<String, List<ButtonAction>> buttonMap = WebUtil.getSession(USER_BUTTON_KEY);
        return buttonMap.get(menu_id);
    }

    /**
     * 获取登录用户页面控制参数
     *
     * @param menu_id
     * @param ruleList
     * @return
     */
    private Map getPageControls(String menu_id, List<RuleInterface> ruleList) {
        DataResource dataResource = new DataResource();
        //获取用户规则
        Map ruleMaps = WebUtil.getSession(USER_RULE_KEY);
        Map<String, List<RuleItem>> ruleMap = (Map<String, List<RuleItem>>) ruleMaps.get(menu_id);
        if (null == ruleMap) return new HashMap();

        for (RuleInterface ri : ruleList) {
            List<RuleItem> ruleItems = ruleMap.get(ri.getKey());
            if (null != ruleItems && RuleType.pageControls.equals(ri.getType())) {
                dataResource.putAll(ri.doProcessing(ruleItems));
            }
        }
        return dataResource.getAll();
    }
}
