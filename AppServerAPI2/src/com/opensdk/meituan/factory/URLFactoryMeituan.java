package com.opensdk.meituan.factory;

import com.opensdk.meituan.exception.ApiSysException;
import com.opensdk.meituan.util.PropertiesUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenbin on 17/02/05.
 */
public class URLFactoryMeituan {

    private static String urlPrefix = "";
    private static Map<String,String> urlMap;
    private static Map<String,String> urlTypeMap;

    static {
        urlMap = new HashMap<String, String>();
        urlTypeMap = new HashMap<String, String>();

        //门店
        urlMap.put("poiSave","poi/save");
        urlMap.put("poiGetIds","poi/getids");
        urlMap.put("poiMget","poi/mget");
        urlMap.put("poiOpen","poi/open");
        urlMap.put("poiClose","poi/close");
        urlMap.put("poiOffline","poi/offline");
        urlMap.put("poiOnline","poi/online");
        urlMap.put("poiQualifySave","poi/qualify/save");
        urlMap.put("poiSendTimeSave","poi/sendtime/save");
        urlMap.put("poiAdditionalSave","poi/additional/save");
        urlMap.put("poiUpdatepromoteinfo","poi/updatepromoteinfo");
        urlMap.put("poiList","poi/list");
        urlMap.put("poiTagList","poiTag/list");
        urlMap.put("poiUpdatePromotionInfo","poi/updatepromoteinfo");
        urlMap.put("poiUpdateShippingtime","poi/shippingtime/update");
        urlMap.put("poiIsDelayPushLogistics","poi/logistics/isDelayPush");
        urlMap.put("poiSetDelayPushLogistics","poi/logistics/setDelayPush");
        urlMap.put("poiComment","poi/comment/app_poi_code");




        urlTypeMap.put("poiSave","POST");
        urlTypeMap.put("poiGetIds","GET");
        urlTypeMap.put("poiMget","GET");
        urlTypeMap.put("poiOpen","POST");
        urlTypeMap.put("poiClose","POST");
        urlTypeMap.put("poiOffline","POST");
        urlTypeMap.put("poiOnline","POST");
        urlTypeMap.put("poiQualifySave","POST");
        urlTypeMap.put("poiSendTimeSave","POST");
        urlTypeMap.put("poiAdditionalSave","POST");
        urlTypeMap.put("poiUpdatepromoteinfo","POST");
        urlTypeMap.put("poiList","GET");
        urlTypeMap.put("poiTagList","POST");
        urlTypeMap.put("poiUpdatePromotionInfo","POST");
        urlTypeMap.put("poiUpdateShippingtime","POST");
        urlTypeMap.put("poiIsDelayPushLogistics","POST");
        urlTypeMap.put("poiSetDelayPushLogistics","POST");
        urlTypeMap.put("poiComment","GET");



        //配送
        urlMap.put("shippingSave","shipping/save");
        urlMap.put("shippingList","shipping/list");
        urlMap.put("shippingBatchSave","shipping/batchsave");
        urlMap.put("isDelayPushLogistics","logistics/isDelayPush");
        urlMap.put("setDelayPushLogistics","logistics/setDelayPush");



        urlTypeMap.put("shippingSave","POST");
        urlTypeMap.put("shippingList","GET");
        urlTypeMap.put("shippingBatchSave","POST");
        urlTypeMap.put("isDelayPushLogistics","POST");
        urlTypeMap.put("setDelayPushLogistics","POST");


        //菜品
        urlMap.put("foodList","food/list");
        urlMap.put("foodListByPage","food/list");
        urlMap.put("foodSave","food/save");
        urlMap.put("foodBatchSave","food/batchsave");
        urlMap.put("foodInitData","food/initdata");
        urlMap.put("foodBatchInitData","food/batchinitdata");
        urlMap.put("foodDelete","food/delete");
        urlMap.put("foodSkuSave","food/sku/save");
        urlMap.put("foodSkuDelete","food/sku/delete");
        urlMap.put("updateFoodSkuStock","food/sku/stock");
        urlMap.put("updateFoodSkuPrice","food/sku/price");
        urlMap.put("incFoodSkuStock","food/sku/inc_stock");
        urlMap.put("descFoodSkuStock","food/sku/desc_stock");
        urlMap.put("foodCatList","foodCat/list");
        urlMap.put("foodCatUpdate","foodCat/update");
        urlMap.put("foodCatDelete","foodCat/delete");
        urlMap.put("foodGet","food/get");

        urlTypeMap.put("foodList","GET");
        urlTypeMap.put("foodSave","POST");
        urlTypeMap.put("foodBatchSave","POST");
        urlTypeMap.put("foodInitData","POST");
        urlTypeMap.put("foodBatchInitData","POST");
        urlTypeMap.put("foodDelete","POST");
        urlTypeMap.put("foodSkuSave","POST");
        urlTypeMap.put("foodSkuDelete","POST");
        urlTypeMap.put("updateFoodSkuStock","POST");
        urlTypeMap.put("updateFoodSkuPrice","POST");
        urlTypeMap.put("incFoodSkuStock","POST");
        urlTypeMap.put("descFoodSkuStock","POST");
        urlTypeMap.put("foodCatList","GET");
        urlTypeMap.put("foodCatUpdate","POST");
        urlTypeMap.put("foodCatDelete","POST");
        urlTypeMap.put("foodGet","GET");

        //药品
        urlMap.put("medicineCatSave","medicineCat/save");
        urlMap.put("medicineCatUpdate","medicineCat/update");
        urlMap.put("medicineCatDelete","medicineCat/delete");
        urlMap.put("medicineCatList","medicineCat/list");
        urlMap.put("medicineList","medicine/list");
        urlMap.put("medicineSave","medicine/save");
        urlMap.put("medicineBatchSave","medicine/batchsave");
        urlMap.put("medicineUpdate","medicine/update");
        urlMap.put("medicineBatchUpdate","medicine/batchupdate");
        urlMap.put("medicineDelete","medicine/delete");
        urlMap.put("medicineStock","medicine/stock");

        urlTypeMap.put("medicineCatSave","POST");
        urlTypeMap.put("medicineCatUpdate","POST");
        urlTypeMap.put("medicineCatDelete","POST");
        urlTypeMap.put("medicineCatList","GET");
        urlTypeMap.put("medicineList","GET");
        urlTypeMap.put("medicineSave","POST");
        urlTypeMap.put("medicineBatchSave","POST");
        urlTypeMap.put("medicineUpdate","POST");
        urlTypeMap.put("medicineBatchUpdate","POST");
        urlTypeMap.put("medicineDelete","POST");
        urlTypeMap.put("medicineStock","POST");


        //图片
        urlMap.put("imageUpload", "image/upload");
        urlTypeMap.put("imageUpload","POST");


        //订单
        urlMap.put("orderConfirm", "order/confirm");
        urlMap.put("orderReceived", "order/poi_received");
        urlMap.put("orderCancel", "order/cancel");
        urlMap.put("orderDelivering","order/Delivering");
        urlMap.put("orderArrived","order/arrived");
        urlMap.put("orderRefundAgree","order/refund/agree");
        urlMap.put("orderRefundReject","order/refund/reject");
        urlMap.put("orderSubsidy","order/subsidy");
        urlMap.put("orderViewStatus","order/viewstatus");
        urlMap.put("orderGetActDetailByAcId","order/getActDetailByAcid");
        urlMap.put("orderGetOrderDetail","order/getOrderDetail");
        urlMap.put("orderLogisticsPush","order/logistics/push");
        urlMap.put("orderLogisticsCancel","order/logistics/cancel");
        urlMap.put("orderLogisticsStatus","order/logistics/status");
        urlMap.put("orderZhongbaoShippingFee","order/zhongbao/shippingFee");
        urlMap.put("orderZhongbaoDispatchPrepare","order/zhongbao/dispatch/prepare");
        urlMap.put("orderZhongbaoDispatchConfirm","order/zhongbao/dispatch/confirm");
        urlMap.put("orderZhongbaoUpdateTip","order/zhongbao/update/tip");
        urlMap.put("orderCommentOrder","order/comment/order");
        urlMap.put("orderCommentAddReply","order/comment/add_reply");
        urlMap.put("getOrderDaySeq","order/getOrderDaySeq");
        urlMap.put("getOrderIdByDaySeq","order/getOrderIdByDaySeq");



        urlTypeMap.put("orderConfirm", "GET");
        urlTypeMap.put("orderReceived", "GET");
        urlTypeMap.put("orderCancel", "GET");
        urlTypeMap.put("orderDelivering","GET");
        urlTypeMap.put("orderArrived","GET");
        urlTypeMap.put("orderRefundAgree","GET");
        urlTypeMap.put("orderRefundReject","GET");
        urlTypeMap.put("orderSubsidy","GET");
        urlTypeMap.put("orderViewStatus","GET");
        urlTypeMap.put("orderGetActDetailByAcId","GET");
        urlTypeMap.put("orderGetOrderDetail","GET");
        urlTypeMap.put("orderLogisticsPush","GET");
        urlTypeMap.put("orderLogisticsCancel","GET");
        urlTypeMap.put("orderLogisticsStatus","GET");
        urlTypeMap.put("orderZhongbaoShippingFee","GET");
        urlTypeMap.put("orderZhongbaoDispatchPrepare","GET");
        urlTypeMap.put("orderZhongbaoDispatchConfirm","GET");
        urlTypeMap.put("orderZhongbaoUpdateTip","GET");
        urlTypeMap.put("orderCommentOrder","GET");
        urlTypeMap.put("orderCommentAddReply","POST");
        urlTypeMap.put("getOrderDaySeq","GET");
        urlTypeMap.put("getOrderIdByDaySeq","GET");

        //评价信息
        urlMap.put("commentList", "poi/comment/app_poi_code");
        urlTypeMap.put("commentList","GET");
    }

    /**
     * 通过方法名生成url
     * @param methodName
     * @return
     */
    public static String genUrlPrefix(String methodName) throws ApiSysException {
        if(urlPrefix.equals("")){
            String env = PropertiesUtil.getEnvironmentMode();
            if("0".equals(env)){
                urlPrefix = "http://test.waimaiopen.meituan.com/api/v1/";
            }else if("1".equals(env)){
                urlPrefix = "http://waimaiopen.meituan.com/api/v1/";
            }else if("2".equals(env)){
                urlPrefix = "http://127.0.0.1:9000/api/v1/";
            }
        }
        String url = urlPrefix + urlMap.get(methodName);
        return url;
    }

    /**
     * 获取请求的类型
     * @param methodName
     * @return
     */
    public static String genUrlType(String methodName){
        String methodType = urlTypeMap.get(methodName);

        return methodType;
    }

    public static String genUrlForGenSig(String methodName,Map<String, String> systemParamsMap,
                                              Map<String,String> applicationParamsMap) throws ApiSysException{
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.putAll(systemParamsMap);
        //如果应用级参数不为空，则组合应用级参数
        if(applicationParamsMap != null){
            paramMap.putAll(applicationParamsMap);
        }
        String str = concatParams(paramMap);
        String basedUrl = genUrlPrefix(methodName) + "?" + str + systemParamsMap.get("appSecret");
        return basedUrl;
    }

    public static String genOnlyHasSysParamsAndSigUrl(String urlPrefix,Map<String,String> systemParamsMap,String sig){
        String str = concatParams(systemParamsMap);
        String basedUrl = urlPrefix+ "?" + str + "&sig=" + sig;
        return basedUrl;
    }

    public static String genNotOnlySysParamsUrlForGetRequest(String urlPrefix,Map<String,String> systemParamsMap,String sig,Map<String,String> otherParamsMap){
        systemParamsMap.putAll(otherParamsMap);
        String str = concatParams(systemParamsMap);
        String handledUrl = urlPrefix+ "?" + str + "&sig=" + sig;
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
            if(key.equals("appSecret")){
                continue;
            }
            String val = params2.get(key);
            str += "&" + key + "=" + val;
        }
        return str.replaceFirst("&", "");
    }
}
