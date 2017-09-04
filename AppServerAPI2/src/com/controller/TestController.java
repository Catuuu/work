package com.controller;

import com.MessageRunnable;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.system.*;
import com.framework.system.SystemConfig;
import com.framework.util.MapUtil;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.eleme.vo.MessageParam;
import com.opensdk.eleme.vo.OrderParam;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.meituan.vo.OrderDetailParam;
import com.opensdk.meituan.vo.PoiPolicyParam;
import com.opensdk.meituan.vo.SystemParamMeituan;
import com.opensdk.weixin.util.HttpUtil;
import com.service.*;
import com.service.WeiXinService;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.framework.annotation.CheckType.CHECK_LOGIN;
import static com.framework.annotation.CheckType.NO_CHECK;

/**
 * Created by c on 2017-02-02.
 * 用于测试
 */
@Controller
@RequestMapping("Test")
public class TestController extends BasicController {
    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "PrintService")
    protected PrintService printService;

    @Resource(name = "OrderService")
    protected OrderService orderService;


    @Resource(name = "PackService")
    protected PackService PackService;

    @Resource(name = "WeiXinService")
    protected WeiXinService weiXinService;

    @Resource(name = "MemberService")
    protected MemberService memberService;


    private final static SystemParamMeituan sysPram = new SystemParamMeituan("483", "a4afb2e41caad32336832a694509152b");


    @RequestMapping(value = "sendCode", method = RequestMethod.GET)
    @ResourceMethod(name = "发送短信消息", check = CHECK_LOGIN)
    @ResponseBody
    public String sendCode() throws Exception {

        //String messagecontent = "【菜大师】"+"为感谢您对本店的支持，特在本月10号至15号推出爆款活动，满25减15，满40减20，满60减24，菜大师邀您品尝品质外卖！";
        String messagecontent = "【菜大师】" + "yoyoyo，菜大师七月新品上线啦！美味总有一款属于你的free style！退订回T";
        // String messagecontent = "【帅小锅】"+"震惊！！！手撕鸡、小炒土豆片等多款热门菜改良强势回归！\n" +"另有两款优秀新品上线，赶紧来尝尝吧~";

        List<Map> list = sqlDao.getRecordList("cds_message_code.getRecords5");


        for (Map map : list) {
            String phone = map.get("rec_tel").toString();
            MessageCode message = new MessageCode();
            message.setPhone(phone);
            message.setMc_type(9);
            message.setContent(messagecontent);
            String orderstr = JSONObject.toJSONString(message);


          /* CdsMessageCode message2 = new CdsMessageCode();
            Date d = new Date();
            message2.setMc_mobile(phone);
            message2.setMc_date(d);
            message2.setMc_addip("127.0.0.1");
            message2.setMc_type("9");
            message2.setMc_content(messagecontent);

            sqlDao.insertRecord("cds_message_code.saveEntity",message2);*/


            jmsTemplate.send("messageCode", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(orderstr);
                }
            });
        }

        String phone = "15802790395";
        MessageCode message = new MessageCode();
        message.setPhone(phone);
        message.setMc_type(9);
        message.setContent(messagecontent);
        String orderstr = JSONObject.toJSONString(message);
        jmsTemplate.send("messageCode", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });

        return "";
    }


    @RequestMapping(value = "order", method = RequestMethod.GET)
    @ResourceMethod(name = "测试发送订单信息", check = NO_CHECK)
    @ResponseBody
    public String test4() throws Exception {
        String id = "3011280027823242372";
        long order_id =Long.parseLong(id);

        OrderParam orderParam = APIFactoryEleme.getOrderAPI().orderGetOrderDetail(SystemConfig.GetSystemParamEleme(),id);
        CdsStores stores = storesService.GetStores(orderParam.getTp_restaurant_id());
        if(stores!=null) {
            CdsMember member = memberService.GetMember(orderParam, stores);
            CdsOrderInfo orderInfo = new CdsOrderInfo();
            orderInfo.setElemeParam(member, orderParam, stores); //设置饿了么订单的相关信息
            orderInfo.setOrder_no(storesService.getStoresNo(orderInfo, stores.getBrand_fromno_start(), orderInfo.getCreate_date()));//获取订单流水号

            orderService.createOrder(orderInfo); //创建订单
        }/*


        //新订单处理
        OrderDetailParam orderParam = APIFactoryMeituan.getOrderAPI().orderGetOrderDetail(SystemConfig.GetSystemParamMeituan(),order_id,1);



        CdsStores stores = storesService.GetStores(Integer.parseInt(orderParam.getApp_poi_code()));

        if(stores!=null){
            CdsMember member = memberService.GetMember(orderParam,stores);
            CdsOrderInfo orderInfo = new CdsOrderInfo();
            orderInfo.setMeitianParam(member,orderParam,stores); //设置美团订单的相关信息
            orderInfo.setOrder_no(storesService.getStoresNo(orderInfo,stores.getBrand_fromno_start(), orderInfo.getCreate_date()));//获取订单流水号

            orderService.createOrder(orderInfo); //创建订单

        }
*/

        /*CdsOrderInfo orderInfo = new CdsOrderInfo();
        orderInfo.setOrder_id(order_id);
        orderInfo.addConditionField("order_id");
        orderInfo = sqlDao.getRecord(orderInfo);
        weiXinService.sendTaskOrder(orderInfo);*/
        /*OrderDetailParam orderParam = APIFactoryMeituan.getOrderAPI().orderGetOrderDetail(SystemConfig.GetSystemParamMeituan(),Long.parseLong(order_id),1);
        CdsStores stores = storesService.GetStores(Integer.parseInt(orderParam.getApp_poi_code()));
        if(stores!=null){
            CdsMember member = memberService.GetMember(orderParam,stores);
            CdsOrderInfo orderInfo = new CdsOrderInfo();
            orderInfo.setMeitianParam(member,orderParam,stores); //设置美团订单的相关信息


        }*/
        /*OrderParam orderParam = APIFactoryEleme.getOrderAPI().orderGetOrderDetail(SystemConfig.GetSystemParamEleme(),order_id);
        CdsStores stores = storesService.GetStores(orderParam.getTp_restaurant_id());
        if(stores!=null){
            CdsMember member = memberService.GetMember(orderParam,stores);
            CdsOrderInfo orderInfo = new CdsOrderInfo();
            orderInfo.setElemeParam(member,orderParam,stores); //设置饿了么订单的相关信息
            System.out.println(order_id);
        }*/



        /*CdsWeixinOrderInfo weixinInfo = new CdsWeixinOrderInfo();
        weixinInfo.setOrder_id(order_id);
        weixinInfo.addConditionField("order_id");
        weixinInfo = sqlDao.getRecord(weixinInfo);
        if (weixinInfo != null) {
            CdsMember member = memberService.GetMember(weixinInfo);
            if (member != null) {
                CdsStores stores = storesService.GetStores(weixinInfo.getStores_id(), member.getBrand_id());
                if (stores != null) {
                    CdsOrderInfo orderInfo = new CdsOrderInfo();
                    orderInfo.setWeixinParam(member, weixinInfo, stores); //设置微信订单的相关信息
                    orderInfo.setOrder_no(storesService.getStoresNo(orderInfo, stores.getBrand_id(), weixinInfo.getCreate_date()));//获取订单流水号
                    orderService.createOrder(orderInfo); //创建订单

                    if (orderInfo.getFromin().equals("微信") && orderInfo.getOrder_type() == 2) {
                        String key = "finsh_"+orderInfo.getOrder_id();
                        addRedis(3, key, "true", 20 * 60 * 60);
                    }
                }
            }
        }*/



        /*long meituan_order_id = 16440642737211277L;
        OrderDetailParam orderParam = APIFactoryMeituan.getOrderAPI().orderGetOrderDetail(SystemConfig.GetSystemParamMeituan(), meituan_order_id, 1);

        //PoiPolicyParam poiPolicyParam =  APIFactoryMeituan.getOrderAPI().orderGetActDetailByAcId(SystemConfig.GetSystemParamMeituan(),10019);

        CdsStores cdsStores = storesService.GetStores(Integer.parseInt(orderParam.getApp_poi_code()));
        CdsMember member = memberService.GetMember(orderParam, cdsStores);
        CdsOrderInfo orderInfo = new CdsOrderInfo();
        orderInfo.setMeitianParam(member, orderParam, cdsStores); //设置美团订单的相关信息*/
        return "";
    }


    @RequestMapping(value = "orderTest", method = RequestMethod.GET)
    @ResourceMethod(name = "测试发送订单信息", check = NO_CHECK)
    @ResponseBody
    public Map orderTest(String order_id) throws Exception {
        Map map = new HashMap();
        map.put("data", "ok");
        return map;
        //return "{\"data\": \"ok\"}";
    }

    @RequestMapping(value = "orderTest2", method = RequestMethod.GET)
    @ResourceMethod(name = "测试发送订单信息", check = NO_CHECK)
    @ResponseBody
    public String orderTest2(String order_id) throws Exception {

        return "{\"data\": \"ok\"}";
    }

    @RequestMapping(value = "ordermessage", method = RequestMethod.GET)
    @ResourceMethod(name = "测试发送订单信息", check = NO_CHECK)
    @ResponseBody
    public String ordermessage(String order_id) throws Exception {
        CdsOrderInfo cdsOrderInfo = new CdsOrderInfo();
        cdsOrderInfo.setOrder_id(order_id);
        cdsOrderInfo.addConditionField("order_id");
        cdsOrderInfo = sqlDao.getRecord(cdsOrderInfo);
        logger.error("订单ID==" + order_id);
        if (cdsOrderInfo != null) {
            weiXinService.sendGetOrder(cdsOrderInfo);
            weiXinService.sendTaskOrder(cdsOrderInfo);
            weiXinService.sendFinishOrder(cdsOrderInfo);
            weiXinService.sendCancelOrder(cdsOrderInfo);
        }
        return "成功";
    }


    @RequestMapping(value = "test2", method = RequestMethod.GET)
    @ResourceMethod(name = "测试reids", check = CHECK_LOGIN)
    @ResponseBody
    public String test2(int count) throws Exception {

        for (int i = 0; i < count; i++) {
            MessageRunnable r = new MessageRunnable(storesService, i);
            Thread t = new Thread(r);//创建线程
            t.start();
        }
        return "";
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    @ResourceMethod(name = "测试消息发送", check = CHECK_LOGIN)
    @ResponseBody
    public String test(String order_id) throws Exception {
        /*String orderstr = "123456789";
        jmsTemplate.send("光谷店", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                return session.createTextMessage(orderstr);
            }
        });*/

        CdsOrderInfo orderInfo = new CdsOrderInfo();
        orderInfo.setOrder_id(order_id);
        orderInfo.addConditionField("order_id");
        orderInfo = sqlDao.getRecord(orderInfo);
        if (orderInfo != null) {
            orderService.orderGoodDo(orderInfo);
        }
        return "";
    }


    @RequestMapping(value = "test3", method = RequestMethod.GET)
    @ResourceMethod(name = "生成高德配送距离", check = CHECK_LOGIN)
    @ResponseBody
    public String test3(String key, int stores_id) throws Exception {
        key = "5b30e5f159c008aeb008e09a99ccbda4";
        CdsStores stores = new CdsStores();
        stores.setStores_id(stores_id);
        stores.addConditionField("stores_id");
        stores = sqlDao.getRecord(stores);

        JSONObject jo = JSONObject.parseObject(stores.getPoints());
        MapGG gg = MapUtil.bd_decrypt(jo.getDoubleValue("lat"), jo.getDoubleValue("lng"));

        double gg_log = gg.getGg_lon();
        double gg_lat = gg.getGg_lat();

        Map param = new HashMap();
        param.put("stores_id", stores_id);
        List<CdsOrderInfo> list = sqlDao.getRecordList("sys_dict.getRecordList2", param);
        for (CdsOrderInfo send : list) {

            String url = "http://restapi.amap.com/v3/distance?origins=" + gg_log + "," + gg_lat + "&destination=" + send.getReceiver_lng() + "," + send.getReceiver_lat() + "&output=json&key=" + key + "&type=3";
            send.setUrl(url);
            try {
                jmsTemplate.send("cds_order_send.queue", new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(JSONObject.toJSONString(send));
                    }
                });

            } catch (Exception e) {

            }
        }
        return "";
    }

    public static void main(String[] args) {
        int distance = 1001;
        int duration = 0;

        float price = 2f;
        int kilometre = 0; //计算增长数
        if (distance > 0) {
            double b = (float) distance / 500;
            kilometre = (int) Math.ceil(b);
            price += 1 * kilometre;
        }
        System.out.println(price);
    }

}
