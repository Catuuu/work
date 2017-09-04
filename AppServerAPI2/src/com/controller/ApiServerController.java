package com.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.system.*;
import com.framework.system.SystemConfig;
import com.framework.util.BeanUtil;
import com.framework.util.DateUtil;
import com.framework.util.MapUtil;
import com.framework.util.WebUtil;
import com.opensdk.eleme.util.SignGenerator;
import com.opensdk.eleme.util.StringUtil;
import com.opensdk.eleme.vo.MessageParam;
import com.opensdk.eleme.vo.OrderParam;
import com.opensdk.eleme2.api.entity.other.OMessage;
import com.opensdk.eleme2.api.utils.CallbackValidationUtil;
import com.opensdk.meituan.vo.OrderDetailParam;
import com.service.GoodService;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import com.service.*;

import static com.framework.annotation.CheckType.CHECK_MUITUAN;
import static com.framework.annotation.CheckType.NO_CHECK;


/**
 * Created by Administrator on 2017/2/4.
 */
@Controller
@RequestMapping("/ApiServer")
public class ApiServerController extends BasicController {

    @Resource(name = "OrderService")
    protected OrderService orderService;

    @Resource(name = "MemberService")
    protected MemberService memberService;

    @Resource(name = "StoresService")
    protected StoresService storesService;


    /**
     * 接收饿了么推送消息2.0
     * @param oMessage
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "ReceiveEleme2Order",method= RequestMethod.POST)
    @ResourceMethod(name = "接收饿了么推送消息", check = NO_CHECK)
    public String ReceiveEleme2Order(@RequestBody OMessage oMessage){
        SystemConfig.GetSystemParamEleme().getConsumer_secret();
        if (!CallbackValidationUtil.isValidMessage(oMessage, SystemConfig.GetEleme2Config().getApp_secret())) {
            logger.error("验证失败");
            return "{\"message\": \"error\"}";
        }

        jmsTemplate.send("elemeorder2.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(oMessage));
            }
        });
        return "{\"message\": \"ok\"}";
    }

    @ResponseBody
    @RequestMapping(value ="ReceiveEleme2Order",method= RequestMethod.GET)
    @ResourceMethod(name = "接收饿了么推送消息", check = NO_CHECK)
    public String ReceiveEleme2Order(){

        return "{\"message\": \"ok\"}";
    }
    /**
     * 接收百度推送消息2.0
     * @param oMessage
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "ReceiveBaiduOrder",method= RequestMethod.POST)
    @ResourceMethod(name = "接收百度推送消息", check = NO_CHECK)
    public String ReceiveBaiduOrder(@RequestBody OMessage oMessage){
        SystemConfig.GetSystemParamEleme().getConsumer_secret();
        if (!CallbackValidationUtil.isValidMessage(oMessage, SystemConfig.GetEleme2Config().getApp_secret())) {
            logger.error("验证失败");
            return "{\"message\": \"error\"}";
        }

        jmsTemplate.send("elemeorder2.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(oMessage));
            }
        });
        return "{\"message\": \"ok\"}";
    }

    @ResponseBody
    @RequestMapping(value ="ReceiveBaiduOrder",method= RequestMethod.GET)
    @ResourceMethod(name = "接收百度推送消息", check = NO_CHECK)
    public String ReceiveBaiduOrder(){

        return "{\"message\": \"ok\"}";
    }


    /**
     * 接收饿了么推送消息
     * @param message
     * @return String
     */
    @ResponseBody
    @RequestMapping("ReceiveElemeOrder")
    @ResourceMethod(name = "接收饿了么推送消息", check = NO_CHECK)
    public String ReceiveElemeOrder(MessageParam message){

       /* message.setEleme_order_ids("3008858653582322706");
        message.setPush_action(1);
        boolean flag = true;*/

        boolean flag = SignGenerator.getSigPush(message, SystemConfig.GetSystemParamEleme().getConsumer_secret());//sig验证
        if(!flag){
            logger.error("验证失败");
            return "{\"message\": \"error\"}";
        }else{
            try {
                if(message.getPush_action()==1){//新订单
                    String [] order_ids= message.getEleme_order_ids().split(",");
                    for (int i=0;i<order_ids.length;i++){
                        String order_id = order_ids[i];
                        MessageParam oneMessage = new MessageParam();
                        oneMessage.setPush_action(1);
                        oneMessage.setEleme_order_id(order_id);
                        jmsTemplate.send("elemeorder.queue", new MessageCreator() {
                            @Override
                            public Message createMessage(Session session) throws JMSException {
                                return session.createTextMessage(JSONObject.toJSONString(oneMessage));
                            }
                        });
                    }
                }else {//订单状态变更
                    jmsTemplate.send("elemeorderstatus.queue", new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage(JSONObject.toJSONString(message));
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("验证失败");
                return "{\"message\": \"error\"}";
            }

        }

        return "{\"message\": \"ok\"}";
    }

    /**
     * 1、推送订单URL,对应API文档8.1
     *
     * @return String
     */
    @ResponseBody
    @RequestMapping("ReceiveMeiruanOrderPay")
    //@ResourceMethod(name = "推送订单URL", check = CHECK_MUITUAN)
    @ResourceMethod(name = "推送订单URL", check = NO_CHECK)
    public String ReceiveMeiruanOrderPay(){
        Map params =  BeanUtil.createBean(WebUtil.getRequest(),HashMap.class);

        String order_id = params.get("order_id").toString();
        String latitude = params.get("latitude").toString();
        String longitude = params.get("longitude").toString();

        Map param = new HashMap();
        param.put("order_id",order_id);
        param.put("latitude",latitude);
        param.put("longitude",longitude);

        jmsTemplate.send("meituanorder.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(param));
            }
        });
        return "{\"data\": \"ok\"}";
    }

    /**
     * 2、订单已确认推送回调,对应API文档7.2
     * @return String
     */
    @ResponseBody
    @RequestMapping("ReceiveMeiruanOrderConfrim")
    @ResourceMethod(name = "已确认订单推送回调", check = CHECK_MUITUAN)
    public String ReceiveMeiruanOrderConfrim(){
        //Map params =  BeanUtil.createBean(WebUtil.getRequest(),HashMap.class);
        com.opensdk.meituan.vo.MessageParam message =  BeanUtil.createBean(WebUtil.getRequest(),com.opensdk.meituan.vo.MessageParam.class);
        message.setPush_action(2);

        jmsTemplate.send("meituanorderstatus.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(message));
            }
        });
        return "{\"data\": \"ok\"}";
    }

    /**
     * 3、已完成订单推送回调URL,对应API文档7.3
     * @return String
     */
    @ResponseBody
    @RequestMapping("ReceiveMeiruanOrderFulfillment")
    @ResourceMethod(name = "已完成订单推送回调URL", check = CHECK_MUITUAN)
    public String ReceiveMeiruanOrderFulfillment(){
        //Map params =  BeanUtil.createBean(WebUtil.getRequest(),HashMap.class);
        com.opensdk.meituan.vo.MessageParam message =  BeanUtil.createBean(WebUtil.getRequest(),com.opensdk.meituan.vo.MessageParam.class);
        message.setPush_action(3);

        jmsTemplate.send("meituanorderstatus.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(message));
            }
        });
        return "{\"data\": \"ok\"}";
    }

    /**
     * 4、美团用户或客服取消URL,对应API文档8
     * @return String
     */
    @ResponseBody
    @RequestMapping("ReceiveMeiruanOrderCancel")
    @ResourceMethod(name = "美团用户或客服取消URL", check = CHECK_MUITUAN)
    public String ReceiveMeiruanOrderCancel(){
        Map params =  BeanUtil.createBean(WebUtil.getRequest(),HashMap.class);
        com.opensdk.meituan.vo.MessageParam message =  BeanUtil.createBean(WebUtil.getRequest(),com.opensdk.meituan.vo.MessageParam.class);
        message.setPush_action(4);

        jmsTemplate.send("meituanorderstatus.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(message));
            }
        });
        return "{\"data\": \"ok\"}";
    }




    /**
     * 5、美团用户或客服退款流程操作URL,对应API文档10
     * @return String
     */
    @ResponseBody
    @RequestMapping("ReceiveMeiruanOrderBack")
    @ResourceMethod(name = "美团用户或客服取消URL", check = CHECK_MUITUAN)
    public String ReceiveMeiruanOrderBack(){
        Map params =  BeanUtil.createBean(WebUtil.getRequest(),HashMap.class);
        com.opensdk.meituan.vo.MessageParam message =  BeanUtil.createBean(WebUtil.getRequest(),com.opensdk.meituan.vo.MessageParam.class);
        message.setPush_action(5);

        jmsTemplate.send("meituanorderstatus.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(message));
            }
        });
        return "{\"data\": \"ok\"}";
    }

    /**
     * 6、订单配送状态回调URL,对应API文档10
     * @return String
     */
    @ResponseBody
    @RequestMapping("ReceiveMeiruanOrdersendstatus")
    @ResourceMethod(name = "订单配送状态回调URL", check = CHECK_MUITUAN)
    public String ReceiveMeiruanOrdersendstatus(){
        Map params =  BeanUtil.createBean(WebUtil.getRequest(),HashMap.class);
        com.opensdk.meituan.vo.MessageParam message =  BeanUtil.createBean(WebUtil.getRequest(),com.opensdk.meituan.vo.MessageParam.class);
        message.setPush_action(6);
        if(message.getDispatcher_name()!=null&&!message.getDispatcher_name().equals("")){
            try {
                message.setDispatcher_name(URLDecoder.decode(message.getDispatcher_name(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        jmsTemplate.send("meituanorderstatus.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(message));
            }
        });
        return "{\"data\": \"ok\"}";
    }

    /**
     * 7、美团用户或客服部分退款流程操作URL,对应API文档8
     * @return String
     */
    @ResponseBody
    @RequestMapping("ReceiveMeiruanOrderBack2")
    @ResourceMethod(name = "美团用户或客服部分退款流程", check = CHECK_MUITUAN)
    public String ReceiveMeiruanOrderBack2(){
        Map params =  BeanUtil.createBean(WebUtil.getRequest(),HashMap.class);
        com.opensdk.meituan.vo.MessageParam message =  BeanUtil.createBean(WebUtil.getRequest(),com.opensdk.meituan.vo.MessageParam.class);
        message.setPush_action(7);

        jmsTemplate.send("meituanorderstatus.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(message));
            }
        });
        return "{\"data\": \"ok\"}";
    }
    /**
     * 8、订单结算信息回调URL,对应API文档
     * @return String
     */
    @ResponseBody
    @RequestMapping("ReceiveMeiruanOrderSettlement")
    @ResourceMethod(name = "订单结算信息回调URL", check = CHECK_MUITUAN)
    public String ReceiveMeiruanOrderSettlement(){
        Map params =  BeanUtil.createBean(WebUtil.getRequest(),HashMap.class);
        com.opensdk.meituan.vo.MessageParam message =  BeanUtil.createBean(WebUtil.getRequest(),com.opensdk.meituan.vo.MessageParam.class);
        message.setPush_action(8);

        jmsTemplate.send("meituanorderstatus.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(message));
            }
        });
        return "{\"data\": \"ok\"}";
    }


    /**
     * 9、门店状态变更回调URL,对应API文档
     * @return String
     */
    @ResponseBody
    @RequestMapping("ReceiveMeiruanOrderShopstatus")
    @ResourceMethod(name = "门店状态变更回调", check = CHECK_MUITUAN)
    public String ReceiveMeiruanOrderShopstatus(){
        Map params =  BeanUtil.createBean(WebUtil.getRequest(),HashMap.class);
        com.opensdk.meituan.vo.MessageParam message =  BeanUtil.createBean(WebUtil.getRequest(),com.opensdk.meituan.vo.MessageParam.class);
        message.setPush_action(9);

        jmsTemplate.send("meituanorderstatus.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSONObject.toJSONString(message));
            }
        });
        return "{\"data\": \"ok\"}";
    }














    /**
     * 接收百度推送消息
     * @param cdsOrderInfo
     * @return String
     */
    @ResponseBody
    @RequestMapping("ReceiveBaiduOrderPay")
    @ResourceMethod(name = "接收百度订单消息", check = NO_CHECK)
    public String ReceiveBaiduOrderPay(CdsOrderInfo cdsOrderInfo){
        logger.info(JSONObject.toJSON(cdsOrderInfo));

        return "{\"data\": \"ok\"}";
    }


    /**
     * 接收百度订单推送消息
     * @param bodyjson
     * @return String
     */
    @ResponseBody
    @RequestMapping("CdsOutside")
    @ResourceMethod(name = "接收订单推送消息", check = NO_CHECK)
    public Map CdsOutside(String bodyjson){
        Map<String,String> resultMap = new HashMap();
        logger.info("百度推送接收消息:"+bodyjson);
        Map map = BeanUtil.createBean(bodyjson,HashMap.class);
        JSONObject jo = JSONObject.parseObject(bodyjson);
        if(jo.getString("service_time")==null||jo.getString("service_time").equals("立即送餐")||jo.getString("service_time").equals("0")){
            jo.remove("service_time");
        }

        String form_stores_id = jo.getString("stores_id");
        jo.remove("stores_id");


        CdsOrderInfo cdsOrderInfo = JSONObject.toJavaObject(jo, CdsOrderInfo.class);
        cdsOrderInfo.addConditionFields("fromin,order_desc");
        CdsOrderInfo older = sqlDao.getRecord(cdsOrderInfo);
        if(older!=null){
            resultMap.put("code","1");
            resultMap.put("message","订单已存在！");
            return resultMap;
            //return "{\"code\": \"1\",\"message\": \"订单已存在！\"}";
        }
        cdsOrderInfo.setOrder_status(2);
        cdsOrderInfo.setOrder_type(1);
        cdsOrderInfo.setBrand_id(1);
        JSONArray goodlist = jo.getJSONArray("goods_list");

        List<CdsGood> goods = new ArrayList<CdsGood>();
        for (int i=0;i<goodlist.size();i++) {
            JSONObject good = goodlist.getJSONObject(i);
            CdsGood cdsGood = new CdsGood();
            cdsGood.setGood_name(good.getString("good_name"));
            cdsGood.setPrice(good.getFloatValue("current_price"));
            cdsGood.setQuantity(good.getIntValue("count"));
            cdsGood.setGood_type(1);
            goods.add(cdsGood);
        }
        cdsOrderInfo.setGoods(BeanUtil.toJsonString(goods));
        cdsOrderInfo.setPay_type_id(1);
        cdsOrderInfo.setPay_type_name("三方平台");

        Map params = new HashMap();
        params.put("stores_id",form_stores_id);
        CdsStores stores = sqlDao.getRecord("cds_order_info.getCdsStore",params);

        if(stores!=null){
            CdsMember member = memberService.GetMember(cdsOrderInfo,stores);
            cdsOrderInfo.setMember_id(member.getMember_id());
            cdsOrderInfo.setOrder_id(com.framework.util.StringUtil.getPrimaryOrderKey());
            cdsOrderInfo.setStores_id(stores.getStores_id());
            cdsOrderInfo.setIn_time(new Date());
            cdsOrderInfo.setBrand_id(1);

            JSONObject jodetail = JSONObject.parseObject(stores.getDispatch_extinfo());

            if(cdsOrderInfo.getFromin().equals("百度外卖")){
                cdsOrderInfo.setBaidu_lat(cdsOrderInfo.getReceiver_lat());
                cdsOrderInfo.setBaidu_lng(cdsOrderInfo.getReceiver_lng());

                MapGG mapGG = MapUtil.bd_decrypt(cdsOrderInfo.getBaidu_lat(),cdsOrderInfo.getBaidu_lng());
                cdsOrderInfo.setReceiver_lat(mapGG.getGg_lat());
                cdsOrderInfo.setReceiver_lng(mapGG.getGg_lon());

                int send_type = Integer.parseInt(jodetail.getString("send_type3"));//0百度配送 1人工分配 2自动给“生活半径”配送 3自动给“菜大师”配送
                if(send_type==0){
                    cdsOrderInfo.setSend_name("百度");//配送方式
                    cdsOrderInfo.setIs_sync(0);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }else if(send_type==1){
                    cdsOrderInfo.setSend_name("人工分配");  //配送方式
                    cdsOrderInfo.setIs_sync(0);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }else if(send_type==2){
                    cdsOrderInfo.setSend_name("生活半径");  //配送方式
                    cdsOrderInfo.setIs_sync(1);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }else if(send_type==3){
                    cdsOrderInfo.setSend_name("点我达");  //配送方式
                    cdsOrderInfo.setIs_sync(1);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }else if(send_type==4){
                    cdsOrderInfo.setSend_name("运速达");  //配送方式
                    cdsOrderInfo.setIs_sync(1);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }

            }else if(cdsOrderInfo.getFromin().equals("美团")){
                MapBD mapBD = MapUtil.bd_encrypt(cdsOrderInfo.getReceiver_lat(),cdsOrderInfo.getReceiver_lng());
                cdsOrderInfo.setBaidu_lat(mapBD.getBd_lat());
                cdsOrderInfo.setBaidu_lng(mapBD.getBd_lon());

                int send_type = Integer.parseInt(jodetail.getString("send_type2"));//0百度配送 1人工分配 2自动给“生活半径”配送 3自动给“菜大师”配送
                if(send_type==0){
                    cdsOrderInfo.setSend_name("美团");//配送方式
                    cdsOrderInfo.setIs_sync(0);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }else if(send_type==1){
                    cdsOrderInfo.setSend_name("人工分配");  //配送方式
                    cdsOrderInfo.setIs_sync(0);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }else if(send_type==2){
                    cdsOrderInfo.setSend_name("生活半径");  //配送方式
                    cdsOrderInfo.setIs_sync(1);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }else if(send_type==3){
                    cdsOrderInfo.setSend_name("点我达");  //配送方式
                    cdsOrderInfo.setIs_sync(1);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }else if(send_type==4){
                    cdsOrderInfo.setSend_name("运速达");  //配送方式
                    cdsOrderInfo.setIs_sync(1);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }

            }else if(cdsOrderInfo.getFromin().equals("饿了么")){
                MapBD mapBD = MapUtil.bd_encrypt(cdsOrderInfo.getReceiver_lat(),cdsOrderInfo.getReceiver_lng());
                cdsOrderInfo.setBaidu_lat(mapBD.getBd_lat());
                cdsOrderInfo.setBaidu_lng(mapBD.getBd_lon());

                int send_type = Integer.parseInt(jodetail.getString("send_type2"));//0百度配送 1人工分配 2自动给“生活半径”配送 3自动给“菜大师”配送
                if(send_type==0){
                    cdsOrderInfo.setSend_name("饿了么");//配送方式
                    cdsOrderInfo.setIs_sync(0);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }else if(send_type==1){
                    cdsOrderInfo.setSend_name("人工分配");  //配送方式
                    cdsOrderInfo.setIs_sync(0);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }else if(send_type==2){
                    cdsOrderInfo.setSend_name("生活半径");  //配送方式
                    cdsOrderInfo.setIs_sync(1);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }else if(send_type==3){
                    cdsOrderInfo.setSend_name("点我达");  //配送方式
                    cdsOrderInfo.setIs_sync(1);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }else if(send_type==4){
                    cdsOrderInfo.setSend_name("运速达");  //配送方式
                    cdsOrderInfo.setIs_sync(1);   //是否发送配送 0、不需要,1、未发送，2、已发送
                }
            }

            if(cdsOrderInfo.getService_time()!=null){
                cdsOrderInfo.setService_time_str(DateUtil.dateToStr(cdsOrderInfo.getService_time(),"HH:mm"));
            }else{
                cdsOrderInfo.setService_time_str("及时送达");
            }
            cdsOrderInfo.setBrand_id(1);
            cdsOrderInfo.setOrder_no(storesService.getStoresNo(cdsOrderInfo,0, cdsOrderInfo.getCreate_date()));//获取订单流水号

            orderService.createOrder(cdsOrderInfo); //创建订单
        }
        resultMap.put("code","1");
        resultMap.put("message","订单保存成功！");
        return resultMap;
    }
}
