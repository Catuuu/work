package com.framework.inteceptor;

import com.alibaba.fastjson.JSONObject;
import com.framework.util.WebUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/6/30.
 */
@Aspect
@Component
public class SystemLogAspect {
    //Controller层切点
    @Pointcut("@annotation(com.framework.annotation.ResourceMethod)")
    public  void controllerAspect() {}

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     */
    @Before("controllerAspect()")
    public  void doBefore(JoinPoint joinPoint) {
        WebUtil.setRequest("classname",joinPoint.getTarget().getClass().getName());
        WebUtil.setRequest("methodname",joinPoint.getSignature().getName());
    }

    @Around("controllerAspect()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {

        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch

        if(WebUtil.getRequest("returnValue")==null){
            // 设置方法的返回值
            WebUtil.getRequest().setAttribute("returnValue", JSONObject.toJSONString(retVal));
        }

        return retVal;
    }
}
