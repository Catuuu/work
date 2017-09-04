package com.opensdk.eleme.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 请求参数配置
 * Created by chenbin on 17/02/05.
 */
public enum ParamRequiredEnum {
    SysParam("appId, appSecret, timestamp"),
    //门店
    PoiSave("app_poi_code, name, address, latitude, longitude, phone, shipping_fee, shipping_time, "
            + "open_level, is_online, third_tag_name"),
    PoiMGet("app_poi_codes"),
    PoiOPen("app_poi_code"),
    PoiClose("app_poi_code"),
    PoiOffline("app_poi_code, reason"),
    PoiOnline("app_poi_code"),
    PoiQualifySave("app_poi_code, type, qualification_url"),
    PoiSendTimeSave("app_poi_codes, send_time"),
    PoiAdditionalSave("app_poi_code"),
    PoiUpdatePromotionInfo("app_poi_code"),
    PoiUpdateShippingTime("app_poi_code"),
    PoiIsDelayPushLogistics("app_poi_code"),
    PoiSetDelayPushLogistics("app_poi_code"),
    PoiComment("app_poi_code"),

    //配送
    ShippingSave("app_poi_code, app_shipping_code, type, area, min_price"),
    ShippingList("app_poi_code"),
    ShippingBatchSave("app_poi_code, shipping_data"),
    ElestoresBind("restaurant_id,tp_restaurant_id"),//饿了么绑定餐厅
    ElestoresBindQuery("tp_restaurant_id"),//根据门店stores_id查询饿了么绑定餐厅信息
    ElestoresBindAresh("restaurant_id,tp_restaurant_id"),//饿了么重新绑定餐厅
    ElestoresMode("restaurant_id,order_mode"),//饿了么餐厅接单模式
    ElestoresModeQuery("restaurant_ids"),//饿了么餐厅接单模式查询

    //菜品
    FoodCatUpdate("app_poi_code, category_name"),
    FoodCatList("app_poi_code"),
    FoodCatDelete("app_poi_code, category_name"),
    FoodList("app_poi_code"),
    FoodListByPage("app_poi_code, offset, limit"),
    FoodSave("app_poi_code, app_food_code"),
    FoodBatchSave("app_poi_code, food_data"),
    FoodInitData("app_poi_code, app_food_code, category_name"),
    FoodBatchInitData("app_poi_code, food_data"),
    FoodDelete("app_poi_code, app_food_code"),
    FoodSkuSave("app_poi_code, app_food_code, skus"),
    FoodSkuDelete("app_poi_code, app_food_code, sku_id"),
    UpdateFoodSkuStock("app_poi_code, food_data"),
    UpdateFoodSkuPrice("app_poi_code, food_data"),
    incFoodSkuStock("app_poi_code, food_data"),
    descFoodSkuStock("app_poi_code, food_data"),


    //菜品分类
    EleGoodClassBind("restaurant_id,name"),//饿了么食物分类绑定
    EleGoodClassBindQuery("food_category_id"),//饿了么食物分类查询
    EleGoodClassBindUpdate("food_category_id,name"),//饿了么食物分类更新
    EleGoodClassBindDelete("food_category_id"),//删除饿了么食物分类
    EleStoresGoodClassQuery("restaurant_id"),//饿了么门店下所有分类查询
    EleGoodQuery("food_id"),//查询食物详情
    EleClassGoodsQuery("food_category_id"),//查询分类食物列表
    EleUploadByUrl("image_url"),//通过url上传图片
    EleSaveFood("food_category_id,name,price,description,max_stock,stock"),//添加食物
    EleUpdateFood("food_id"),//更新食物
    EleDeleteFood("food_id"),//删除食物
    EleUpdateStores("restaurant_id"),//更新餐厅信息


    //图片
    ImageUpload("app_poi_code, img_name"),


    //订单
    OrderGetOrderDetail("eleme_order_id"),   //查询订单详细信息
    orderBatchGet("day,restaurant_id"), //根据餐厅批量获取订单ID

    orderStatus("eleme_order_id"),//查询订单状态
    orderStatusUpdate("eleme_order_id,status"),//商家确认订单
    OrderCancel("eleme_order_id, status, reason"),//商家取消订单
    OrderReceived("eleme_order_id,status"),//商家确认订单
    OrderRefundAgree("eleme_order_id"),//同意退单
    OrderRefundReject("eleme_order_id, reason"),//不同意退单
    orderPullNew("restaurant_id"),//拉取新订单

    orderNew("restaurant_id"),//查询新订单
    orderDelivery("eleme_order_ids"),//批量获取订单的配送信息;


    //评价信息
    CommentList("restaurant_id,offset,limit"),//获取餐厅订单评价列表;
    CommentCount("restaurant_id"),//获取餐厅订单评价总数;
    CommentReply("restaurant_id,comment_id,content,replier_name");//评论回复


    private String paramNames;

    ParamRequiredEnum(String paramNames) {
        this.paramNames = paramNames;
    }

    public String getParamNames() {
        return paramNames;
    }

    public static List<String> getParams(ParamRequiredEnum paramRequiredEnum) {
        String paramNames = paramRequiredEnum.getParamNames();
        List<String> paramNameListTemp = Arrays.asList(paramNames.split(","));
        List<String> paramNameList = new ArrayList<String>();
        for (String paramName : paramNameListTemp) {
            paramNameList.add(paramName.trim());
        }
        return paramNameList;
    }
}
