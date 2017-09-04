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
import com.framework.util.MD5;
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
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.framework.annotation.CheckType.CHECK_LOGIN;
import static com.framework.annotation.CheckType.NO_CHECK;


/**
 * Created by Administrator on 2017/2/4.网络打印机调用接口
 */
@Controller
@RequestMapping("/printAPI")
public class PrintController extends BasicController {
    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "PrintService")
    protected PrintService printService;

    @Resource(name = "OrderService")
    protected OrderService orderService;

    private static String password = "A622625ufdki12yui3";

    @RequestMapping(value = "getConfigInfo",method = RequestMethod.POST)
    @ResourceMethod(name = "获取消息队列配置信息",check = NO_CHECK)
    @ResponseBody
    public String getConfigInfo(@RequestParam HashMap formInfo) throws Exception {
        if(formInfo.get("timestamp")==null){
            throw new Exception("404");
        }
        if(formInfo.get("sign")==null){
            throw new Exception("404");
        }

        String timestamp = formInfo.get("timestamp").toString();
        String paramsign = formInfo.get("sign").toString();
        String sign = StringUtil.MD5(timestamp+password);

        if(!paramsign.equals(sign)){
            throw new Exception("404");
        }

        String result = "";
        result+="tcp://192.168.1.233:61616"+"\r\n";
        result+="zhangsan"+"\r\n";
        result+="123"+"\r\n";
        return result;
    }

    @RequestMapping(value = "printrEmedy",method = RequestMethod.POST)
    @ResourceMethod(name = "补单接口", check = NO_CHECK)
    @ResponseBody
    public String printrEmedy(@RequestBody Map formInfo) throws Exception {
        if(formInfo.get("stores_id")==null){
            throw new Exception("404");
        }
        if(formInfo.get("order_no")==null){
            throw new Exception("404");
        }
        if(formInfo.get("timestamp")==null){
            throw new Exception("404");
        }
        if(formInfo.get("day")==null){
            throw new Exception("404");
        }
        if(formInfo.get("sign")==null){
            throw new Exception("404");
        }
        String stores_id = formInfo.get("stores_id").toString();
        String order_no = formInfo.get("order_no").toString();
        String timestamp = formInfo.get("timestamp").toString();
        String paramsign = formInfo.get("sign").toString();
        String day = formInfo.get("day").toString();


        String sign = StringUtil.MD5(stores_id+order_no+timestamp+password);
        if(!paramsign.equals(sign)){
            throw new Exception("404");
        }

        Map paramMap = new HashMap();
        paramMap.put("stores_id",stores_id);
        paramMap.put("order_no",order_no);
        paramMap.put("day",day);
        CdsOrderInfo cdsOrderInfo = sqlDao.getRecord("cds_prints.getPrintOrder");

        String orderstr = JSONObject.toJSONString(cdsOrderInfo);
        //打印小票
        jmsTemplate.send("order.print", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });
        return "1";
    }

    @RequestMapping(value = "printrSuccess",method = RequestMethod.POST)
    @ResourceMethod(name = "打印成功", check = NO_CHECK)
    @ResponseBody
    public String printrSuccess(@RequestBody Map formInfo) throws Exception {
        if(formInfo.get("order_id")==null){
            throw new Exception("404");
        }

        if(formInfo.get("timestamp")==null){
            throw new Exception("404");
        }
        if(formInfo.get("sign")==null){
            throw new Exception("404");
        }

        String order_id = formInfo.get("order_id").toString();
        String timestamp = formInfo.get("timestamp").toString();
        String paramsign = formInfo.get("sign").toString();

        String sign = StringUtil.MD5(order_id+timestamp+password);
        if(!paramsign.equals(sign)){
            throw new Exception("404");
        }

        CdsOrderInfo cdsOrderInfo = new CdsOrderInfo();
        cdsOrderInfo.setOrder_id(order_id);
        cdsOrderInfo.setIsstoresprint(1);
        cdsOrderInfo.resetParamField("isstoresprint");
        cdsOrderInfo.addConditionField("order_id");
        sqlDao.updateRecord(cdsOrderInfo);

        return "1";
    }
}
