package com.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.*;
import com.framework.service.BasicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by c on 2017-01-30.
 */
@Service("StoresBrandService")
public class StoresBrandService extends BasicService {
    /**
     * 饿了么绑定后对数据进行同步
     * @param cssb
     */
    @Transactional(rollbackForClassName={"RuntimeException","Exception"})
    public void elestoresBind(CdsStoresBrand cssb){
        String elem_restaurant_id  = cssb.getElem_restaurant_id();
        sqlDao.updateRecord("cds_stores.updateStoresBrand",elem_restaurant_id);
        //cssb.addUnParamFields("stores_brand_id");
        //cssb.addConditionField("stores_brand_id");
        sqlDao.updateRecord("cds_stores.updateStoresBrand2",cssb);
    }

    /**
     *保存平台商品套餐明细
     * @param list
     */
    @Transactional(rollbackForClassName={"RuntimeException","Exception"})
    public void saveGoodList(List<CdsErpGoodList> list,String good_id){
        if(list!=null&&list.size()>0){
            sqlDao.deleteRecord("cds_stores.deleteErpGoodList",good_id);
            for (int i=0;i<list.size();i++){
                CdsErpGoodList el= list.get(i);

//                if(el.getErp_good_id()!=null&&el.getErp_good_id()!=1){
//                    el.addUnParamFields("erp_good_id");
//                    el.addUnConditionFields("erp_good_id");
//                    sqlDao.updateRecord(el);
//                }else{
                    el.addUnParamFields("erp_good_id");
                    sqlDao.insertRecord(el);
//                }
            }
        }else{
            sqlDao.deleteRecord("cds_stores.deleteErpGoodList",good_id);
        }
    }

    /**
     *
     * @param csg (商品对象)
     * @param json(规格数据)
     * @param json2(erp数据)
     * @return
     */
    @Transactional(rollbackForClassName={"RuntimeException","Exception"})
    public String saveGood(CdsStoresGoodsList csg,String json,String json2 ){
        String isok = "0";
        JSONArray array = JSONArray.parseArray(json);
        JSONObject erpJo = JSONObject.parseObject(json2);
        csg.addParamFields();
        sqlDao.insertRecord(csg);
        isok ="1";
        if (csg!=null){
            int sgl_id = csg.getSgl_id();
            for (int i = 0; i < array.size(); i++) {
                CdsStandardList csd = JSONObject.parseObject( JSONObject.toJSONString( array.getJSONObject(i)),CdsStandardList.class);
                csd.setSgl_id(sgl_id);
                HashMap map = sqlDao.getRecord("cds_ptstores_manage.queryStandardId");
                int standard_id=1;
                if (map!=null&&map.size()>0){
                    String standard_ids = map.get("standard_id").toString();
                    if (!StringUtils.isEmpty(standard_ids)){
                        standard_id = Integer.valueOf(standard_ids)+1;
                    }
                }
                csd.setStandard_id(standard_id);
                if (StringUtils.isEmpty(csd.getStandard_name())){
                    csd.addUnParamFields("standard_name");
                }
                if(StringUtils.isEmpty(csd.getBox_count())){
                    csd.addUnParamFields("box_count");
                }
                if (StringUtils.isEmpty(csd.getBox_price())){
                    csd.addUnParamFields("box_price");
                }
                if(StringUtils.isEmpty(csd.getCurrent_price())){
                    csd.addUnParamFields("current_price");
                }
                csd.addParamFields();
                sqlDao.insertRecord(csd);
                isok ="1";
                JSONArray erpArray = getErp(erpJo,i,standard_id);
                if(erpArray!=null&&erpArray.size()>0){
                    for (int j = 0; j < erpArray.size(); j++) {
                        String count = JSONObject.parseObject(erpArray.get(j).toString()).getString("good_count");
                        String good_id = JSONObject.parseObject(erpArray.get(j).toString()).getString("good_id");
                        CdsStandardGoodList csdg = new CdsStandardGoodList();
                        if (!StringUtils.isEmpty(good_id)){
                            csdg.setGood_id(Integer.valueOf(good_id));
                        }
                        if (!StringUtils.isEmpty(count)){
                            csdg.setGood_count(Integer.valueOf(count));
                        }
                        csdg.setStandard_id(standard_id);
                        csdg.addUnParamFields("erp_good_id");
                        sqlDao.insertRecord(csdg);
                        isok ="1";
                    }
                }
            }
        }

        return isok;
    }

    /**
     * 保存erp
     * @param erpJo
     * @param i
     * @param standard_id
     */
    public JSONArray getErp(JSONObject  erpJo,int i,int standard_id){
        String isok = "0";
        String erpStr="";
        if (i==0){
            erpStr = erpJo.getString("a");
        }else if(i==1){
            erpStr = erpJo.getString("b");
        }else if(i==2){
            erpStr = erpJo.getString("c");
        }else if(i==3){
            erpStr = erpJo.getString("d");
        }else if(i==4){
            erpStr = erpJo.getString("e");
        }else if (i==5){
            erpStr = erpJo.getString("f");
        }
        JSONArray erpArray = JSONArray.parseArray(erpStr);

        return erpArray ;
    }


    @Transactional(rollbackForClassName={"RuntimeException","Exception"})
    public void deleteGood(String  sgl_id){
        if (!StringUtils.isEmpty(sgl_id)){
            CdsStoresGoodsList cc = new CdsStoresGoodsList();
            cc.setSgl_id(Integer.valueOf(sgl_id));
            sqlDao.deleteRecord("cds_ptstores_manage.deleteStandardGoodList",cc);
            sqlDao.deleteRecord("cds_ptstores_manage.deleteGood",cc);
            cc.addConditionField("sgl_id");
            sqlDao.deleteRecord(cc);
        }
    }


    /**
     * 要保存的商品对象、被复制的商品id
     * @param csc
     * @param sgl_idc
     */
    @Transactional(rollbackForClassName={"RuntimeException","Exception"})
    public void saveGoodsList(CdsStoresGoodsList csc,int sgl_idc){
        CdsStandardList cd = new CdsStandardList();
        //要添加商品的id
        int sgl_id = csc.getSgl_id();
        csc.addParamFields();
        //保存商品
        sqlDao.insertRecord(csc);
        cd.setSgl_id(sgl_idc);
        cd.addConditionField("sgl_id");
        //查出要批量复制的规格
        List<CdsStandardList> list =  sqlDao.getRecordList(cd);
        if (list!=null&&list.size()>0){
            for (int i = 0; i <list.size() ; i++) {
                //被复制的规格id
                int standard_idc = list.get(i).getStandard_id();
                cd = list.get(i);
                cd.setSgl_id(sgl_id);
                HashMap map = sqlDao.getRecord("cds_ptstores_manage.queryStandardId");
                int standard_id=1;
                if (map!=null&&map.size()>0){
                    String standard_ids = map.get("standard_id").toString();
                        if (!StringUtils.isEmpty(standard_ids)){
                        standard_id = Integer.valueOf(standard_ids)+1;
                    }
                }
                cd.setStandard_id(standard_id);
                cd.addParamFields();
                sqlDao.insertRecord(cd);
                CdsStandardGoodList csg = new CdsStandardGoodList();
                csg.setStandard_id(standard_idc);
                csg.addConditionField("standard_id");
                List<CdsStandardGoodList> lists = sqlDao.getRecordList(csg);
                if (lists!=null&&lists.size()>0){
                    for (int j = 0; j <lists.size() ; j++) {
                        csg = lists.get(j);
                        csg.setStandard_id(standard_id);
                        csg.addUnParamFields("erp_good_id");
                        sqlDao.insertRecord(csg);
                    }
                }
            }
        }
    }

    @Transactional(rollbackForClassName={"RuntimeException","Exception"})
    public void deleteStandard(CdsStandardList csc){
        sqlDao.deleteRecord("cds_ptstores_manage.deleteStandard",csc);
        csc.addConditionField("standard_id");
        csc.addUnParamFields("standard_id");
        sqlDao.deleteRecord(csc);
    }


}
