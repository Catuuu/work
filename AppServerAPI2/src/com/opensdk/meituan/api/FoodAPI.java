package com.opensdk.meituan.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensdk.meituan.constants.ErrorEnum;
import com.opensdk.meituan.constants.ParamRequiredEnum;
import com.opensdk.meituan.exception.ApiOpException;
import com.opensdk.meituan.exception.ApiSysException;
import com.opensdk.meituan.util.ConvertUtil;
import com.opensdk.meituan.util.StringUtil;
import com.opensdk.meituan.vo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenbin on 17/02/05.
 */
public class FoodAPI extends API{

    /**
     * 查询门店菜品列表
     * @param systemParam  系统参数
     * @param appPoiCode  门店code
     * @return
     */
    public List<FoodParam> foodList(SystemParamMeituan systemParam, String appPoiCode)
        throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodList, systemParam, appPoiCode);

        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodList);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
//        System.out.println(data);
        List<FoodParam> foodParams = new ArrayList<FoodParam>();
        try{
            JSONArray jsonArray = JSONArray.parseArray(data);
         for(int i=0; i<jsonArray.size(); i++){
             JSONObject fullFood = JSONObject.parseObject(jsonArray.get(i).toString());
             FoodParam foodParam = null;
             if(fullFood.get("skus") != null){
                 String skuStr = fullFood.get("skus").toString();
                 fullFood.remove("skus");
                 foodParam = JSONObject.parseObject(fullFood.toString(), FoodParam.class);
                 if(!StringUtil.isBlank(skuStr)){
                     List<FoodSkuParam> skus = JSONArray.parseArray(skuStr, FoodSkuParam.class);
                     foodParam.setSkus(skus);
                 }
             }else{
                 foodParam = JSONObject.parseObject(fullFood.toString(), FoodParam.class);
             }
             foodParams.add(foodParam);
         }
        }catch (Exception e){
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return foodParams;
    }

    /**
     * 查询门店菜品列表
     * @param systemParam  系统参数
     * @param appPoiCode  门店code
     * @param offset  偏移量
     * @param limit  查询个数
     * @return
     */
    public List<FoodParam> foodListByPage(SystemParamMeituan systemParam, String appPoiCode, int offset, int limit)
        throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodListByPage, systemParam, appPoiCode);

        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodListByPage);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<FoodParam> foodParams = new ArrayList<FoodParam>();
        try{
            JSONArray jsonArray = JSONArray.parseArray(data);
            for(int i=0; i<jsonArray.size(); i++){
                JSONObject fullFood = JSONObject.parseObject(jsonArray.get(i).toString());
                FoodParam foodParam = null;
                if(fullFood.get("skus") != null){
                    String skuStr = fullFood.get("skus").toString();
                    fullFood.remove("skus");
                    foodParam = JSONObject.parseObject(fullFood.toString(), FoodParam.class);
                    if(!StringUtil.isBlank(skuStr)){
                        List<FoodSkuParam> skus = JSONArray.parseArray(skuStr, FoodSkuParam.class);
                        foodParam.setSkus(skus);
                    }
                }else{
                    foodParam = JSONObject.parseObject(fullFood.toString(), FoodParam.class);
                }
                foodParams.add(foodParam);
            }
        }catch (Exception e){
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return foodParams;
    }

    /**
     * 更新菜品分类
     * @param systemParam  系统参数
     * @param appPoiCode  门店code
     * @param originCategoryName  原始的菜品分类
     * @param newCategoryName  新建的菜品分类
     * @param sequence  菜品排序序号
     * @return
     */
    public String foodCatUpdate(SystemParamMeituan systemParam, String appPoiCode,
                                String originCategoryName, String newCategoryName,
                                int sequence) throws ApiOpException,
                                                                    ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

//        beforeMethod(ParamRequiredEnum.FoodCatUpdate,systemParam, appPoiCode,newCategoryName);

        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        if (originCategoryName != null && !"".equals(originCategoryName) && !"null".equals(originCategoryName) && !"NULL".equals(originCategoryName)) {
            applicationParamsMap.put("category_name_origin", originCategoryName);
        }
        applicationParamsMap.put("category_name", newCategoryName);
        String sequence_str = String.valueOf(sequence);
        if (sequence_str != null && !"".equals(sequence_str) && !"null".equals(sequence_str) && !"NULL".equals(sequence_str)) {
            applicationParamsMap.put("sequence", sequence_str);
        }
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodCatUpdate);


        return requestApi(methodName,systemParam, applicationParamsMap);
    }

    /**
     * 获取门店下菜品分类
     * @param systemParam  系统参数
     * @param appPoiCode  门店code
     * @return
     */
    public List<FoodCatParam> foodCatList(SystemParamMeituan systemParam, String appPoiCode)
        throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodCatList, systemParam, appPoiCode);

        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<FoodCatParam> foodCatParams = null;
        try{
            foodCatParams = JSONArray.parseArray(data, FoodCatParam.class);
        }catch (Exception e){
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return foodCatParams;
    }


    /**
     * 删除菜品分类
     * @param systemParam  系统参数
     * @param appPoiCode  门店code
     * @param categoryName  要删除的菜品分类

     * @return
     */
    public String foodCatDelete(SystemParamMeituan systemParam, String appPoiCode,
                                String categoryName) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("category_name", categoryName);
        beforeMethod(ParamRequiredEnum.FoodCatDelete,systemParam, appPoiCode, categoryName);
        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 创建／更新菜品
     * @param systemParam  系统参数
     * @param foodParam  菜品信息
     * @return
     */
    public String foodSave(SystemParamMeituan systemParam, FoodParam foodParam) throws ApiOpException,
                                                                                        ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodSave, systemParam, foodParam);

        //组织应用级参数
        Map<String,String> applicationParamsMap = ConvertUtil.convertToMap(foodParam);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodSave);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 批量保存菜品(不包含sku信息)
     * @param systemParam  系统参数
     * @param foodParams  菜品信息
     * @return
     */
    public String foodBatchSave(SystemParamMeituan systemParam, String appPoiCode,
                                List<FoodParam> foodParams) throws ApiOpException,
                                                                          ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(foodParams));
        beforeMethod(ParamRequiredEnum.FoodBatchSave,systemParam,appPoiCode,JSONObject.toJSONString(foodParams));

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 创建／更新菜品(支持菜品多规格,不含删除逻辑)
     * @param systemParam  系统参数
     * @param foodParam  菜品信息
     * @return
     */
    public String foodInitData(SystemParamMeituan systemParam , FoodParam foodParam)
        throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodInitData, systemParam, foodParam);

        //组织应用级参数
        Map<String,String> applicationParamsMap = ConvertUtil.convertToMap(foodParam);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodInitData);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }


    /**
     * 批量保存菜品(可以添加sku信息)
     * @param systemParam  系统参数
     * @param foodParams  菜品信息
     * @return
     */
    public String foodBatchInitData(SystemParamMeituan systemParam, String appPoiCode,
                                    List<FoodParam> foodParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(foodParams));

        beforeMethod(ParamRequiredEnum.FoodBatchInitData,systemParam, appPoiCode, JSONObject.toJSONString(foodParams));


        return requestApi(methodName, systemParam, applicationParamsMap);
    }


    /**
     * 删除菜品
     * @param systemParam  系统参数
     * @param appPoiCode  门店code
     * @param appFoodCode  要删除的菜品code

     * @return
     */
    public String foodDelete(SystemParamMeituan systemParam, String appPoiCode,
                             String appFoodCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodDelete,systemParam, appPoiCode, appFoodCode);

        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_code", appFoodCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodDelete);


        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 保存菜品sku
     * @param systemParam  系统参数
     * @param foodSkuParams  菜品sku信息
     * @return
     */
    public String foodSkuSave(SystemParamMeituan systemParam, String app_poi_code, String app_food_code, List<FoodSkuParam> foodSkuParams)
        throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(null,systemParam, app_poi_code, JSON.toJSONString(foodSkuParams));

        //组织应用级参数
//        Map<String,String> applicationParamsMap = ConvertUtil.convertToMap(foodSkuParams);
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code",app_poi_code);
        applicationParamsMap.put("app_food_code",app_food_code);
        applicationParamsMap.put("skus",JSON.toJSONString(foodSkuParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodSkuSave);


        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 删除菜品sku
     * @param systemParam  系统参数
     * @param appPoiCode  门店code
     * @param appFoodCode  要删除的菜品code
     * @param skuId  要删除的菜品skuId

     * @return
     */
    public String foodSkuDelete(SystemParamMeituan systemParam, String appPoiCode, String appFoodCode,
                                String skuId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(null,systemParam, appPoiCode, appFoodCode,skuId);

        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_code", appFoodCode);
        applicationParamsMap.put("sku_id", skuId);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodSkuDelete);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 更新菜品sku价格
     * @param systemParam  系统参数
     * @param foodSkuPriceParams  菜品sku价格信息
     * @return
     */
    public String updateFoodSkuPrice(SystemParamMeituan systemParam, String appPoiCode,
                                     List<FoodSkuPriceParam> foodSkuPriceParams)
        throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(null,systemParam, appPoiCode,JSONObject.toJSONString(foodSkuPriceParams));

        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(foodSkuPriceParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.UpdateFoodSkuPrice);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 更新菜品sku库存
     * @param systemParam  系统参数
     * @param foodSkuStockParams  菜品sku库存信息
     * @return
     */
    public String updateFoodSkuStock(SystemParamMeituan systemParam, String appPoiCode,
                                     List<FoodSkuStockParam> foodSkuStockParams)
        throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(null,systemParam, appPoiCode, JSONObject.toJSONString(foodSkuStockParams));

        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(foodSkuStockParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.UpdateFoodSkuStock);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 增加菜品库存
     * @param systemParam
     * @param appPoiCode
     * @param foodSkuStockParams
     * @return
     */
    public String incFoodSkuStock(SystemParamMeituan systemParam, String appPoiCode,
                                  List<FoodSkuStockParam> foodSkuStockParams)
    throws ApiOpException, ApiSysException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(null, systemParam, appPoiCode, JSONObject.toJSONString(foodSkuStockParams));

        //组织应用级参数
        Map<String, String> applicationParamMap = new HashMap<String, String>();
        applicationParamMap.put("app_poi_code", appPoiCode);
        applicationParamMap.put("food_data", JSON.toJSONString(foodSkuStockParams));
        beforeMethod(systemParam, applicationParamMap, ParamRequiredEnum.incFoodSkuStock);

        return requestApi(methodName, systemParam, applicationParamMap);
    }

    /**
     * 减少菜品库存
     * @param systemParam
     * @param appPoiCode
     * @param foodSkuStockParams
     * @return
     */
    public String descFoodSkuStock(SystemParamMeituan systemParam, String appPoiCode,
                                   List<FoodSkuStockParam> foodSkuStockParams)
            throws ApiOpException, ApiSysException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(null, systemParam, appPoiCode, JSONObject.toJSONString(foodSkuStockParams));

        //组织应用级参数
        Map<String, String> applicationParamMap = new HashMap<String, String>();
        applicationParamMap.put("app_poi_code", appPoiCode);
        applicationParamMap.put("food_data", JSON.toJSONString(foodSkuStockParams));
        beforeMethod(systemParam, applicationParamMap, ParamRequiredEnum.descFoodSkuStock);

        return requestApi(methodName, systemParam, applicationParamMap);
    }

    /**
     * 查询食物详情
     * @param systemParam
     * @param appPoiCode
     * @param appFoodCode
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String foodGet(SystemParamMeituan systemParam, String appPoiCode,
                          String appFoodCode)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(null,systemParam, appPoiCode,appFoodCode);

        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_code", appFoodCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodGet);
        return requestApi(methodName, systemParam, applicationParamsMap);
    }

}

