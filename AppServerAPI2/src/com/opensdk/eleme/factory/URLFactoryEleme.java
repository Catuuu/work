package com.opensdk.eleme.factory;

import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.util.PropertiesUtil;
import com.opensdk.eleme.vo.SystemParamEleme;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenbin on 17/02/05.
 */
public class URLFactoryEleme {

    private static String urlPrefix = "";
    private static Map<String, String> urlMap;
    private static Map<String, String> urlTypeMap;

    static {
        urlMap = new HashMap<String, String>();
        urlTypeMap = new HashMap<String, String>();

        //门店
        urlMap.put("poiSave", "poi/save");
        urlMap.put("poiGetIds", "poi/getids");
        urlMap.put("poiMget", "poi/mget");
        urlMap.put("poiOpen", "poi/open");
        urlMap.put("poiClose", "poi/close");
        urlMap.put("poiOffline", "poi/offline");
        urlMap.put("poiOnline", "poi/online");
        urlMap.put("poiQualifySave", "poi/qualify/save");
        urlMap.put("poiSendTimeSave", "poi/sendtime/save");
        urlMap.put("poiAdditionalSave", "poi/additional/save");
        urlMap.put("poiUpdatepromoteinfo", "poi/updatepromoteinfo");
        urlMap.put("poiList", "poi/list");
        urlMap.put("poiTagList", "poiTag/list");
        urlMap.put("poiUpdatePromotionInfo", "poi/updatepromoteinfo");
        urlMap.put("poiUpdateShippingtime", "poi/shippingtime/update");
        urlMap.put("poiIsDelayPushLogistics", "poi/logistics/isDelayPush");
        urlMap.put("poiSetDelayPushLogistics", "poi/logistics/setDelayPush");
        urlMap.put("poiComment", "poi/comment/app_poi_code");


        urlTypeMap.put("poiSave", "POST");
        urlTypeMap.put("poiGetIds", "GET");
        urlTypeMap.put("poiMget", "GET");
        urlTypeMap.put("poiOpen", "POST");
        urlTypeMap.put("poiClose", "POST");
        urlTypeMap.put("poiOffline", "POST");
        urlTypeMap.put("poiOnline", "POST");
        urlTypeMap.put("poiQualifySave", "POST");
        urlTypeMap.put("poiSendTimeSave", "POST");
        urlTypeMap.put("poiAdditionalSave", "POST");
        urlTypeMap.put("poiUpdatepromoteinfo", "POST");
        urlTypeMap.put("poiList", "GET");
        urlTypeMap.put("poiTagList", "POST");
        urlTypeMap.put("poiUpdatePromotionInfo", "POST");
        urlTypeMap.put("poiUpdateShippingtime", "POST");
        urlTypeMap.put("poiIsDelayPushLogistics", "POST");
        urlTypeMap.put("poiSetDelayPushLogistics", "POST");
        urlTypeMap.put("poiComment", "GET");


        //配送
        urlMap.put("shippingSave", "shipping/save");
        urlMap.put("shippingList", "shipping/list");
        urlMap.put("shippingBatchSave", "shipping/batchsave");
        urlMap.put("isDelayPushLogistics", "logistics/isDelayPush");
        urlMap.put("setDelayPushLogistics", "logistics/setDelayPush");


        urlTypeMap.put("shippingSave", "POST");
        urlTypeMap.put("shippingList", "GET");
        urlTypeMap.put("shippingBatchSave", "POST");
        urlTypeMap.put("isDelayPushLogistics", "POST");
        urlTypeMap.put("setDelayPushLogistics", "POST");


        //菜品
        urlMap.put("foodList", "food/list");
        urlMap.put("foodListByPage", "food/list");
        urlMap.put("foodSave", "food/save");
        urlMap.put("foodBatchSave", "food/batchsave");
        urlMap.put("foodInitData", "food/initdata");
        urlMap.put("foodBatchInitData", "food/batchinitdata");
        urlMap.put("foodDelete", "food/delete");
        urlMap.put("foodSkuSave", "food/sku/save");
        urlMap.put("foodSkuDelete", "food/sku/delete");
        urlMap.put("updateFoodSkuStock", "food/sku/stock");
        urlMap.put("updateFoodSkuPrice", "food/sku/price");
        urlMap.put("incFoodSkuStock", "food/sku/inc_stock");
        urlMap.put("descFoodSkuStock", "food/sku/desc_stock");
        urlMap.put("foodCatList", "foodCat/list");
        urlMap.put("foodCatUpdate", "foodCat/update");
        urlMap.put("foodCatDelete", "foodCat/delete");

        urlTypeMap.put("foodList", "GET");
        urlTypeMap.put("foodSave", "POST");
        urlTypeMap.put("foodBatchSave", "POST");
        urlTypeMap.put("foodInitData", "POST");
        urlTypeMap.put("foodBatchInitData", "POST");
        urlTypeMap.put("foodDelete", "POST");
        urlTypeMap.put("foodSkuSave", "POST");
        urlTypeMap.put("foodSkuDelete", "POST");
        urlTypeMap.put("updateFoodSkuStock", "POST");
        urlTypeMap.put("updateFoodSkuPrice", "POST");
        urlTypeMap.put("incFoodSkuStock", "POST");
        urlTypeMap.put("descFoodSkuStock", "POST");
        urlTypeMap.put("foodCatList", "GET");
        urlTypeMap.put("foodCatUpdate", "POST");
        urlTypeMap.put("foodCatDelete", "POST");


        //图片
        urlMap.put("imageUpload", "image/upload");
        urlTypeMap.put("imageUpload", "POST");


        //订单
        urlMap.put("orderGetOrderDetail", "order/<eleme_order_id>/"); //查询订单详情
        urlTypeMap.put("orderGetOrderDetail", "GET");

        urlMap.put("orderBatchGet", "orders/batch_get/"); //批量查询订单详情
        urlTypeMap.put("orderBatchGet", "GET");

        urlMap.put("orderStatus", "order/<eleme_order_id>/status/");//查询订单状态
        urlTypeMap.put("orderStatus", "GET");

        urlMap.put("orderStatusUpdate", "order/<eleme_order_id>/status/");//修改订单状态
        urlTypeMap.put("orderStatusUpdate", "PUT");

        urlMap.put("orderCancel", "order/<eleme_order_id>/status/");//商家取消订单
        urlTypeMap.put("orderCancel", "PUT");

        urlMap.put("orderReceived", "order/<eleme_order_id>/status/");//商家确认订单
        urlTypeMap.put("orderReceived", "PUT");

        urlMap.put("orderRefundAgree", "order/<eleme_order_id>/agree_refund/");//同意退单
        urlTypeMap.put("orderRefundAgree", "POST");

        urlMap.put("orderRefundReject", "order/<eleme_order_id>/disagree_refund/");//不同意退单
        urlTypeMap.put("orderRefundReject", "POST");

        urlMap.put("orderPullNew", "order/pull/new/");//拉取对应餐厅的新订单
        urlTypeMap.put("orderPullNew", "GET");

        urlMap.put("orderNew", "order/new/");//查询新订单
        urlTypeMap.put("orderNew", "GET");

        urlMap.put("orderDelivery", "order/delivery/");//批量获取订单的配送信息
        urlTypeMap.put("orderDelivery", "GET");

        urlMap.put("elestoresBind", "restaurant/binding/");//饿了么绑定餐厅
        urlTypeMap.put("elestoresBind", "POST");

        urlMap.put("elestoresBindQuery", "restaurant/binding/");//根据stores_id查询饿了么绑定餐厅信息
        urlTypeMap.put("elestoresBindQuery", "GET");

        urlMap.put("elestoresBindAresh", "restaurant/binding/");//饿了么重新绑定餐厅
        urlTypeMap.put("elestoresBindAresh", "PUT");

        urlMap.put("elestoresMode", "restaurant/<restaurant_id>/order_mode/");//饿了么餐厅接单模式
        urlTypeMap.put("elestoresMode", "PUT");

        urlMap.put("elestoresModeQuery", "restaurants/batch_status/");//饿了么餐厅接单模式查询
        urlTypeMap.put("elestoresModeQuery", "GET");

        urlMap.put("eleGoodClassBind", "new_food_category/");//饿了么分类绑定
        urlTypeMap.put("eleGoodClassBind", "POST");

        urlMap.put("eleGoodClassBindUpdate", "new_food_category/<food_category_id>/");//饿了么分类更新
        urlTypeMap.put("eleGoodClassBindUpdate", "PUT");

        urlMap.put("eleGoodClassBindDelete", "new_food_category/<food_category_id>/");//饿了么分类删除
        urlTypeMap.put("eleGoodClassBindDelete", "DELETE");

        urlMap.put("eleGoodClassBindQuery", "new_food_category/<food_category_id>/");//饿了么分类查询
        urlTypeMap.put("eleGoodClassBindQuery", "GET");

        urlMap.put("eleStoresGoodClassQuery", "restaurant/<restaurant_id>/food_categories/");//饿了么门店下所有分类查询
        urlTypeMap.put("eleStoresGoodClassQuery", "GET");

        urlMap.put("eleGoodQuery", "food/<food_id>/");//查询食物详情
        urlTypeMap.put("eleGoodQuery", "GET");

        urlMap.put("eleClassGoodsQuery", "food_category/<food_category_id>/foods/");//查询分类食物列表
        urlTypeMap.put("eleClassGoodsQuery", "GET");

        urlMap.put("eleUploadByUrl", "image/upload_by_url/");//通过url上传图片
        urlTypeMap.put("eleUploadByUrl", "POST");

        urlMap.put("eleSaveFood", "food/");//添加食物
        urlTypeMap.put("eleSaveFood", "POST");

        urlMap.put("eleUpdateFood", "food/<food_id>/");//更新食物
        urlTypeMap.put("eleUpdateFood", "PUT");

        urlMap.put("eleDeleteFood", "food/<food_id>/");//删除食物
        urlTypeMap.put("eleDeleteFood", "DELETE");

        urlMap.put("eleUpdateStores", "restaurant/<restaurant_id>/info/");//删除食物
        urlTypeMap.put("eleUpdateStores", "PUT");

        //评价信息
        urlMap.put("commentList", "comment/<restaurant_id>/list_view/");//获取店铺评价列表;
        urlTypeMap.put("commentList", "GET");

        urlMap.put("commentCount", "comment/<restaurant_id>/count/");//获取店铺评价总数;
        urlTypeMap.put("commentCount", "GET");

        urlMap.put("commentReply", "comment/<restaurant_id>/reply/");//回复评论;
        urlTypeMap.put("commentReply", "POST");
    }

    /**
     * 通过方法名生成url
     *
     * @param methodName
     * @return
     */
    public static String genUrlPrefix(String methodName) throws ApiSysException {
        return genUrlPrefix(methodName, null);
    }

    /**
     * 通过方法名生成url
     *
     * @param methodName
     * @return
     */
    public static String genUrlPrefix(String methodName, Map<String, String> applicationParamsMap) throws ApiSysException {
        if (urlPrefix.equals("")) {
            String env = PropertiesUtil.getEnvironmentMode();
            if ("0".equals(env)) {
                urlPrefix = "http://v2.openapi.ele.me/";
            } else if ("1".equals(env)) {
                urlPrefix = "http://v2.openapi.ele.me/";
            } else if ("2".equals(env)) {
                urlPrefix = "http://127.0.0.1:9000/api/v1/";
            }
        }

        String url = "";
        if (applicationParamsMap != null) {
            String urlPath = urlMap.get(methodName);
            Object[] key_arr = applicationParamsMap.keySet().toArray();

            for (Object key : key_arr) {
                String val = applicationParamsMap.get(key);
                if (urlPath.indexOf("<" + key + ">") > -1) {
                    urlPath = urlPath.replaceAll("<" + key + ">", val);
                    applicationParamsMap.remove(key);
                }
            }
            url = urlPrefix + urlPath;
        } else {
            url = urlPrefix + urlMap.get(methodName);
        }
        return url;
    }

    /**
     * 获取请求的类型
     *
     * @param methodName
     * @return
     */
    public static String genUrlType(String methodName) {
        String methodType = urlTypeMap.get(methodName);

        return methodType;
    }

    public static String genUrlForGenSig(String methodName, Map<String, String> systemParamsMap,
                                         Map<String, String> applicationParamsMap, SystemParamEleme systemParam) throws ApiSysException {


        String basedUrl = genUrlPrefix(methodName, applicationParamsMap);

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.putAll(systemParamsMap);
        //如果应用级参数不为空，则组合应用级参数
        if (applicationParamsMap != null) {
            paramMap.putAll(applicationParamsMap);
        }
        String str = concatParams(paramMap);
        basedUrl = basedUrl + "?" + str + systemParam.getConsumer_secret();
        return basedUrl;
    }

    public static String genOnlyHasSysParamsAndSigUrl(String urlPrefix, Map<String, String> systemParamsMap, String sig) {
        String str = concatParams(systemParamsMap);
        String basedUrl = urlPrefix + "?" + str + "&sig=" + sig;
        return basedUrl;
    }

    public static String genNotOnlySysParamsUrlForGetRequest(String urlPrefix, Map<String, String> systemParamsMap, String sig, Map<String, String> otherParamsMap) {
        systemParamsMap.putAll(otherParamsMap);
        String str = concatParams(systemParamsMap);
        String handledUrl = urlPrefix + "?" + str + "&sig=" + sig;
        return handledUrl;
    }

    public static String genUrlForGetRequest(String urlHasParamsNoSig, String sig) {
        String handledUrl = urlHasParamsNoSig + "&sig=" + sig;
        return handledUrl;
    }

    private static String concatParams(Map<String, String> params2) {
        Object[] key_arr = params2.keySet().toArray();
        Arrays.sort(key_arr);
        String str = "";

        for (Object key : key_arr) {
            if (key.equals("appSecret")) {
                continue;
            }
            String val = params2.get(key);
            str += "&" + key + "=" + val;
        }
        return str.replaceFirst("&", "");
    }

}
