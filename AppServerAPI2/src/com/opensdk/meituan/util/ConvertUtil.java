package com.opensdk.meituan.util;

import com.alibaba.fastjson.JSON;
import com.opensdk.meituan.constants.ErrorEnum;
import com.opensdk.meituan.exception.ApiSysException;
import com.opensdk.meituan.vo.SystemParamMeituan;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by chenbin on 17/02/05.
 */
public class ConvertUtil {

    /**
     * 对象转化成map
     * @param object
     * @return
     */
    public static Map<String,String> convertToMap(Object object) throws ApiSysException {
        Map<String,String> resultMap = new HashMap<String, String>();
        try{
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                Class typeClazz = field.getType();
                String key = field.getName();
                String val = null;
                if(List.class.isAssignableFrom(typeClazz)){
                    val = JSON.toJSONString(field.get(object));
                }else {
                    val = String.valueOf(field.get(object));
                }
                if (val != null && !"".equals(val) && !"null".equals(val) && !"NULL".equals(val)) {
                    resultMap.put(key, val);
                }
            }
        }catch (Exception e){
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return resultMap;
    }

    /**
     * 系统参数转化成map
     * @param systemParam
     * @return
     */
    public static Map<String,String> convertSystemParamsToMap(SystemParamMeituan systemParam){
        Map<String,String> resultMap = new HashMap<String, String>();
        resultMap.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));
        resultMap.put("app_id",systemParam.getAppId());
        resultMap.put("appSecret",systemParam.getAppSecret());
        return resultMap;
    }

    /**
     * 对象转化为参数列表
     * @param applicationParamsMap
     * @return
     */
    public static List<BasicNameValuePair> convertToEntity(Map<String,String> applicationParamsMap)
        throws ApiSysException{
        List<BasicNameValuePair> formParam = new ArrayList<BasicNameValuePair>();
        try{
            if(applicationParamsMap != null){
                Iterator<Map.Entry<String, String>> iterator = applicationParamsMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, String> entry = iterator.next();
                    if(entry.getValue() != null && !"".equals(entry.getValue()) &&
                       !"null".equals(entry.getValue()) && !"NULL".equals(entry.getValue())) {
                        BasicNameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                        formParam.add(nameValuePair);
                    }
                }
            }
        }catch (Exception e){
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }

        return formParam;
    }

    /**
     * 对象转化为参数列表
     * @param applicationParamsMap
     * @return
     */
    public static List<NameValuePair> convertToEntityBasic(Map<String,String> applicationParamsMap)
        throws ApiSysException{
        List<NameValuePair> formParam = new ArrayList<NameValuePair>();
        try{
            if(applicationParamsMap != null){
                Iterator<Map.Entry<String, String>> iterator = applicationParamsMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, String> entry = iterator.next();
                    BasicNameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), JSON.toJSONString(entry.getValue()));
                    formParam.add(nameValuePair);
                }
            }
        }catch (Exception e){
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }

        return formParam;
    }

}
