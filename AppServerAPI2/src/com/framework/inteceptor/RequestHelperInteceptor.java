package com.framework.inteceptor;

import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.RequestLog;
import com.framework.util.BeanUtil;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import org.springframework.core.MethodParameter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

/**
 * spring上下文绑定拦截器
 * User: Administrator
 * Date: 12-12-24
 * Time: 下午5:28
 * 上下文请求拦截器
 */
public class RequestHelperInteceptor extends HandlerInterceptorAdapter {

    @Resource(name = "jmsQueueTemplate")
    protected JmsTemplate jmsTemplate;

    private HandlerMethod handlerMethod;

    //在实际的handler被执行前被调用
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        String key = StringUtil.getPrimaryKey();
        request.setAttribute("startTime", startTime);
        request.setAttribute("request_primary_key", key);

        String paramstr = JSONObject.toJSONString(BeanUtil.createBean(request, HashMap.class));
        request.setAttribute("paramstr", paramstr);


        WebUtil.setRequest(request);
        WebUtil.setResponse(response);
        return super.preHandle(request, response, handler);
    }

    //在handler被执行后被调用
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        this.handlerMethod = ((HandlerMethod) handler);

        //String classname = this.handlerMethod.getBean().getClass().getName();
        //String methodname = this.handlerMethod.getMethod().getName();
        String classname = request.getAttribute("classname").toString();
        String methodname = request.getAttribute("methodname").toString();


        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        String methodParametersstr = "";

        for (MethodParameter methodParameter : methodParameters) {
            //methodParametersstr += methodParameter.getParameterType().toString() + " " + methodParameter.getParameterName() + ",";
            methodParametersstr += methodParameter.getParameterName() + ",";
        }
        if (!methodParametersstr.equals("")) {
            methodParametersstr = methodParametersstr.substring(0, methodParametersstr.length() - 1);
        }

        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        String paramstr = (String) request.getAttribute("paramstr");
        String returnValue = (String) request.getAttribute("returnValue");
        String key = WebUtil.getRequest("request_primary_key");
        request.removeAttribute("startTime");
        request.removeAttribute("paramstr");
        request.removeAttribute("returnValue");
        request.removeAttribute("classname");
        request.removeAttribute("methodname");

        if (returnValue == null || returnValue.equals("null")) {
            returnValue = JSONObject.toJSONString(BeanUtil.createBean(request, HashMap.class));
        }

        RequestLog requestLog = new RequestLog();
        requestLog.setRequestLogKey(key);
        requestLog.setRemoteHost(request.getRemoteHost());
        requestLog.setRemoteAddr(request.getRemoteAddr());
        requestLog.setRequestURL(request.getRequestURL().toString());
        requestLog.setClassname(classname);
        requestLog.setMethodname(methodname);
        requestLog.setMethodParametersstr(methodParametersstr);
        requestLog.setStartTime(new Date(startTime));
        requestLog.setEndTime(new Date(endTime));
        requestLog.setExecuteTime(executeTime);
        requestLog.setParamstr(paramstr);
        requestLog.setReturnValue(returnValue);

        //更新用户信息,修改用户的订餐数量
        jmsTemplate.send("request.log", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(requestLog));
            }
        });
    }


}
