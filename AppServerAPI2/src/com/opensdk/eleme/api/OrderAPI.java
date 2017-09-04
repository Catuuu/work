package com.opensdk.eleme.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensdk.eleme.constants.ErrorEnum;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.vo.*;
import com.opensdk.eleme.constants.ParamRequiredEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenbin on 17/02/05.
 */
public class OrderAPI extends API {


    /**
     * 饿了么获取订单详细信息
     *
     * @param systemParam    系统参数
     * @param eleme_order_id 订单id
     * @return
     */
    public OrderParam orderGetOrderDetail(SystemParamEleme systemParam, String eleme_order_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderGetOrderDetail, systemParam, eleme_order_id);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("eleme_order_id", String.valueOf(eleme_order_id));
        applicationParamsMap.put("tp_id", String.valueOf(1));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderGetOrderDetail);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        OrderParam orderParam = null;
        try {
            JSONObject jo = JSONObject.parseObject(data);
            JSONObject jodetail = JSONObject.parseObject(jo.getString("detail"));

            List<OrderExtraParam> orderExtraParams = JSONArray.parseArray(jodetail.getString("extra"), OrderExtraParam.class);

            List<OrderFoodDetailParam> orderFoodDetailParams = new ArrayList<OrderFoodDetailParam>();
            JSONArray groupArray = JSONArray.parseArray(jodetail.getString("group"));
            for (int i = 0; i < groupArray.size(); i++) {
                JSONArray basket = (JSONArray) groupArray.get(i);//获取篮子
                for (int j = 0; j < basket.size(); j++) {//循环每个篮子下面的菜品
                    JSONObject food = (JSONObject) basket.get(j);
                    JSONArray garnish = (JSONArray) food.get("garnish");
                    for (int k = 0; k < garnish.size(); k++) {
                        JSONObject foodgarnish = (JSONObject) basket.get(j);
                        OrderFoodDetailParam foodDetailParam = JSONObject.toJavaObject(foodgarnish, OrderFoodDetailParam.class);
                        orderFoodDetailParams.add(foodDetailParam);
                    }
                    OrderFoodDetailParam foodDetailParam = JSONObject.toJavaObject(food, OrderFoodDetailParam.class);
                    orderFoodDetailParams.add(foodDetailParam);
                }

            }

            OrderDetailParam detail = new OrderDetailParam();
            detail.setExtra(orderExtraParams);
            detail.setGroup(orderFoodDetailParams);
            jo.put("detail", detail);

            orderParam = JSONObject.toJavaObject(jo, OrderParam.class);

        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return orderParam;
    }


    /**
     * 饿了么批量获取订单详细信息
     *
     * @param day           系统参数
     * @param restaurant_id 餐厅ID
     * @param statuses      订单状态   -1、订单已取消  0、订单未处理 1、订单等待餐厅确认 2、订单已处理 9、订单已完成
     * @return
     */
    public List<String> orderBatchGet(SystemParamEleme systemParam, String day, int restaurant_id, String statuses)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.orderBatchGet, systemParam, day, restaurant_id, statuses);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("day", day);
        applicationParamsMap.put("restaurant_id", String.valueOf(restaurant_id));
        applicationParamsMap.put("statuses", statuses);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.orderBatchGet);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<String> orders = null;

        try {
            JSONObject jo = JSONObject.parseObject(data);
            orders = JSONArray.parseArray(jo.getString("order_ids"), String.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return orders;
    }


    /**
     * 饿了么批量获取订单详细信息
     *
     * @param day           系统参数
     * @param restaurant_id 餐厅ID
     * @return
     */
    public List<String> orderBatchGet(SystemParamEleme systemParam, String day, int restaurant_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.orderBatchGet, systemParam, day, restaurant_id);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("day", day);
        applicationParamsMap.put("restaurant_id", String.valueOf(restaurant_id));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.orderBatchGet);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<String> orders = null;

        try {
            JSONObject jo = JSONObject.parseObject(data);
            orders = JSONArray.parseArray(jo.getString("order_ids"), String.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return orders;
    }

    /**
     * 获取订单状态    -1、订单已取消  0、订单未处理 1、订单等待餐厅确认 2、订单已处理 9、订单已完成
     *
     * @param systemParam    系统参数
     * @param eleme_order_id 订单id
     * @return
     */
    public JSONObject orderStatus(SystemParamEleme systemParam, String eleme_order_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("eleme_order_id", eleme_order_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.orderStatus);
        String data = requestApi(methodName, systemParam, applicationParamsMap);

        return JSONObject.parseObject(data);
    }


    /**
     * 修改订单状态
     *
     * @param systemParam    系统参数
     * @param eleme_order_id 订单id
     * @param status         -1、订单已取消  0、订单未处理 1、订单等待餐厅确认 2、订单已处理 9、订单已完成
     * @return
     */
    public String orderStatusUpdate(SystemParamEleme systemParam, String eleme_order_id, int status)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("eleme_order_id", eleme_order_id);
        applicationParamsMap.put("status", String.valueOf(status));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.orderStatusUpdate);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 商家取消订单
     *
     * @param systemParam    系统参数
     * @param eleme_order_id 订单id
     * @param reason         取消原因
     * @return
     */
    public String orderCancel(SystemParamEleme systemParam, String eleme_order_id, String reason)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("eleme_order_id", eleme_order_id);
        applicationParamsMap.put("reason", reason);
        applicationParamsMap.put("status", String.valueOf(-1));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderCancel);
        return requestApi(methodName, systemParam, applicationParamsMap);
    }


    /**
     * 将订单设为商家已收到
     *
     * @param systemParam    系统参数
     * @param eleme_order_id 订单id
     * @return
     */
    public String orderReceived(SystemParamEleme systemParam, String eleme_order_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("eleme_order_id", eleme_order_id);
        applicationParamsMap.put("status", String.valueOf(2));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderReceived);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }


    /**
     * 订单确认退款请求
     *
     * @param systemParam    系统参数
     * @param eleme_order_id 订单id
     * @return
     */
    public String orderRefundAgree(SystemParamEleme systemParam, String eleme_order_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("eleme_order_id", eleme_order_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderRefundAgree);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 驳回订单退款申请
     *
     * @param systemParam    系统参数
     * @param eleme_order_id 订单id
     * @param reason         驳回退款详情
     * @return
     */
    public String orderRefundReject(SystemParamEleme systemParam, String eleme_order_id, String reason)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("eleme_order_id", eleme_order_id);
        applicationParamsMap.put("reason", reason);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderRefundReject);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 饿了么拉取对应餐厅的新订单    返回当前时间前30分钟的订单
     *
     * @param restaurant_id 餐厅ID
     * @return
     */
    public List<String> orderPullNew(SystemParamEleme systemParam, int restaurant_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("restaurant_id", String.valueOf(restaurant_id));

        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.orderPullNew);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<String> orders = null;

        try {
            JSONObject jo = JSONObject.parseObject(data);
            orders = JSONArray.parseArray(jo.getString("order_ids"), String.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return orders;
    }

    /**
     * 饿了么查询新订单    新订单是以是否被获取到为判断标准，与订单状态无关。即如果订单已经通过调用/order/new/接口被获取过，下次调用该接口，将不会有该订单。
     *
     * @param restaurant_id 餐厅ID
     * @return
     */
    public List<String> orderNew(SystemParamEleme systemParam, int restaurant_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("restaurant_id", String.valueOf(restaurant_id));

        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.orderNew);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<String> orders = new ArrayList<String>();

        try {
            JSONObject jo = JSONObject.parseObject(data);
            JSONArray jsonArray = jo.getJSONArray("order_ids");
            for (int i = 0; i < jsonArray.size(); i++) {
                orders.add(jsonArray.getString(i));
            }
           // orders = JSONArray.parseArray(jo.getString("order_ids"), String.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return orders;
    }

    /**
     * 拉取订单配送信息
     *
     * @param systemParam     系统参数
     * @param eleme_order_ids (123,456)订单号列表(列表数量不大于50)
     * @return
     */
    public String orderDelivery(SystemParamEleme systemParam, String eleme_order_ids)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("eleme_order_ids", eleme_order_ids);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.orderDelivery);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }
}
