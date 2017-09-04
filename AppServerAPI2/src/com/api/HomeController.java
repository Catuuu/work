package com.api;

import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.CdsUsers;
import com.framework.util.DateUtil;
import com.framework.util.WebUtil;
import com.service.OrderService;
import com.service.PrintService;
import com.service.StoresService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.framework.annotation.CheckType.CHECK_LOGIN;


/**
 * Created by Administrator on 2017/2/4.订单查询接口
 */
@Controller
@RequestMapping("/homeApi")
public class HomeController extends BasicController {
    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "PrintService")
    protected PrintService printService;

    @Resource(name = "OrderService")
    protected OrderService orderService;



    @RequestMapping(value = "viewData",method = RequestMethod.POST)
    @ResourceMethod(name = "显示订单数量", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage printOrder(@RequestBody Map formInfo) throws Exception {
        int stores_id = WebUtil.getUser().getStores_id();

        Map paramMap = new HashMap();
        paramMap.put("cur_date",DateUtil.getDate());
        paramMap.put("yesterday",DateUtil.dateToStr(DateUtil.addDay(new Date(),-1)));
        paramMap.put("last_week",DateUtil.dateToStr(DateUtil.addDay(new Date(),-7)));
        paramMap.put("stores_id",stores_id);

        Map resultMap = new HashMap();
        List list = sqlDao.getRecordList("cds_home.getdoActionCount",paramMap);



        JsonMessage message = new JsonMessage(1,"获取成功",resultMap);
        return message;
    }

    @RequestMapping(value = "exceptionOrderList",method = RequestMethod.POST)
    @ResourceMethod(name = "异常订单列表", check = CHECK_LOGIN)
    @ResponseBody
    public void exceptionOrderList(@RequestParam HashMap formInfo) throws Exception {
//        int stores_id = WebUtil.getUser().getStores_id();
//        Map paramMap = new HashMap();
//        paramMap.put("cur_date",DateUtil.getDate());
//        paramMap.put("yesterday",DateUtil.dateToStr(DateUtil.addDay(new Date(),-1)));
//        paramMap.put("last_week",DateUtil.dateToStr(DateUtil.addDay(new Date(),-7)));
//        paramMap.put("stores_id",stores_id);

        if(formInfo.get("sl_date_begin")==null){
            formInfo.put("sl_date_begin", DateUtil.getDate());
        }
        if(formInfo.get("sl_date_end")==null){
            formInfo.put("sl_date_end",DateUtil.getDate());
        }

        if(WebUtil.getSession("stores_id") != null)
            formInfo.put("stores_id",WebUtil.getSession("stores_id"));
        else formInfo.put("stores_id",WebUtil.getUser().getStores_id());
        if(formInfo.get("sort")!=null&&formInfo.get("sort").toString().equals("fromin_no")){
            formInfo.put("sort","CAST(fromin_no as int)");
        }
        queryAndResponsePage("cds_home.exception_order_list", formInfo);

    }


    @RequestMapping(value = "shopSelect",method = RequestMethod.POST)
    @ResourceMethod(name = "选择店铺", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage shopSelect(CdsUsers user) throws Exception {

        CdsUsers user1 = WebUtil.getUser();
        if(user.getStores_id()!=0){
            WebUtil.setSession("stores_id",user.getStores_id());
            if(user.getStores_id()==-1){
                WebUtil.removeSession("stores_id");
            }
//            user1.setStores_id(user.getStores_id());
        }
        if(user.getCur_date()!=null){
            user1.setCur_date(user.getCur_date());
        }


        JsonMessage message = new JsonMessage(1,"获取成功",user1);
        return message;
    }



}
