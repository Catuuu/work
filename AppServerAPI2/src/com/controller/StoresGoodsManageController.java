package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.system.CdsClass;
import com.framework.mapping.system.CdsStoresBrand;
import com.framework.mapping.system.CdsStoresClass;
import com.framework.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.framework.annotation.CheckType.CHECK_LOGIN;

/**
 * Created by c on 2017-02-02.
 */
@Controller
@RequestMapping("StoresGoodsManage")
public class StoresGoodsManageController extends BasicController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResourceMethod(name = "门店管理", check = CHECK_LOGIN)
    public String index() throws Exception {
        int stores_id = WebUtil.getUser().getStores_id();
        int cds_stores_brand_id = getStoresBrandId(stores_id, 1);
        int sxg_stores_brand_id = getStoresBrandId(stores_id, 2);
        List<JSONObject> cds_list = getClassList(cds_stores_brand_id);
        List<JSONObject> sxg_list = getClassList(sxg_stores_brand_id);
        if (cds_list != null && cds_list.size() > 0) {
            WebUtil.getRequest().setAttribute("cds_list", cds_list);
        }
        if (sxg_list != null && sxg_list.size() > 0) {
            WebUtil.getRequest().setAttribute("sxg_list", sxg_list);
        }
        WebUtil.getRequest().setAttribute("stores_id", stores_id);
        return "storesGoodsManager/index";
    }

    /**
     * 根据门店id、品牌id返回stores_brand_id
     *
     * @param stores_id
     * @param brand_id
     * @return
     */
    public int getStoresBrandId(int stores_id, int brand_id) {
        CdsStoresBrand csb = new CdsStoresBrand();
        csb.setBrand_id(brand_id);
        csb.setStores_id(stores_id);
        csb.addConditionFields("brand_id,stores_id");
        csb = sqlDao.getRecord(csb);
        int stores_brand_id = 0;
        if (csb != null) {
            stores_brand_id = csb.getStores_brand_id();
        }
        return stores_brand_id;
    }

    /**
     * 根据stores_brand_id返回类别集合
     *
     * @param stores_brand_id
     * @return List
     */
    public List<JSONObject> getClassList(int stores_brand_id) {
        CdsStoresClass csc = new CdsStoresClass();
        csc.setStores_brand_id(stores_brand_id);
        csc.addConditionField("stores_brand_id");
        List<CdsStoresClass> list = sqlDao.getRecordList(csc);
        List<JSONObject> listJo = new ArrayList<JSONObject>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                JSONObject jo = new JSONObject();
                jo.put("class_id", list.get(i).getClass_id());
                jo.put("class_name", getClassName(list.get(i).getClass_id()));
                listJo.add(jo);
            }

        }
        return listJo;
    }

    /**
     * 根据class_id获取class_name
     *
     * @param class_id
     * @return
     */
    public String getClassName(int class_id) {
        CdsClass cc = new CdsClass();
        cc.setClass_id(class_id);
        cc.addConditionField("class_id");
        cc = sqlDao.getRecord(cc);
        return cc.getClass_name();
    }

    @RequestMapping(value = "getStoresList", method = RequestMethod.POST)
    @ResourceMethod(name = "门店管理列表", check = CHECK_LOGIN)
    @ResponseBody
    public void getStoresList(@RequestParam HashMap formInfo) throws Exception {

        queryAndResponsePage("cds_stores_manage.getPageRecord", formInfo);
    }


    public static void main(String[] args) {


    }


}
