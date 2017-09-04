package com.opensdk.eleme.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensdk.eleme.constants.ErrorEnum;
import com.opensdk.eleme.constants.ParamRequiredEnum;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.util.ConvertUtil;
import com.opensdk.eleme.util.StringUtil;
import com.opensdk.eleme.vo.*;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
    public List<FoodParam> foodList(SystemParamEleme systemParam, String appPoiCode)
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
    public List<FoodParam> foodListByPage(SystemParamEleme systemParam, String appPoiCode, int offset, int limit)
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
    public String foodCatUpdate(SystemParamEleme systemParam, String appPoiCode,
                                String originCategoryName, String newCategoryName,
                                int sequence) throws ApiOpException,
                                                                    ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodCatUpdate,systemParam, appPoiCode,newCategoryName);

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
    public List<FoodCatParam> foodCatList(SystemParamEleme systemParam, String appPoiCode)
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
    public String foodCatDelete(SystemParamEleme systemParam, String appPoiCode,
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
    public String foodSave(SystemParamEleme systemParam, FoodParam foodParam) throws ApiOpException,
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
    public String foodBatchSave(SystemParamEleme systemParam, String appPoiCode,
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
    public String foodInitData(SystemParamEleme systemParam , FoodParam foodParam)
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
    public String foodBatchInitData(SystemParamEleme systemParam, String appPoiCode,
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
    public String foodDelete(SystemParamEleme systemParam, String appPoiCode,
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
    public String foodSkuSave(SystemParamEleme systemParam, String app_poi_code, String app_food_code, List<FoodSkuParam> foodSkuParams)
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
    public String foodSkuDelete(SystemParamEleme systemParam, String appPoiCode, String appFoodCode,
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
    public String updateFoodSkuPrice(SystemParamEleme systemParam, String appPoiCode,
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
    public String updateFoodSkuStock(SystemParamEleme systemParam, String appPoiCode,
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
    public String incFoodSkuStock(SystemParamEleme systemParam, String appPoiCode,
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
    public String descFoodSkuStock(SystemParamEleme systemParam, String appPoiCode,
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
     * 饿了么食物分类绑定
     * @param systemParam
     * @param restaurant_id     饿了么绑定id
     * @param name
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String eleGoodClassBind(SystemParamEleme systemParam, String restaurant_id,String name)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("restaurant_id", restaurant_id);
        applicationParamsMap.put("name", name);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.EleGoodClassBind);
        return requestApiStr(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 饿了么食物分类更新
     * @param systemParam
     * @param food_category_id
     * @param name
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String eleGoodClassBindUpdate(SystemParamEleme systemParam, String food_category_id,String name)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("food_category_id", food_category_id);
        applicationParamsMap.put("name", name);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.EleGoodClassBindUpdate);
        return requestApiStr(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 饿了么食物分类删除
     * @param systemParam
     * @param food_category_id
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String eleGoodClassBindDelete(SystemParamEleme systemParam, String food_category_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("food_category_id", food_category_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.EleGoodClassBindDelete);
        return requestApiStr(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 饿了么食物分类查询
     * @param systemParam
     * @param food_category_id
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String eleGoodClassBindQuery(SystemParamEleme systemParam, String food_category_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("food_category_id", food_category_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.EleGoodClassBindQuery);
        return requestApiStr(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 饿了么门店下食物分类查询
     * @param systemParam
     * @param restaurant_id
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String eleStoresGoodClassQuery(SystemParamEleme systemParam, String restaurant_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("restaurant_id", restaurant_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.EleStoresGoodClassQuery);
        return requestApiStr(methodName, systemParam, applicationParamsMap);
    }


    /**
     * 饿了么门店分类下食物查询
     * @param systemParam
     * @param food_id
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String eleGoodQuery(SystemParamEleme systemParam, String food_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("food_id", food_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.EleGoodQuery);
        return requestApiStr(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 查询分类食物列表
     * @param systemParam
     * @param food_category_id
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String eleClassGoodsQuery(SystemParamEleme systemParam, String food_category_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("food_category_id", food_category_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.EleClassGoodsQuery);
        return requestApiStr(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 通过url上传图片
     * @param systemParam
     * @param image_url
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String eleUploadByUrl(SystemParamEleme systemParam, String image_url)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("image_url", image_url);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.EleUploadByUrl);
        return requestApiStr(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 保存食物
     * @param systemParam
     * @param
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String eleSaveFood(SystemParamEleme systemParam, String food_category_id,String name,String price,String description,String max_stock,String stock,String image_hash)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("food_category_id", food_category_id);
        applicationParamsMap.put("name", name);
        applicationParamsMap.put("price", price);
        applicationParamsMap.put("description", description);
        applicationParamsMap.put("max_stock", max_stock);
        applicationParamsMap.put("stock", stock);
        if(!StringUtils.isEmpty(image_hash)){
            applicationParamsMap.put("image_hash", image_hash);
        }
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.EleSaveFood);
        return requestApiStr(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 跟新食物
     * @param systemParam
     * @param
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String eleUpdateFood(SystemParamEleme systemParam, String food_category_id,String food_id,String name,String price,String description,String stock,String image_hash,String packing_fee)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map applicationParamsMap = new HashMap();
        if(!StringUtils.isEmpty(food_category_id)){
            applicationParamsMap.put("food_category_id", food_category_id);
        }
        if(!StringUtils.isEmpty(name)){
            applicationParamsMap.put("name", name);
        }
        if(!StringUtils.isEmpty(price)){
            applicationParamsMap.put("price", price);
        }
        if(!StringUtils.isEmpty(description)){
            applicationParamsMap.put("description", description);
        }
        if(!StringUtils.isEmpty(stock)){
            applicationParamsMap.put("stock", stock);
        }
        if(!StringUtils.isEmpty(image_hash)){
            applicationParamsMap.put("image_hash", image_hash);
        }
        if(!StringUtils.isEmpty(packing_fee)){
            applicationParamsMap.put("packing_fee", packing_fee);
        }
        applicationParamsMap.put("food_id", food_id);
//        JSONObject jo  = new JSONObject();
//        JSONObject jo2 =  new JSONObject();
//        JSONArray list = new JSONArray();
//        jo2.put("spec_id","");
//        jo2.put("stock","");
//        jo2.put("price","");
//        list.add(jo2);
//        jo.put("specs",list);
//        String str = list.toJSONString();
//        applicationParamsMap.put("specs", str);
//        applicationParamsMap.put("on_shelf", on_shelf);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.EleUpdateFood);
        return requestApiStr(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 删除食物
     * @param systemParam
     * @param food_id
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String eleDeleteFood(SystemParamEleme systemParam, String food_id)
            throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("food_id", food_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.EleDeleteFood);
        return requestApiStr(methodName, systemParam, applicationParamsMap);
    }

}

