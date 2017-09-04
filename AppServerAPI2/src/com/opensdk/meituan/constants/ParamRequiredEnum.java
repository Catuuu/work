package com.opensdk.meituan.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
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

    //菜品
    FoodCatUpdate("app_poi_code, category_name, sequence"),
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
    FoodGet("app_poi_code,app_food_code"),



    //药品
    MedicineCatSave("app_poi_code, category_code, category_name, sequence"),
    MedicineCatUpdate("app_poi_code, category_code"),
    MedicineCatDelete("app_poi_code, category_code"),
    MedicineCatList("app_poi_code"),
    MedicineList("app_poi_code"),
    MedicineSave("app_poi_code, app_medicine_code, upc, medicine_no, spec, price, stock, "
                 + "category_code, category_name, is_sold_out"),
    MedicineBatchSave("app_poi_code, medicine_data"),
    MedicineUpdate("app_poi_code, app_medicine_code"),
    MedicineBatchUpdate("app_poi_code, medicine_data"),
    MedicineDelete("app_poi_code, app_medicine_code"),
    MedicineStock("app_poi_code, medicine_data"),


    //图片
    ImageUpload("app_poi_code, img_name"),


    //订单
    OrderConfirm("order_id"),
    OrderReceived("order_id"),
    OrderCancel("order_id, reason, reason_code"),
    OrderDelivering("order_id"),
    OrderArrived("order_id"),
    OrderRefundAgree("order_id, reason"),
    OrderRefundReject("order_id, reason"),
    OrderSubsidy("order_id"),
    OrderViewStatus("order_id"),
    OrderGetActDetailByAcId("act_detail_id"),
    OrderGetOrderDetail("order_id"),
    OrderLogisticsPush("order_id"),
    OrderLogisticsCancel("order_id"),
    OrderLogisticsStatus("order_id"),
    OrderZhongbaoShippingFee("order_ids"),
    OrderPrepareZhongbaoDispatch("order_id"),
    OrderConfirmZhongbaoDispatch("order_id"),
    OrderUpdateZhongbaoTip("order_id"),
    OrderCommentOrder("order_id"),
    OrderCommentAddReply("order_id"),
    GetOrderDaySeq("app_poi_code"),
    GetOrderIdByDaySeq("app_poi_code,date_time,day_seq"),

    //评价信息
    CommentList("app_poi_code,start_time,end_time,pageoffset,pagesize");


    private String paramNames;

    ParamRequiredEnum(String paramNames) {
        this.paramNames = paramNames;
    }

    public String getParamNames(){
        return paramNames;
    }

    public static List<String> getParams(ParamRequiredEnum paramRequiredEnum){
        String paramNames = paramRequiredEnum.getParamNames();
        List<String> paramNameListTemp = Arrays.asList(paramNames.split(","));
        List<String> paramNameList = new ArrayList<String>();
        for(String paramName : paramNameListTemp){
            paramNameList.add(paramName.trim());
        }
        return paramNameList;
    }
}
