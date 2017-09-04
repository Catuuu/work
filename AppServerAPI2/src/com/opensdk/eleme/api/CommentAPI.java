package com.opensdk.eleme.api;

import com.opensdk.eleme.constants.ParamRequiredEnum;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.vo.SystemParamEleme;

import java.util.HashMap;
import java.util.Map;

/**
 * 饿了么评价信息
 * Created by Administrator on 2017/7/10 0010.
 */
public class CommentAPI extends API {
    /**
     * 店铺评价列表信息
     *
     * @param systemParam   系统参数
     * @param restaurant_id 饿了么餐厅ID
     * @param offset        位移
     * @param limit         评论数量
     * @return
     */
    public String commentList(SystemParamEleme systemParam, String restaurant_id, int offset, int limit)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("restaurant_id", restaurant_id);
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.CommentList);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 店铺评价总数
     *
     * @param systemParam   系统参数
     * @param restaurant_id 饿了么餐厅ID
     * @return
     */
    public String commentCount(SystemParamEleme systemParam, String restaurant_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("restaurant_id", restaurant_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.CommentCount);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 店铺评价回复
     *
     * @param systemParam   系统参数
     * @param restaurant_id 饿了么餐厅ID
     * @param comment_id 评论ID
     * @param content 评论内容
     * @param replier_name 评论人
     * @return
     */
    public String commentReply(SystemParamEleme systemParam, String restaurant_id, String comment_id, String content, String replier_name)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("restaurant_id", restaurant_id);
        applicationParamsMap.put("comment_id", comment_id);
        applicationParamsMap.put("content", content);
        applicationParamsMap.put("replier_name", replier_name);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.CommentReply);

        return requestApiStr(methodName, systemParam, applicationParamsMap);
    }
}
