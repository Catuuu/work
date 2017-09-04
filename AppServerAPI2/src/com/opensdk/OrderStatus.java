package com.opensdk;

import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.util.PropertiesUtil;
import com.opensdk.eleme.vo.SystemParamEleme;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenbin on 17/02/05.
 */
public class OrderStatus {
    private static Map<String,OrderStatusCode> elemestatusMap;
    private static Map<String,OrderStatusCode> meitianstatusMap;

    static {
        elemestatusMap = new HashMap<String, OrderStatusCode>();
        //订单取消原因
        /*elemestatusMap.put("其它原因","0");
        elemestatusMap.put("假订单","1");
        elemestatusMap.put("重复订单","2");
        elemestatusMap.put("联系不上餐厅","3");
        elemestatusMap.put("联系不上用户","4");
        elemestatusMap.put("餐厅已打烊","6");
        elemestatusMap.put("超出配送范围","7");

        elemestatusMap.put("用户无理由退单","9");
        elemestatusMap.put("配送方检测餐品不合格","10");
        elemestatusMap.put("由于配送过程问题,用户退单","11");
        elemestatusMap.put("订单被替换","12");
        elemestatusMap.put("用户取消订单","13");
        elemestatusMap.put("餐厅长时间未接单，订单自动取消","14");*/

        elemestatusMap.put("店铺太忙",new OrderStatusCode("8","商家现在太忙"));//
        elemestatusMap.put("商品已售完",new OrderStatusCode("5","商品已经售完"));//
        elemestatusMap.put("地址无法配送",new OrderStatusCode("7","超出配送范围"));//
        elemestatusMap.put("店铺已打烊",new OrderStatusCode("6","商家已经打烊"));//
        elemestatusMap.put("联系不上用户",new OrderStatusCode("4","联系不上用户"));//
        elemestatusMap.put("重复订单",new OrderStatusCode("2","重复订单"));//
        elemestatusMap.put("配送员取餐慢",new OrderStatusCode("11","由于配送过程问题,用户退单"));//
        elemestatusMap.put("配送员送餐慢",new OrderStatusCode("11","由于配送过程问题,用户退单"));
        elemestatusMap.put("配送员丢餐、少餐、餐洒",new OrderStatusCode("11","由于配送过程问题,用户退单"));
        elemestatusMap.put("其他原因",new OrderStatusCode("0","其他原因"));

        meitianstatusMap = new HashMap<String, OrderStatusCode>();
        meitianstatusMap.put("店铺太忙",new OrderStatusCode("2008","店铺太忙"));
        meitianstatusMap.put("商品已售完",new OrderStatusCode("2009","商品已售完"));
        meitianstatusMap.put("地址无法配送",new OrderStatusCode("2010","地址无法配送"));
        meitianstatusMap.put("店铺已打烊",new OrderStatusCode("2011","店铺已打烊"));
        meitianstatusMap.put("联系不上用户",new OrderStatusCode("2012","联系不上用户"));
        meitianstatusMap.put("重复订单",new OrderStatusCode("2013","重复订单"));
        meitianstatusMap.put("配送员取餐慢",new OrderStatusCode("2014","配送员取餐慢"));
        meitianstatusMap.put("配送员送餐慢",new OrderStatusCode("2015","配送员送餐慢"));
        meitianstatusMap.put("配送员丢餐、少餐、餐洒",new OrderStatusCode("2016","配送员丢餐、少餐、餐洒"));
        meitianstatusMap.put("其他原因",new OrderStatusCode("1301","其他原因"));
    }

    /**
     * 获取请求的类型
     * @param methodName
     * @return
     */
    public static OrderStatusCode genUrlType(String methodName){
        OrderStatusCode methodType = elemestatusMap.get(methodName);
        return methodType;
    }
}
