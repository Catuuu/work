package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.*;
import com.framework.util.DateUtil;
import com.framework.util.StringAlphas;
import com.framework.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import static com.framework.annotation.CheckType.CHECK_LOGIN;

/**
 * Created by c on 2017-02-02.
 */
@Controller
@RequestMapping("MsGoods")
public class ErpGoodsController extends BasicController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResourceMethod(name = "erp界面跳转", check = CHECK_LOGIN)
    public String index() throws Exception {
        CdsClassType cct = new CdsClassType();
        CdsBrand cb = new CdsBrand();
        List<CdsBrand> classTypeList =  sqlDao.getRecordList(cb);
        WebUtil.getRequest().setAttribute("classtypelist", classTypeList);
        return "msgoods/index";
    }

    @RequestMapping(value = "GetErpGoodsLists", method = RequestMethod.POST)
    @ResourceMethod(name = "erp菜品信息管理列表", check = CHECK_LOGIN)
    @ResponseBody
    public void GetErpGoodsLists(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_stores.getMsGoodsPageRecord", formInfo);
    }

    @RequestMapping(value = "updateErp", method = RequestMethod.POST)
    @ResourceMethod(name = "保存品牌信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage updateErp(String good_id,String opt) {
        CdsMsGoods cmg = new CdsMsGoods();
        if(!StringUtils.isEmpty(good_id)){
            cmg.setGood_id(Integer.valueOf(good_id));
            cmg.addConditionField("good_id");
            cmg = sqlDao.getRecord(cmg);
            if (opt.equals("bc")) {
                //是否备餐
                if(cmg.getIsback()==1){
                    cmg.setIsback(0);
                }else{
                    cmg.setIsback(1);
                }
                cmg.addParamField("isback");
            } else {
                //上下架
                if(cmg.getStatus()==1){
                    cmg.setStatus(2);
                }else{
                    cmg.setStatus(1);
                }
                cmg.addParamField("status");
            }
            cmg.addConditionField("good_id");
            sqlDao.updateRecord(cmg);
        }
        return new JsonMessage(1);
    }

    @RequestMapping(value = "saveMsGoods", method = RequestMethod.POST)
    @ResourceMethod(name = "保存Erp菜品信息", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveMsGoods(@RequestParam HashMap hashMap, String opt) {
        Iterator it = hashMap.entrySet().iterator();
        CdsMsGoods cmg = new CdsMsGoods();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if (!StringUtils.isEmpty(value)) {
                if ("good_id".equals(key)) {
                    cmg.setGood_id(Integer.valueOf(value));
                }
                if ("class_id".equals(key)) {
                    cmg.setClass_id(Integer.valueOf(value));
                }
//                if ("good_num".equals(key)) {
//                    cmg.setGood_num(value);
//                }
                if ("good_name".equals(key)) {
                    cmg.setGood_name(value);
                    String pinxin = StringAlphas.String2Alpha(value);
                    cmg.setPinxin(pinxin);
                    cmg.setUptime(DateUtil.getToday());
                }
//                if ("pinxin".equals(key)) {
//                    cmg.setPinxin(value);
//                }
//                if ("good_key".equals(key)) {
//                    cmg.setGood_key(value);
//                }
//                if ("uptime".equals(key)) {
//                    try {
//                        cmg.setUptime(DateUtil.strToDate(value));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
                if ("good_price".equals(key)) {
                    cmg.setGood_price(Double.valueOf(value));
                }
                if ("isback".equals(key)) {
                    cmg.setIsback(Integer.valueOf(value));
                }
                if ("status".equals(key)) {
                    cmg.setStatus(Integer.valueOf(value));
                }
                if ("out_time".equals(key)) {
                    cmg.setOut_time(Integer.valueOf(value));
                }
//                if ("listorder".equals(key)) {
//                    cmg.setListorder(Integer.valueOf(value));
//                }
                if ("draftsman".equals(key)) {
                    cmg.setDraftsman(Integer.valueOf(value));
                }
                if ("auditor".equals(key)) {
                    cmg.setAuditor(Integer.valueOf(value));
                }
                if ("approver".equals(key)) {
                    cmg.setApprover(Integer.valueOf(value));
                }
                if ("finished_weight".equals(key)) {
                    cmg.setFinished_weight(Integer.valueOf(value));
                }
                if ("gist".equals(key)) {
                    cmg.setGist(value);
                }
                if ("process".equals(key)) {
                    cmg.setProcess(value);
                }
//                if ("is_sync".equals(key)) {
//                    cmg.setIs_sync(Integer.valueOf(value));
//                }
//                if ("isweight".equals(key)) {
//                    cmg.setIsweight(Integer.valueOf(value));
//                }
//
//                if ("mode".equals(key)) {
//                    cmg.setMode(value);
//                }
//                if ("plu_num".equals(key)) {
//                    cmg.setPlu_num(Integer.valueOf(value));
//                }
//                if ("est_type_id".equals(key)) {
//                    cmg.setEst_type_id(Integer.valueOf(value));
//                }
            }else{
                if ("good_price".equals(key)) {
                    cmg.setGood_price(0);
                }
            }

        }
        if (opt.equals("add")) {
            HashMap map = sqlDao.getRecord("cds_stores.queryGoodNum",cmg);
            Iterator it2 = map.entrySet().iterator();
            while (it2.hasNext()){
                Map.Entry entry = (Map.Entry) it2.next();
                String num =entry.getValue().toString();
                String good_num = (Integer.valueOf(num)+1)+"";
                cmg.setGood_num(good_num);
            }
            cmg.setListorder(1);
            cmg.addUnParamFields("good_id,good_key");
            sqlDao.insertRecord(cmg);
        } else {
            cmg.addUnParamFields("good_id,good_num,listorder,good_key");
            cmg.addConditionField("good_id");
            sqlDao.updateRecord(cmg);
        }
        return new JsonMessage(1);
    }

    @RequestMapping(value = "saveErp", method = RequestMethod.POST)
    @ResourceMethod(name = "销售商品及原料消耗同步", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage saveErp(@RequestParam HashMap hashMap, String fid,String fname) {
        Iterator it = hashMap.entrySet().iterator();
        JSONObject jo = new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        int i =0;
        JSONObject jo3 = new JSONObject();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();

            if (!StringUtils.isEmpty(value)) {
                if(("rows"+'['+i+']'+"[mid]").equals(key)){
                    jo3.put("mid",value);
                }else if(("rows"+'['+i+']'+"[mname]").equals(key)){
                    jo3.put("mname",value);
                }else if(("rows"+'['+i+']'+"[unit]").equals(key)){
                    jo3.put("unit",value);
                }else if(("rows"+'['+i+']'+"[count]").equals(key)){
                    jo3.put("count",value);
                    list.add(jo3);
                    jo3 = new JSONObject();
                    ++i;
                }
                if("fid".equals(key)){
                    jo.put("fid",fid);
                }
                if("fname".equals(key)){
                    jo.put("fname",fname);
                }
            }
        }
        jo.put("mates",list);
        String jsondata = jo.toJSONString();
        return new JsonMessage(1,jsondata);
    }


    @RequestMapping(value = "userIndex", method = RequestMethod.GET)
    @ResourceMethod(name = "用户查询页面", check = CHECK_LOGIN)
    public String userIndex(String status) throws Exception {
        CdsStores cs = new CdsStores();
        List<CdsStores> cslist = sqlDao.getRecordList(cs);
        WebUtil.getRequest().setAttribute("status", status);
        WebUtil.getRequest().setAttribute("storeslist", cslist);
        return "common/users_query";
    }


    @RequestMapping(value = "getUserList", method = RequestMethod.POST)
    @ResourceMethod(name = "员工列表", check = CHECK_LOGIN)
    @ResponseBody
    public void getUserList(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_stores.getUserList", formInfo);
    }

    @RequestMapping(value = "getUserName", method = RequestMethod.POST)
    @ResourceMethod(name = "根据ID获取name", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage getUserName(String id) {
        CdsUsers cu = new CdsUsers();
        String name="";
        if(!StringUtils.isEmpty(id)){
            cu.setId(Integer.valueOf(id));
            cu.addConditionField("id");
            cu = sqlDao.getRecord(cu);
            if(cu!=null){
                name = cu.getUser_login();
            }
        }
        return new JsonMessage(1,name);
    }


    @RequestMapping(value = "updateErpCount", method = RequestMethod.POST)
    @ResourceMethod(name = "修改erp商品价格、出餐时间、重量", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage updateErpCount(String good_id,String good_price,String out_time,String finished_weight) {
        CdsMsGoods cmg = new CdsMsGoods();
        if(!StringUtils.isEmpty(good_id)){
            cmg.setGood_id(Integer.valueOf(good_id));
            cmg.addConditionField("good_id");
            cmg =  sqlDao.getRecord(cmg);
            if(!StringUtils.isEmpty(good_price)){
                cmg.setGood_price(Double.valueOf(good_price));
            }
            if(!StringUtils.isEmpty(out_time)){
                cmg.setOut_time(Integer.valueOf(out_time));
            }
            if(!StringUtils.isEmpty(finished_weight)){
                cmg.setFinished_weight(Integer.valueOf(finished_weight));
            }
            cmg.addUnParamFields("good_id");
            cmg.addConditionField("good_id");
            sqlDao.updateRecord(cmg);
        }
        return new JsonMessage(1);
    }

    @RequestMapping(value = "checkName", method = RequestMethod.POST)
    @ResourceMethod(name = "检查商品、类别名称是否重复", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage checkName(String name,String status,String brand_id,String class_id) {
        CdsClass cc = new CdsClass();
        CdsMsGoods cgi = new CdsMsGoods();
        //1检查商品表name是否重复、2检查类别表name是否重复
        String isok ="0";
        if("1".equals(status)){
            if(!StringUtils.isEmpty(class_id)){
                cgi.setClass_id(Integer.valueOf(class_id));
            }
            cgi.setGood_name(name);
            cgi.addConditionFields("good_name,class_id");
            cgi =  sqlDao.getRecord(cgi);
            if(cgi!=null){
                isok = "1";
            }
        }else{
            cc.setClass_name(name);
            cc.setBrand_id(Integer.valueOf(brand_id));
            cc.addConditionField("class_name");
            cc.addConditionField("brand_id");
            cc = sqlDao.getRecord(cc);
            if(cc!=null){
                isok="1";
            }
        }
        return new JsonMessage(isok);
    }





}
