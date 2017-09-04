package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.*;
import com.framework.util.WebUtil;
import com.service.StoresBrandService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.framework.annotation.CheckType.CHECK_LOGIN;

/**
 * Created by c on 2017-02-02.
 */
@Controller
@RequestMapping("StoresPrinter")
public class StoresPrinterController extends BasicController {

    @Resource(name = "StoresBrandService")
    protected StoresBrandService storesbrandservice;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResourceMethod(name = "门店打印机管理", check = CHECK_LOGIN)
    public String index() throws Exception {
        CdsStores cs = new CdsStores();
        List<CdsStores> cslist = sqlDao.getRecordList(cs);
        WebUtil.getRequest().setAttribute("storeslist", cslist);
        return "storesPrinter/index";
    }



    @RequestMapping(value = "getChuFanMeal", method = RequestMethod.POST)
    @ResourceMethod(name = "门店管理列表", check = CHECK_LOGIN)
    @ResponseBody
    public  List<JSONObject> getChuFanMeal(@RequestParam HashMap formInfo) throws Exception {
        CdsChufanMeal ccf = new CdsChufanMeal();
        String stores_id = formInfo.get("stores_id").toString();
        List<CdsChufanMeal> list = new ArrayList<CdsChufanMeal>();
        List<JSONObject> listjo = new ArrayList<JSONObject>();
        if(!StringUtils.isEmpty(stores_id)){
            ccf.setStores_id(Integer.valueOf(stores_id));
            ccf.addConditionField("stores_id");
             list = sqlDao.getRecordList(ccf);
             if(list!=null&&list.size()>0){
                 for (int i = 0; i < list.size(); i++) {
                     JSONObject jo = new JSONObject();
                     jo.put("cm_id",list.get(i).getCm_id());
                     jo.put("cm_name",list.get(i).getCm_name());
                     jo.put("cook_group",list.get(i).getCook_group());
                     jo.put("ct_user",list.get(i).getCt_user());
                     jo.put("jardiniere_group",list.get(i).getJardiniere_group());
                     jo.put("wash_group",list.get(i).getWash_group());
                     jo.put("stores_id",list.get(i).getStores_id());
                     jo.put("status",list.get(i).getStatus());
                     jo.put("ct_num",list.get(i).getCt_num());
                     jo.put("ct_password",list.get(i).getCt_password());
                     listjo.add(jo);
                 }
             }
        }
        return listjo;
//        queryAndResponsePage("cds_printer.getChuFanMeal", formInfo);
    }

    @RequestMapping(value = "getChuFanTask", method = RequestMethod.POST)
    @ResourceMethod(name = "门店管理列表", check = CHECK_LOGIN)
    @ResponseBody
    public  JsonMessage getChuFanTask(@RequestParam String stores_id) throws Exception {
        CdsChufanTask ccf = new CdsChufanTask();
        List<CdsChufanTask> list = new ArrayList<CdsChufanTask>();
        List<JSONObject> listjo = new ArrayList<JSONObject>();
        JsonMessage jm = new JsonMessage();
        if(!StringUtils.isEmpty(stores_id)){
            ccf.setStores_id(Integer.valueOf(stores_id));
            ccf.addConditionField("stores_id");
            list = sqlDao.getRecordList(ccf);
            if(list!=null&&list.size()>0){
                jm.setStatus("1");
                for (int i = 0; i < list.size(); i++) {
                    JSONObject jo = new JSONObject();
                    jo.put("es_id",list.get(i).getEs_id());
                    jo.put("ct_name",list.get(i).getCt_name());
                    jo.put("ct_user",list.get(i).getCt_user());
                    jo.put("ct_password",list.get(i).getCt_password());
                    jo.put("print_ip",list.get(i).getPrint_ip());
                    jo.put("status",list.get(i).getStatus());
                    listjo.add(jo);
                }
            }
            jm.setObj(listjo.get(0));
        }
        return jm;
    }

    @RequestMapping(value = "getChuFanGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "门店管理列表", check = CHECK_LOGIN)
    @ResponseBody
    public void getChuFanGoods(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_printer.getChuFanGoods", formInfo);
    }


    @RequestMapping(value = "setStatus", method = RequestMethod.POST)
    @ResourceMethod(name = "设置", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage setStatus(String es_id, String status) {
        CdsChufanTask cct = new CdsChufanTask();
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        if (!StringUtils.isEmpty(es_id)) {
            cct.setEs_id(Integer.valueOf(es_id));
            cct.addConditionField("es_id");
            cct = sqlDao.getRecord(cct);
            if(!StringUtils.isEmpty(status)){
                cct.setStatus(Integer.valueOf(status));
                cct.addUnParamFields("es_id");
                cct.addConditionField("es_id");
                sqlDao.updateRecord(cct);
                jm.setStatus("1");
            }
        }
        return jm;
    }

    @RequestMapping(value = "toggle", method = RequestMethod.POST)
    @ResourceMethod(name = "设置", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage toggle(String cm_id, String status) {
        CdsChufanMeal ccf = new CdsChufanMeal();
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        if (!StringUtils.isEmpty(cm_id)) {
            ccf.setCm_id(Integer.valueOf(cm_id));
            ccf.addConditionField("cm_id");
            ccf = sqlDao.getRecord(ccf);
            if(!StringUtils.isEmpty(status)){
                ccf.setStatus(Integer.valueOf(status));
                ccf.addUnParamFields("cm_id");
                ccf.addConditionField("cm_id");
                sqlDao.updateRecord(ccf);
                jm.setStatus("1");
            }
        }
        return jm;
    }

    @RequestMapping(value = "deleteGood", method = RequestMethod.POST)
    @ResourceMethod(name = "设置", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage deleteGood(String cmg_id) {
        CdsChufanMealGoods ccm = new CdsChufanMealGoods();
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        if (!StringUtils.isEmpty(cmg_id)) {
            ccm.setCmg_id(Integer.valueOf(cmg_id));
            ccm.addConditionField("cmg_id");
            ccm = sqlDao.getRecord(ccm);
            sqlDao.deleteRecord(ccm);
            jm.setStatus("1");
        }
        return jm;
    }


    @RequestMapping(value = "getErpGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "门店管理列表", check = CHECK_LOGIN)
    @ResponseBody
    public void getErpGoods(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_printer.getErpGoods", formInfo);
    }

//    @RequestMapping(value = "checkGood", method = RequestMethod.POST)
//    @ResourceMethod(name = "设置", check = CHECK_LOGIN)
//    @ResponseBody
//    public JsonMessage checkGood(String good_id,String stores_id) {
//        CdsChufanMealGoods ccm = new CdsChufanMealGoods();
//        JsonMessage jm = new JsonMessage();
//        jm.setStatus("0");
//        if (!StringUtils.isEmpty(cmg_id)) {
//            ccm.setCmg_id(Integer.valueOf(cmg_id));
//            ccm.addConditionField("cmg_id");
//            ccm = sqlDao.getRecord(ccm);
//            sqlDao.deleteRecord(ccm);
//            jm.setStatus("1");
//        }
//        return jm;
//    }


    @RequestMapping(value = "addErpGood", method = RequestMethod.POST)
    @ResourceMethod(name = "添加", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage addErpGood(String cm_id,String good_id) {
        CdsChufanMealGoods ccm = new CdsChufanMealGoods();
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        if (!StringUtils.isEmpty(cm_id)&&!StringUtils.isEmpty(good_id)) {
            ccm.setCm_id(Integer.valueOf(cm_id));
            ccm.setGood_id(Integer.valueOf(good_id));
            ccm.addUnParamFields("cmg_id");
            sqlDao.insertRecord(ccm);
            jm.setStatus("1");
        }
        return jm;
    }

    @RequestMapping(value = "addTask", method = RequestMethod.POST)
    @ResourceMethod(name = "添加", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage addTask(CdsChufanTask ccf) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        if(ccf!=null&&ccf.getEs_id()!=null){
            //修改
            ccf.addUnParamFields("es_id");
            ccf.addConditionField("es_id");
            sqlDao.updateRecord(ccf);
            jm.setStatus("1");
        }else{
            //新增
            ccf.addUnParamFields("es_id");
            sqlDao.insertRecord(ccf);
            jm.setStatus("1");
        }
        return jm;
    }

    @RequestMapping(value = "addMeal", method = RequestMethod.POST)
    @ResourceMethod(name = "添加", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage addMeal(CdsChufanMeal ccm,String opt) {
        JsonMessage jm = new JsonMessage();
        jm.setStatus("0");
        if("add".equals(opt)){
            //新增
            ccm.addUnParamFields("cm_id");
            sqlDao.insertRecord(ccm);
            jm.setStatus("1");
        }else{
            //修改
            ccm.addUnParamFields("cm_id");
            ccm.addConditionField("cm_id");
            sqlDao.updateRecord(ccm);
            jm.setStatus("1");
        }
        return jm;
    }





}
