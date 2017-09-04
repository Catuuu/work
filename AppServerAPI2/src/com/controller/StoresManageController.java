package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.*;
import com.framework.system.SystemConfig;
import com.framework.system.SystemConstant;
import com.framework.util.BeanUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.meituan.vo.PoiParam;
import com.service.StoresBrandService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

import static com.framework.annotation.CheckType.CHECK_LOGIN;

/**
 * Created by c on 2017-02-02.
 */
@Controller
@RequestMapping("StoresManage")
public class StoresManageController extends BasicController {

    @Resource(name = "StoresBrandService")
    protected StoresBrandService storesbrandservice;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResourceMethod(name = "门店管理", check = CHECK_LOGIN)
    public String index() throws Exception {

        return "storesManage/index";
    }

    @RequestMapping(value = "getStoresList", method = RequestMethod.POST)
    @ResourceMethod(name = "门店管理列表", check = CHECK_LOGIN)
    @ResponseBody
    public void getStoresList(@RequestParam HashMap formInfo) throws Exception {
        queryAndResponsePage("cds_stores_manage.getPageRecord", formInfo);
    }

    @RequestMapping(value = "getProvince", method = RequestMethod.POST)
    @ResourceMethod(name = "获取省级列表", check = CHECK_LOGIN)
    @ResponseBody
    public List<JSONObject> getProvince() throws Exception {
       CdsArea ca = new CdsArea();
       ca.setAre_level(0);
       ca.addConditionField("are_level");
       List<CdsArea> list = sqlDao.getRecordList(ca);
       List<JSONObject> listjo = new ArrayList<JSONObject>();
       if(list!=null&&list.size()>0){
           for (int i = 0; i <list.size() ; i++) {
               JSONObject jo = new JSONObject();
               jo.put("id",list.get(i).getId());
               jo.put("text",list.get(i).getDataname());
               listjo.add(jo);
           }
       }
       return listjo;
    }


    @RequestMapping(value = "getCity", method = RequestMethod.POST)
    @ResourceMethod(name = "获取市级列表", check = CHECK_LOGIN)
    @ResponseBody
    public List<JSONObject> getCity(String id) throws Exception {
        CdsArea ca = new CdsArea();
        List<JSONObject> listjo = new ArrayList<JSONObject>();
        if(!StringUtils.isEmpty(id)){
            ca.setParentid(Integer.valueOf(id));
            ca.addConditionField("parentid");
            List<CdsArea> list = sqlDao.getRecordList(ca);
            if(list!=null&&list.size()>0){
                for (int i = 0; i <list.size() ; i++) {
                    JSONObject jo = new JSONObject();
                    jo.put("id",list.get(i).getId());
                    jo.put("text",list.get(i).getDataname());
                    listjo.add(jo);
                }
            }
        }
        return listjo;
    }








}
