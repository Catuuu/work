package com.framework.util;

import com.framework.dao.SqlDao;
import com.framework.mapping.system.CdsUsers;
import com.opensdk.dianwoda.vo.SystemParamDianWoDa;
import com.opensdk.eleme.vo.SystemParamEleme;
import com.opensdk.meituan.vo.SystemParamMeituan;
import com.opensdk.shenhou.vo.SystemParamShenhou;
import com.opensdk.weixin.vo.SystemParamWeixin;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.framework.system.SystemConstant.LOGIN_USER_KEY;

/**
 * Created with IntelliJ IDEA.
 * User: chenbin
 * Date: 13-1-8
 * Time: 下午4:29
 * To change this template use File | Settings | File Templates.
 */
public class WebUtil {
    private static ThreadLocal<HttpServletRequest> requests = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> responses = new ThreadLocal<HttpServletResponse>();
    private static ServletContext servletContext = null;

    private static SqlDao sqlDao = null;

    private static JmsTemplate jmsTemplate = null;



    public static SqlDao getSqlDao() {
        return sqlDao;
    }

    public static void setSqlDao(SqlDao sDao) {
        sqlDao = sDao;
    }

    public static JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public static void setJmsTemplate(JmsTemplate sjmsTemplate) {
        jmsTemplate = sjmsTemplate;
    }


    public static CdsUsers getUser(){
        CdsUsers cdsUsers =  WebUtil.getSession(LOGIN_USER_KEY);
        return cdsUsers;
    }

    /**
     * 将请求对象存储到本地线程中
     *
     * @param request 请求对象
     */
    public static void setRequest(HttpServletRequest request) {
        requests.remove();
        requests.set(request);
    }

    /**
     * 从本地线程中得到请求对象
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return requests.get();
    }

    /**
     * 移除请求对象
     */
    public static void removeRequest() {
        requests.remove();
    }

    /**
     * 将响应对象存储到本地线程
     *
     * @param response 响应对象
     */
    public static void setResponse(HttpServletResponse response) {
        responses.remove();
        responses.set(response);
    }

    /**
     * 从本地线程中得到响应对象
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return responses.get();
    }

    /**
     * 移除响应对象
     */
    public static void removeResponse() {
        responses.remove();
    }

    /**
     * 从本地线程中得到会话对象
     *
     * @return HttpServletResponse
     */
    public static HttpSession getSession() {
        return getRequest().getSession(true);
    }


    /**
     * 将全局对象存储到本地线程
     *
     * @param context 全局对象
     */
    public static void setServletContext(ServletContext context) {
        servletContext = context;
    }

    /**
     * 从本地线程中得到全局对象
     *
     * @return ServletContext
     */
    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    public static WebApplicationContext getWebApplicationContext() {
        return WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

    /**
     * 移除全局对象
     */
    public static void removeServletContext() {
        servletContext = null;
    }

    /**
     * 请求参数获取
     *
     * @param key
     * @return
     */
    public static <T> T getRequest(String key) {
        return (T) getRequest().getAttribute(key);
    }

    /**
     * 请求参数设置
     *
     * @param key
     * @param obj
     */
    public static void setRequest(String key, Object obj) {
        getRequest().setAttribute(key, obj);
    }

    /**
     * 请求参数移除
     *
     * @param key
     */
    public static void removeRequest(String key) {
        getRequest().removeAttribute(key);
    }

    /**
     * 回话级参数获取
     *
     * @param key
     * @return
     */
    public static <T> T getSession(String key) {
        return (T) getSession().getAttribute(key);
    }

    /**
     * 回话级参数设置
     *
     * @param key
     * @param obj
     */
    public static void setSession(String key, Object obj) {
        getSession().setAttribute(key, obj);
    }

    /**
     * 回话级参数移除
     *
     * @param key
     */
    public static void removeSession(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * 应用级参数获取
     *
     * @param key
     * @return
     */
    public static <T> T getApplication(String key) {
        return (T) getServletContext().getAttribute(key);
    }

    /**
     * 应用级参数设置
     *
     * @param key
     * @param obj
     */
    public static void setApplication(String key, Object obj) {
        getServletContext().setAttribute(key, obj);
    }

    /**
     * 应用级参数移除
     *
     * @param key
     */
    public static void removeApplication(String key) {
        getServletContext().removeAttribute(key);
    }
}
