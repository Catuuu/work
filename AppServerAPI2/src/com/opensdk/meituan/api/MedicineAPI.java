package com.opensdk.meituan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensdk.meituan.exception.ApiOpException;
import com.opensdk.meituan.exception.ApiSysException;
import com.opensdk.meituan.vo.MedicineCatParam;
import com.opensdk.meituan.vo.MedicineParam;
import com.opensdk.meituan.vo.SystemParamMeituan;
import com.opensdk.meituan.constants.ParamRequiredEnum;
import com.opensdk.meituan.util.ConvertUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenbin on 17/02/05.
 */
public class MedicineAPI extends API {

    /**
     *  创建药品分类
     *  @param systemParam 系统参数, 必填
     *  @param medicineCatParam 药品参数, 必填
     */
    public String medicineCatSave(SystemParamMeituan systemParam, MedicineCatParam medicineCatParam)
                                         throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.MedicineCatSave,systemParam, medicineCatParam);

        //组织应用级参数
        Map<String,String> applicationParamsMap = ConvertUtil.convertToMap(medicineCatParam);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.MedicineCatSave);

        return requestApi(methodName,systemParam,applicationParamsMap);
    }

    /**
     *  更新药品分类
     *  @param systemParam 系统参数, 必填
     *  medicineCatParam.appPoiCode 门店code, 必填
     *  medicineCatParam.categoryCode 新建药品种类code, 必填
     *  medicineCatParam.categoryName 新建药品种类名称, 非必填
     *  medicineCatParam.sequence 药品分类排序, 非必填
     */
    public String medicineCatUpdate(SystemParamMeituan systemParam, MedicineCatParam medicineCatParam)
                                           throws ApiOpException, ApiSysException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.MedicineCatUpdate,systemParam, medicineCatParam);

        //组织应用级参数
        Map<String, String> applicationParamsMap = ConvertUtil.convertToMap(medicineCatParam);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.MedicineCatUpdate);

        return requestApi(methodName,systemParam,applicationParamsMap);
    }

    /**
     *  删除药品分类
     *  @param systemParam 系统参数, 必填
     *  @param appPoiCode 门店code, 必填
     *  @param categoryCode 要删除的药品分类code, 必填
     */
    public String medicineCatDelete(SystemParamMeituan systemParam, String appPoiCode, String categoryCode)
                                           throws ApiOpException, ApiSysException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(null,systemParam, appPoiCode, categoryCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("category_code", categoryCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.MedicineCatDelete);

        return requestApi(methodName,systemParam,applicationParamsMap);
    }

    /**
     *  查询药品分类
     *  @param systemParam 系统参数, 必填
     *  @param appPoiCode 门店code, 必填
     */
    public List<MedicineCatParam> medicineCatList(SystemParamMeituan systemParam, String appPoiCode)
                                         throws ApiOpException, ApiSysException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.MedicineCatList, systemParam, appPoiCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);


        String data = requestApi(methodName, systemParam, applicationParamsMap);

        List<MedicineCatParam> medicineCatParams = JSONArray.parseArray(data, MedicineCatParam.class);

        return medicineCatParams;
    }

    /**
     *  创建全新药品(非批量)
     *  @param systemParam 系统参数, 必填
     *  @param medicineParam 药品参数, 必填
     */
    public String medicineSave(SystemParamMeituan systemParam, MedicineParam medicineParam)
                                      throws ApiOpException, ApiSysException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.MedicineSave,systemParam, medicineParam);

        //组织应用级参数
        Map<String,String> applicationParamsMap = ConvertUtil.convertToMap(medicineParam);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.MedicineSave);

        return requestApi(methodName,systemParam,applicationParamsMap);
    }

    /**
     *  更新已有药品(非批量)
     *  @param systemParam 系统参数, 必填
     *  @param medicineParam 药品参数, 必填
     */
    public String medicineUpdate(SystemParamMeituan systemParam, MedicineParam medicineParam)
            throws ApiOpException, ApiSysException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.MedicineUpdate,systemParam, medicineParam.getApp_poi_code(),medicineParam.getApp_medicine_code());

        //组织应用级参数
        Map<String,String> applicationParamsMap = ConvertUtil.convertToMap(medicineParam);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.MedicineUpdate);

        return requestApi(methodName,systemParam,applicationParamsMap);
    }

    /**
     *  批量新建药品
     *  @param systemParam 系统参数, 必填
     *  @param appPoiCode 门店code, 必填
     *  @param medicineParams 药品参数, 必填
     */
    public String medicineBatchsave(SystemParamMeituan systemParam, String appPoiCode, List<MedicineParam> medicineParams)
                                           throws ApiOpException, ApiSysException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.MedicineBatchSave,systemParam, appPoiCode, JSONObject.toJSONString(medicineParams));

        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("medicine_data", JSONObject.toJSONString(medicineParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.MedicineBatchSave);

        return requestApi(methodName,systemParam,applicationParamsMap);
    }

    /**
     *  批量更新药品
     *  @param systemParam 系统参数, 必填
     *  @param appPoiCode 门店code, 必填
     *  @param medicineParams 药品参数, 必填
     */
    public String medicineBatchupdate(SystemParamMeituan systemParam, String appPoiCode, List<MedicineParam> medicineParams)
            throws ApiOpException, ApiSysException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.MedicineBatchUpdate,systemParam, appPoiCode, JSONObject.toJSONString(medicineParams));

        //组织应用级参数
        Map<String,String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("medicine_data", JSONObject.toJSONString(medicineParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.MedicineBatchUpdate);

        return requestApi(methodName,systemParam,applicationParamsMap);
    }


    /**
     *  删除药品
     *  @param systemParam 系统参数, 必填
     *  @param appPoiCode 门店code, 必填
     *  @param appMedicineCode 药品code, 必填
     */
    public String medicineDelete(SystemParamMeituan systemParam, String appPoiCode, String appMedicineCode)
            throws ApiOpException, ApiSysException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.MedicineDelete,systemParam, appPoiCode,appMedicineCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_medicine_code", appMedicineCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.MedicineDelete);


        return requestApi(methodName,systemParam,applicationParamsMap);
    }

    /**
     *  查看药品列表
     *  @param systemParam 系统参数, 必填
     *  @param appPoiCode 门店code, 必填
     */
    public List<MedicineParam> medicineList(SystemParamMeituan systemParam, String appPoiCode)
            throws ApiOpException, ApiSysException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.MedicineList,systemParam, appPoiCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);


        String data = requestApi(methodName, systemParam, applicationParamsMap);

        List<MedicineParam> medicineParams = JSONArray.parseArray(data, MedicineParam.class);

        return medicineParams;
    }

    /**
     *  查看药品列表
     *  @param systemParam 系统参数, 必填
     *  @param appPoiCode 门店code, 必填
     *  @param medicine_data 药品数据, 必填
     */
    public String medicineStock(SystemParamMeituan systemParam, String appPoiCode, String medicine_data)
            throws ApiOpException, ApiSysException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.MedicineStock,systemParam, appPoiCode, medicine_data);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<String, String>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("medicine_data", medicine_data);

        return requestApi(methodName,systemParam,applicationParamsMap);
    }
}
