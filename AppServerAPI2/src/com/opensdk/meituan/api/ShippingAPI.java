package com.opensdk.meituan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensdk.meituan.constants.ErrorEnum;
import com.opensdk.meituan.constants.ParamRequiredEnum;
import com.opensdk.meituan.exception.ApiOpException;
import com.opensdk.meituan.exception.ApiSysException;
import com.opensdk.meituan.vo.ShippingParam;
import com.opensdk.meituan.vo.SystemParamMeituan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenbin on 17/02/05.
 * 配送范围
 */
public class ShippingAPI extends API {

    /**
     * 创建／更新门店配送范围
     *
     * @param systemParam     系统参数
     * @param appPoiCode      门店code
     * @param appShippingCode APP方提供的配送范围id，如果一个门店没有配送范围ID，且每个门店只有一个配送范围， 可以填'1'
     * @param type            配置范围类型k, （'1'表示多个配送范围由多个多边形组成）只支持多边形
     * @param area            配送范围 type 为 1 时: [{"x":39941199,"y":116385384},{
     *                        "x":39926983,"y":116361694}, {"x ":39921586,"y":116398430}] type 为 2
     *                        时: {"r":1000,"x":40029380,"y":1164 18390}， 需要对其urlEncode，x代表纬度，y代表经度（美团使用的是高德坐标系，也就是火星坐标系，如果是百度
     *                        坐标系需要转换，配送范围坐标需要乘以一百万)
     * @param minPrice        该配送区域的起送价
     * @param shipping_fee    该配送区域的配送费(建议填写这个字段设定配送费,如果此字段为空,则以门店保存的配送费为准)

     */
    public String shippingSave(SystemParamMeituan systemParam, String appPoiCode,
                               String appShippingCode,
                               String type, String area, String minPrice, String shipping_fee)
        throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_shipping_code", appShippingCode);
        applicationParamsMap.put("type", type);
        applicationParamsMap.put("area", area);
        applicationParamsMap.put("min_price", minPrice);
        if (shipping_fee != null && !"".equals(shipping_fee) && !"null".equals(shipping_fee) && !"NULL".equals(shipping_fee)) {
            applicationParamsMap.put("shipping_fee", shipping_fee);
        }
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ShippingSave);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 获取门店配送范围
     * @param systemParam  系统参数
     * @param appPoiCode  门店code
     * @return
     */
    public List<ShippingParam> shippingList(SystemParamMeituan systemParam, String appPoiCode)
        throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ShippingList);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<ShippingParam> shippingParams = null;
        try{
            shippingParams = JSONArray.parseArray(data, ShippingParam.class);
        }catch (Exception e){
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return shippingParams;
    }

    /**
     * 批量创建/更新配送范围
     * @param systemParam  系统参数
     * @param appPoiCode  门店code
     * @return
     */
    public String shippingBatchSave(SystemParamMeituan systemParam, String appPoiCode,
                                    List<ShippingParam> shippingParams)
        throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("shipping_data", JSONObject.toJSONString(shippingParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ShippingBatchSave);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

}
