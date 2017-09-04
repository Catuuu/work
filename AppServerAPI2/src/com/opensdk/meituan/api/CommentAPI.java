package com.opensdk.meituan.api;

import com.opensdk.meituan.constants.ParamRequiredEnum;
import com.opensdk.meituan.exception.ApiOpException;
import com.opensdk.meituan.exception.ApiSysException;
import com.opensdk.meituan.vo.SystemParamMeituan;

import java.util.HashMap;
import java.util.Map;

/**
 * 美团评价信息
 * Created by Administrator on 2017/7/10 0010.
 */
public class CommentAPI extends API{
    /**
     * 获取店铺评价信息
     *
     * @param systemParam
     * @param start_time
     * @param end_time
     * @param pageoffset
     * @param pagesize
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String commentList(SystemParamMeituan systemParam, String app_poi_code,Long start_time,Long end_time, int pageoffset ,int pagesize )
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.CommentList, systemParam, app_poi_code);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", app_poi_code);
        applicationParamsMap.put("start_time", String.valueOf(start_time));
        applicationParamsMap.put("end_time", String.valueOf(end_time));
        applicationParamsMap.put("pageoffset", String.valueOf(pageoffset));
        applicationParamsMap.put("pagesize", String.valueOf(pagesize));

        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.CommentList);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

}
