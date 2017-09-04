package com.api;

import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsOrderLog;
import com.framework.mapping.system.CdsOrderSendLog;
import com.framework.mapping.system.CdsStores;
import com.framework.system.SystemConfig;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import com.opensdk.dianwoda.factory.APIFactoryDianWoDa;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.shenhou.factory.APIFactoryShenHou;
import com.service.OrderService;
import com.service.PrintService;
import com.service.StoresService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.framework.annotation.CheckType.CHECK_LOGIN;
import static com.framework.annotation.CheckType.CHECK_UDESK;
import static com.framework.annotation.CheckType.NO_CHECK;


/**
 * Created by Administrator on 2017/2/4.订单查询接口
 */
@Controller
@RequestMapping("/udeskAPI")
public class UdeskAPIController extends BasicController {
    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "PrintService")
    protected PrintService printService;

    @Resource(name = "OrderService")
    protected OrderService orderService;

    @RequestMapping(value = "orderlist",method = RequestMethod.POST)
    @ResourceMethod(name = "订单查询列表", check = CHECK_LOGIN)
    @ResponseBody
    public void orderlist(@RequestParam HashMap formInfo) throws Exception {
        if(formInfo.get("sl_date_begin")==null){
            formInfo.put("sl_date_begin", DateUtil.getDate());
        }
        if(formInfo.get("sl_date_end")==null){
            formInfo.put("sl_date_end",DateUtil.getDate());
        }

        if(formInfo.get("sort")!=null&&formInfo.get("sort").toString().equals("fromin_no")){
            formInfo.put("sort","CAST(fromin_no as int)");
        }
        queryAndResponsePage("cds_order_info.getPageRecord", formInfo);
    }





}
