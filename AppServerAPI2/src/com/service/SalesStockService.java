package com.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsSalesRecord;
import com.framework.mapping.system.CdsStoresGoodsList;
import com.framework.service.BasicService;
import com.framework.system.SystemConfig;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.meituan.vo.FoodSkuStockParam;
import com.opensdk.meituan.vo.skuStockParam;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 分配库存
 *
 */
@Service("SalesStockService")
public class SalesStockService extends BasicService {




    /**
     * 跟据销售数量分配每个平台库存
     * @param orderInfo
     * @param good_id
     * @param count 购买的份数
     */
    public void salesCountAllot(CdsOrderInfo orderInfo,String good_id,int count){
        int stores_brand_id = orderInfo.getStores_brand_id();
        int stores_id = orderInfo.getStores_id();
        String order_no = orderInfo.getOrder_no();//平台（微信、美团、饿了么）
        Date time = orderInfo.getPay_time();
        //获取商铺包含商品明细
        CdsStoresGoodsList csg = new CdsStoresGoodsList();
        csg.setStores_brand_id(stores_brand_id);
        csg.setGood_id(Integer.valueOf(good_id));
        csg.setStores_id(stores_id);
        csg.addConditionFields("stores_brand_id,good_id,stores_id");
        csg = sqlDao.getRecord(csg);
        //保存商品销售记录
        saveSalesRecord(csg,time,order_no,count);
        if(csg!=null){
            int elem_count = csg.getElem_count();
            int mt_count = csg.getMt_count();
            int wx_count = csg.getWx_count();
            if("美团".equals(order_no)){
                mt_count = mt_count - count;
            }else if("饿了么".equals(order_no)){
                elem_count = elem_count - count;
            }else if("微信".equals(order_no)){
                wx_count = wx_count - count;
            }
            int all_count = csg.getAll_count()-count;
            //库存分配控制变量
            int i = 10;
            if(all_count>0){
                csg.setAll_count(all_count);
                if(elem_count<i||mt_count<i||wx_count==0){
                    //当饿了么库存或者美团库存少于i的时候，重新分配
                    wx_count = Integer.valueOf(Math.floor(all_count/10)+"");
                    all_count = all_count-wx_count;
                    if(all_count%2==0){
                        //能被整除
                        mt_count = all_count/2;
                        elem_count = mt_count;
                    }else{
                        //不能被整除
                        if("美团".equals(order_no)){
                            //向上取整
                            mt_count = Integer.valueOf(Math.ceil(all_count/2)+"");
                            //向下取整
                            elem_count = Integer.valueOf(Math.floor(all_count/2)+"");
                        }else{
                            //向下取整
                            mt_count = Integer.valueOf(Math.floor(all_count/2)+"");
                            //向上取整
                            elem_count = Integer.valueOf(Math.ceil(all_count/2)+"");
                        }
                    }
                }

            }else if(all_count<=0){
                csg.setAll_count(0);
                wx_count=0;
                mt_count=0;
                elem_count=0;
            }
            //平台同步数据
            csg.setMt_count(mt_count);
            csg.setWx_count(wx_count);
            csg.setElem_count(elem_count);
            csg.addUnParamFields("sgl_id");
            sqlDao.updateRecord(csg);
            //饿了么同步数据
            updateElemStock(csg.getFood_id(),elem_count);
            //美团同步数据
            updateMtStock(stores_brand_id+"",csg.getSgl_id()+"",mt_count);
        }


    }




//    /**
//     * 获取饿了么库存
//     * @param food_id
//     * @return
//     */
//    public int getElemeStock(String food_id){
//        int eStock = 0;
//        try {
//            String result = APIFactoryEleme.getFoodAPI().eleGoodQuery(SystemConfig.GetSystemParamEleme(), food_id);
//            JSONObject jo = JSONObject.parseObject(result);
//            if ("200".equals(jo.getString("code")) && jo.getString("message").equals("ok")) {
//                String data = jo.getString("data");
//                jo = JSONObject.parseObject(data);
//                data = jo.getString("food");
//                jo = JSONObject.parseObject(data);
//                String stock = jo.getString("stock");
//                if(!StringUtils.isEmpty(stock)){
//                    eStock = Integer.valueOf(stock);
//                }
//            }
//        } catch (ApiOpException e) {
//            e.printStackTrace();
//        } catch (ApiSysException e) {
//            e.printStackTrace();
//        }
//        return eStock;
//    }

    /**
     * 更新饿了么库存
     * @param food_id
     * @param stock
     */
    public void updateElemStock(String food_id,int stock){
        try {
            String result = APIFactoryEleme.getFoodAPI().eleUpdateFood(SystemConfig.GetSystemParamEleme(),"",food_id,"","","",stock+"","","");
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新美团库存
     * @param stores_brand_id
     * @param sgl_id
     * @param stock
     */
    public void updateMtStock(String stores_brand_id,String sgl_id,int stock){
        List<skuStockParam> skus = new ArrayList<>();
        skuStockParam skuStockParam = new skuStockParam();
        skuStockParam.setSku_id(sgl_id);
        skuStockParam.setStock(stock+"");
        skus.add(skuStockParam);
        List<FoodSkuStockParam> list = new ArrayList<FoodSkuStockParam>();
        FoodSkuStockParam foodSkuStockParam = new FoodSkuStockParam();
        foodSkuStockParam.setApp_food_code(sgl_id);
        foodSkuStockParam.setSkus(skus);
        list.add(foodSkuStockParam);
        try {
            String result2 = APIFactoryMeituan.getFoodAPI().updateFoodSkuStock(SystemConfig.GetSystemParamMeituan(),stores_brand_id,list);
        } catch (com.opensdk.meituan.exception.ApiOpException e) {
            e.printStackTrace();
        } catch (com.opensdk.meituan.exception.ApiSysException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 获取美团库存
//     * @param stores_brand_id
//     * @param mt_isband
//     * @return
//     */
//    public int getMtStock(int stores_brand_id,String mt_isband){
//        int mStock=0;
//        try {
//            String result = APIFactoryMeituan.getFoodAPI().foodGet(SystemConfig.GetSystemParamMeituan(),stores_brand_id+"",mt_isband);
//            JSONObject jo = JSONObject.parseObject(result);
//            result = jo.getString("skus");
//            JSONArray jos = JSONArray.parseArray(result);
//            if(jos!=null&&jos.size()>0){
//                String stock = jos.getJSONObject(0).getString("stock");
//                if(!StringUtils.isEmpty(stock)){
//                    mStock = Integer.valueOf(stock);
//                }
//            }
//        } catch (com.opensdk.meituan.exception.ApiOpException e) {
//            e.printStackTrace();
//        } catch (com.opensdk.meituan.exception.ApiSysException e) {
//            e.printStackTrace();
//        }
//        return mStock;
//    }

    /**
     * 保存销售记录
     * @param csg
     * @param time
     * @param order_no
     * @param count
     */
    public void saveSalesRecord(CdsStoresGoodsList csg ,Date time,String order_no,int count){
        CdsSalesRecord csr = new CdsSalesRecord();
        csr.setStores_id(csg.getStores_id());
        csr.setGood_id(csg.getGood_id());
        csr.setCount(count);
        csr.setPlatform(order_no);
        csr.setTime(time);
        csr.addUnParamFields("id");
        sqlDao.insertRecord(csr);
    }


}
