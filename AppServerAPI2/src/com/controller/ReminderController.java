package com.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.*;
import com.framework.util.DateUtil;
import com.framework.util.ReminderUtil;
import com.framework.util.WebUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.framework.annotation.CheckType.CHECK_LOGIN;

/**
 * Created by c on 2017-02-02.
 */
@Controller
@RequestMapping("Reminder")
public class ReminderController extends BasicController {

    @RequestMapping(value = "indexCd", method = RequestMethod.GET)
    @ResourceMethod(name = "门店管理", check = CHECK_LOGIN)
    public String indexCd() throws Exception {
        CdsStores cs = new CdsStores();
        List<CdsStores> cslist = sqlDao.getRecordList(cs);
        WebUtil.getRequest().setAttribute("storeslist", cslist);
        return "reminder/index";
    }

    @RequestMapping(value = "geReminderList", method = RequestMethod.POST)
    @ResourceMethod(name = "门店管理列表", check = CHECK_LOGIN)
    @ResponseBody
    public void geReminderList(@RequestParam HashMap formInfo) throws Exception {
        CdsUsers user = WebUtil.getUser();
        int Stores_id = user.getStores_id();
        queryAndResponsePage("cds_reminder.geReminderList", formInfo);
    }


    @RequestMapping(value = "validateLogin", method = RequestMethod.POST)
    @ResourceMethod(name = "验证是登陆是否失效", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage validateLogin() {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        CdsUsers user = WebUtil.getUser();
        if (user!=null){
            List<HashMap> list = sqlDao.getRecordList("cds_reminder.getRole",user);
            if(list!=null&&list.size()>0){
                String ksid = getRedisString("ksid");
                if("false".equals(ksid)){
                    //登陆失效
                    String image = getRedisString("image");
                    jmsg.setStatus(image);
                }
            }
        }
        return jmsg;
    }


    @RequestMapping(value = "validateLoginText", method = RequestMethod.POST)
    @ResourceMethod(name = "验证是登陆是否失效", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage validateLoginText() {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        CdsUsers user = WebUtil.getUser();
        //登陆失效
        String image = getRedisString("image");
        jmsg.setStatus(image);
        return jmsg;
    }

    @RequestMapping(value = "reminderLogin", method = RequestMethod.POST)
    @ResourceMethod(name = "手动登陆", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage reminderLogin(String image) {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        ReminderUtil.LoginMis();
        try {
            JSONObject jo =  ReminderUtil.logIn2("cdswhls","cdswhls1",image);
            //登陆获取ksid
            String result = jo.getString("result");
            jo = JSONObject.parseObject(result);
            String isok = jo.getString("succeed");
            if("true".equals(isok)){
                //登陆成功
                result = jo.getString("successData");
                jo = JSONObject.parseObject(result);
                String ksid = jo.getString("ksid");
                //Redis添加ksid
                updateRedis("ksid",ksid);
                jmsg.setStatus("1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jmsg;
    }


    @RequestMapping(value = "reminderHf", method = RequestMethod.POST)
    @ResourceMethod(name = "催单回复", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage reminderHf(String reminder_id,String text) {
        String ksid = getRedisString("ksid");
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        try {
            String isok = ReminderUtil.reminderHf(reminder_id,ksid,text);
            if("1".equals(isok)){
                CdsReminder crd = new CdsReminder();
                if(!StringUtils.isEmpty(reminder_id)){
                    crd.setReminder_id(Integer.valueOf(reminder_id));
                    crd.addConditionField("reminder_id");
                    crd = sqlDao.getRecord(crd);
                    crd.setCr_check_remark(text);//回复内容
                    crd.setType(2);//人工回复
                    crd.setDo_status(2);//已处理
                    crd.addUnParamFields("cr_id");
                    crd.addConditionField("cr_id");
                    sqlDao.updateRecord(crd);
                    jmsg.setStatus("1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jmsg;
    }



    @RequestMapping(value = "cdCount", method = RequestMethod.POST)
    @ResourceMethod(name = "验证是登陆是否失效", check = CHECK_LOGIN)
    @ResponseBody
    public JsonMessage cdCount() {
        JsonMessage jmsg = new JsonMessage();
        jmsg.setStatus("0");
        Map map = sqlDao.getRecord("cds_reminder.getCount");
        String count = map.get("allcount").toString();
        jmsg.setStatus(count);
        return jmsg;
    }




    @RequestMapping(value = "MtText", method = RequestMethod.POST)
    @ResourceMethod(name = "测试", check = CHECK_LOGIN)
    @ResponseBody
    public void MtText() {
        String url = "http://e.waimai.meituan.com/v2/order/customer/reminder/unprocessed/list?pageNum=1";
        String url2 = "http://e.waimai.meituan.com/v2/order/customer/reminder/processed/list?wmPoiId=-1&startDate=2017-07-30&endDate=2017-08-04&pageNum=1";
        JSONObject json = ReminderUtil.getMtJson(url2);
//        System.out.println(jo);
//        ReminderController mc =  new ReminderController();
//        mc.saveMtCd(jo);
        String wmOrderList = json.getString("wmOrderList");
        JSONArray list  = JSONArray.parseArray(wmOrderList);
        if(list!=null&&list.size()>0){
            for (int i = 0; i <list.size() ; i++) {
                JSONObject jo = JSONObject.parseObject(list.get(i).toString());
//                String wm_order_id_view = jo.getString("wm_order_id_view");//订单id
                String wm_order_id_view = "16440644114736907";
                String recipient_address = jo.getString("recipient_address"); //收货地址
                String cartCount = jo.getString("cartCount"); //催单次数
                //催单次数
                int times = 0;
                if(!StringUtils.isEmpty(cartCount)){
                    times = Integer.valueOf(cartCount);
                }
                String wm_poi_id = jo.getString("wm_poi_id");//回复所需参数
                //验证催单信息是否存在
                CdsReminder crd = new CdsReminder();
                crd.setOrder_id(wm_order_id_view);
                crd.addConditionField("order_id");
                crd = sqlDao.getRecord(crd);
                //第一次催单
                if (crd==null&&times==1){
                    crd = new CdsReminder();
                    //根据订单号获取订单配送信息
                    CdsOrderInfo coi = new CdsOrderInfo();
//                    if(!StringUtils.isEmpty(reminderId)){
//                        crd.setReminder_id(Integer.valueOf(reminderId));
//                    }
                    coi.setOrder_desc(wm_order_id_view);
                    coi.addConditionField("order_desc");
                    coi = sqlDao.getRecord(coi);
                    //获取催单时间
//                    String reminderOrder = jo.getString("reminderOrder");
//                    JSONObject je = JSONObject.parseObject(reminderOrder);
//                    String createdTime = je.getString("createdTime");//催单时间

                    //催单处理信息
                    String cr_check_remark = "";
                    if(coi!=null){
                        crd.setStores_id(coi.getStores_id());
                        String name = coi.getTask_user_name();
                        String phone = coi.getTask_user_phone();
                        if(name!=null&&phone!=null){
                            cr_check_remark = "您的外卖已送出，配送员"+name+",电话"+phone;
                        }
                    }


                    crd.setAddress(recipient_address);
                    //只有有配送信息和催单次数为1次的才进行系统回复
                    if(!StringUtils.isEmpty(cr_check_remark)&&"1".equals(cartCount)){
                        //美团回复接口
//                        ReminderController rc = new ReminderController();
//                        String url2 = "http://e.waimai.meituan.com/v2/order/customer/reminder/unprocessed/w/response/v2?wmOrderViewId="+wm_order_id_view+"&wmPoiId="+wm_poi_id+"&response='"+cr_check_remark+"'&remindId="+222;
//                        ReminderUtil.getMtJson(url2);
                        String isok ="1";
                        if("1".equals(isok)){
                            //回复成功
//                                crd.setCr_user_id("admin");
                            try {
                                crd.setCr_check_time(DateUtil.strToTime(DateUtil.getNow()));//处理时间
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            crd.setCr_check_remark(cr_check_remark);
                            crd.setDo_status(2);
                            crd.setType(1);
                        }else{
                            crd.setDo_status(1);
                        }

                    }else{
//                            crd.setType(2);//处理方式（1、系统回复，2人工回复）
                        crd.setDo_status(1);//状态（1、未处理，2已处理）
                    }
                    crd.setTimes(times); //催单次数
                    if (!StringUtils.isEmpty(wm_order_id_view)){
                        crd.setOrder_id(wm_order_id_view);
                    }
//                    crd.setCr_remark(headPromptForPC);//催单内容
                    crd.addUnParamFields("cr_id");
                    sqlDao.insertRecord(crd);
                }else if(crd!=null&&times>1){
                    //第二次催单（催单已存在）
                    crd.setDo_status(1);
                    crd.setType(0);
                    crd.setTimes(times);
//                    crd.setCr_remark(headPromptForPC);//催单内容
                    //获取催单时间
                    crd.addConditionField("cr_id");
                    crd.addUnParamFields("cr_id");
                    sqlDao.updateRecord(crd);
                }

            }
        }

    }




    public  void saveMtCd(JSONObject json){
        String wmOrderList = json.getString("wmOrderList");
        JSONArray list  = JSONArray.parseArray(wmOrderList);
        if(list!=null&&list.size()>0){
            for (int i = 0; i <list.size() ; i++) {
                JSONObject jo = JSONObject.parseObject(list.get(i).toString());
//                String wm_order_id_view = jo.getString("wm_order_id_view");//订单id
                String wm_order_id_view = "16440644114736907";
                String recipient_address = jo.getString("recipient_address"); //收货地址
                String cartCount = jo.getString("cartCount"); //催单次数
                //催单次数
                int times = 0;
                if(!StringUtils.isEmpty(cartCount)){
                    times = Integer.valueOf(cartCount);
                }
                String wm_poi_id = jo.getString("wm_poi_id");//回复所需参数
                //验证催单信息是否存在
                CdsReminder crd = new CdsReminder();
                crd.setOrder_id(wm_order_id_view);
                crd.addConditionField("order_id");
                crd = sqlDao.getRecord(crd);
                //第一次催单
                if (crd==null&&times==1){
                    crd = new CdsReminder();
                    //根据订单号获取订单配送信息
                    CdsOrderInfo coi = new CdsOrderInfo();
//                    if(!StringUtils.isEmpty(reminderId)){
//                        crd.setReminder_id(Integer.valueOf(reminderId));
//                    }
                    coi.setOrder_desc(wm_order_id_view);
                    coi.addConditionField("order_desc");
                    coi = sqlDao.getRecord(coi);
                    //获取催单时间
//                    String reminderOrder = jo.getString("reminderOrder");
//                    JSONObject je = JSONObject.parseObject(reminderOrder);
//                    String createdTime = je.getString("createdTime");//催单时间

                    //催单处理信息
                    String cr_check_remark = "";
                    if(coi!=null){
                        crd.setStores_id(coi.getStores_id());
                        String name = coi.getTask_user_name();
                        String phone = coi.getTask_user_phone();
                        if(name!=null&&phone!=null){
                            cr_check_remark = "您的外卖已送出，配送员"+name+",电话"+phone;
                        }
                    }


                    crd.setAddress(recipient_address);
                    //只有有配送信息和催单次数为1次的才进行系统回复
                    if(!StringUtils.isEmpty(cr_check_remark)&&"1".equals(cartCount)){
                        //美团回复接口
                        ReminderController rc = new ReminderController();
                        String url = "http://e.waimai.meituan.com/v2/order/customer/reminder/unprocessed/w/response/v2?wmOrderViewId="+wm_order_id_view+"&wmPoiId="+wm_poi_id+"&response='"+cr_check_remark+"'&remindId="+222;
                        ReminderUtil.getMtJson(url);
                        String isok ="1";
                        if("1".equals(isok)){
                            //回复成功
//                                crd.setCr_user_id("admin");
                            try {
                                crd.setCr_check_time(DateUtil.strToTime(DateUtil.getNow()));//处理时间
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            crd.setCr_check_remark(cr_check_remark);
                            crd.setDo_status(2);
                            crd.setType(1);
                        }else{
                            crd.setDo_status(1);
                        }

                    }else{
//                            crd.setType(2);//处理方式（1、系统回复，2人工回复）
                        crd.setDo_status(1);//状态（1、未处理，2已处理）
                    }
                    crd.setTimes(times); //催单次数
                    if (!StringUtils.isEmpty(wm_order_id_view)){
                        crd.setOrder_id(wm_order_id_view);
                    }
//                    crd.setCr_remark(headPromptForPC);//催单内容
                    crd.addUnParamFields("cr_id");
                    sqlDao.insertRecord(crd);
                }else if(crd!=null&&times>1){
                    //第二次催单（催单已存在）
                    crd.setDo_status(1);
                    crd.setType(0);
                    crd.setTimes(times);
//                    crd.setCr_remark(headPromptForPC);//催单内容
                    //获取催单时间
                    crd.addConditionField("cr_id");
                    crd.addUnParamFields("cr_id");
                    sqlDao.updateRecord(crd);
                }

            }
        }
    }






}
