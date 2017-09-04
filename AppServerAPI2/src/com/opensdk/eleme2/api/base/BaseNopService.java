package com.opensdk.eleme2.api.base;



import com.opensdk.eleme2.api.annotation.Service;
import com.opensdk.eleme2.api.exception.ServiceException;
import com.opensdk.eleme2.config.Config;
import com.opensdk.eleme2.oauth.response.Token;
import com.opensdk.eleme2.utils.WebUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BaseNopService {
    private Token token;
    private Map<String, Method> methodMap = new HashMap<String, Method>();
    private Class service;
    private Config config;

    public BaseNopService(Config config, Token token, Class service) {
        this.token = token;
        this.service = service;
        this.config = config;
        Method[] methods = service.getMethods();
        for (Method method : methods) {
            methodMap.put(method.getName(), method);
        }
    }

    public <T> T call(String action,Map<String, Object> parameters) throws ServiceException {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Method method = getMethod(methodName);
        Service annotation = (Service) service.getAnnotation(Service.class);
        if (annotation == null)
            throw new RuntimeException("服务未找到Service注解");
        return WebUtils.call(config, action, parameters, token, method.getGenericReturnType());
    }

    private Method getMethod(String methodName) {
        return methodMap.get(methodName);
    }
}
