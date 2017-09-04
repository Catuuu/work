package com.jms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.CdsIncreaseOrder;
import com.framework.mapping.system.CdsMember;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsStores;
import com.framework.mapping.system.CdsStoresBrand;
import com.framework.system.SystemConfig;
import com.framework.util.DateUtil;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.eleme.vo.OrderParam;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.meituan.vo.OrderDetailParam;
import com.service.GiftService;
import com.service.MemberService;
import com.service.OrderService;
import com.service.StoresService;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单补单
 */
@Component
@EnableJms
public class BudanOrderIncreased extends BasicComponent {

    @Resource(name = "MemberService")
    protected MemberService memberService;

    @Resource(name = "StoresService")
    protected StoresService storesService;

    @Resource(name = "OrderService")
    protected OrderService orderService;

    @Resource(name = "GiftService")
    protected GiftService giftService;

   // @Scheduled(cron = "0/60 * *  * * ? ")   //每60秒执行一次
    public void IncreasedOrderDo() {
        CdsStoresBrand cdsstoresBrand = new CdsStoresBrand();
        cdsstoresBrand.setStores_brand_id(11);
        cdsstoresBrand.addConditionFields("stores_brand_id");
        List<CdsStoresBrand> stores_brand_list = sqlDao.getRecordList(cdsstoresBrand);
        for (CdsStoresBrand brand : stores_brand_list) {
            String incstr = JSONObject.toJSONString(brand);
            jmsTemplate.send("caidashi_order_budan.increased", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(incstr);
                }
            });
        }
    }

    /**
     * 订单补单处理，判断是否需要进行补单
     *
     * @param message
     */
    //@JmsListener(containerFactory="jmsListenerContainerFactory",destination = "order.noset.goods",concurrency="2-5")
    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "caidashi_order_budan.increased", concurrency = "1")
    public void onbudanMessage(Message message) {
        CdsStoresBrand storesBrand = null;
        JSONArray jsonarray = null;
        try {
            String data = ((TextMessage) message).getText();
            storesBrand = JSONObject.toJavaObject(JSONObject.parseObject(data), CdsStoresBrand.class);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        if (storesBrand == null) {
            return;
        }
        CdsStores cdsStores = storesService.GetStores(storesBrand.getStores_brand_id());
        if (cdsStores == null) {
            return;
        }

        Map params = new HashMap();
        params.put("cday", DateUtil.getDate());
        params.put("stores_id", cdsStores.getStores_id());

        String eleme_key = "eleme_budan_time_" + storesBrand.getStores_brand_id();
        String meituan_key = "meituan_budan_time_" + storesBrand.getStores_brand_id();

        List<CdsIncreaseOrder> list = sqlDao.getRecordList("cds_order_info.getOrderSum", params);
        for (CdsIncreaseOrder order : list) {
            if (order.getMax_num() != order.getAll_count() || order.getDfsecond() > 120) {
                if (order.getFromin().equals("饿了么")) {
                    //饿了么补单逻辑
                    elem_increased(cdsStores, storesBrand);
                } else if (order.getFromin().equals("美团")) {
                    //美团补单逻辑
                    meituan_increased(cdsStores, storesBrand);
                }
            }
            if (order.getFromin().equals("饿了么")) {
                eleme_key = "";
            } else if (order.getFromin().equals("美团")) {
                meituan_key = "";
            }
        }
        if (!eleme_key.equals("")) {
            //饿了么补单逻辑
            elem_increased(cdsStores, storesBrand);
        }
        if (!meituan_key.equals("")) {
            //美团补单逻辑
            meituan_increased(cdsStores, storesBrand);
        }
    }


    //美团补单
    private void meituan_increased(CdsStores cdsStores, CdsStoresBrand storesBrand) {
        Map params = new HashMap();
        params.put("stores_id", storesBrand.getStores_id());
        params.put("fromin", "美团");
        params.put("brand_id", storesBrand.getBrand_id());
        params.put("cday", DateUtil.getDate());
        List<Map> nolist = sqlDao.getRecordList("cds_order_info.getOrderNolist", params);
        int date_time = Integer.parseInt(DateUtil.dateToStr(new Date(), "yyyyMMdd"));//当天日期

        //最新流水号
        int new_fromin_no = 1;
        try {
            String new_formin_no_str = APIFactoryMeituan.getOrderAPI().getOrderDaySeq(SystemConfig.GetSystemParamMeituan(), String.valueOf(storesBrand.getStores_brand_id()));
            JSONObject jo2 = JSONObject.parseObject(new_formin_no_str);
            new_fromin_no = jo2.getInteger("day_seq");
        } catch (com.opensdk.meituan.exception.ApiOpException e) {
            e.printStackTrace();
        } catch (com.opensdk.meituan.exception.ApiSysException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= new_fromin_no; i++) {
            boolean flag = false;
            for (Map mp : nolist) {//系统订单编号
                int fromin_no = Integer.parseInt(mp.get("fromin_no").toString());
                if (i == fromin_no) {
                    flag = true;
                    break;
                }
            }

            if (flag == false) {
                try {
                    String data = APIFactoryMeituan.getOrderAPI().getOrderIdByDaySeq(SystemConfig.GetSystemParamMeituan(), String.valueOf(storesBrand.getStores_brand_id()), date_time, i);
                    if (data != null && !data.equals("")) {
                        JSONObject jo = JSONObject.parseObject(data);
                        if (jo.getString("result") != null && jo.getString("result").equals("ok")) {
                            long meituan_order_id = jo.getLong("order_id");

                            OrderDetailParam orderParam = APIFactoryMeituan.getOrderAPI().orderGetOrderDetail(SystemConfig.GetSystemParamMeituan(), meituan_order_id, 1);
                            CdsMember member = memberService.GetMember(orderParam, cdsStores);
                            CdsOrderInfo orderInfo = new CdsOrderInfo();
                            orderInfo.setMeitianParam(member, orderParam, cdsStores); //设置美团订单的相关信息
                            orderInfo.setOrder_no(storesService.getStoresNo(orderInfo, cdsStores.getBrand_fromno_start(), orderInfo.getCreate_date()));//获取订单流水号
                            orderInfo = giftService.addGiftGoods(member.getPhone(), orderInfo);
                            orderService.createOrder(orderInfo); //创建订单
                        }
                    }
                } catch (com.opensdk.meituan.exception.ApiOpException e) {
                    e.printStackTrace();
                } catch (com.opensdk.meituan.exception.ApiSysException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //饿了么补单
    private void elem_increased(CdsStores cdsStores, CdsStoresBrand storesBrand) {

        int eleme_stores_id = Integer.parseInt(cdsStores.getElem_restaurant_id());
        List<String> orders = null;
        try {
            //orders = APIFactoryEleme.getOrderAPI().orderPullNew(SystemConfig.GetSystemParamEleme(), eleme_stores_id);
            orders = APIFactoryEleme.getOrderAPI().orderBatchGet(SystemConfig.GetSystemParamEleme(),DateUtil.getDate(), eleme_stores_id);
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
        if (orders == null || orders.size() == 0) {
            return;
        }

        Map params = new HashMap();
        params.put("stores_id", storesBrand.getStores_id());
        params.put("fromin", "饿了么");
        params.put("brand_id", storesBrand.getBrand_id());
        params.put("cday", DateUtil.getDate());
        List<Map> nolist = sqlDao.getRecordList("cds_order_info.getOrderNolist", params);
        //判断订单是否存在
        for (int i = orders.size() - 1; i >= 0; i--) {//饿了么订单编号
            try {
                String eleme_order_id = orders.get(i);
                boolean flag = false;
                for (Map mp : nolist) {//系统订单编号
                    String order_desc = mp.get("order_desc").toString();
                    if (order_desc.equals(eleme_order_id)) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    OrderParam orderParam = APIFactoryEleme.getOrderAPI().orderGetOrderDetail(SystemConfig.GetSystemParamEleme(), eleme_order_id);
                    if (orderParam.getRestaurant_number() != null && !orderParam.getRestaurant_number().equals("0")) {
                        CdsMember member = memberService.GetMember(orderParam, cdsStores);
                        CdsOrderInfo orderInfo = new CdsOrderInfo();
                        orderInfo.setElemeParam(member, orderParam, cdsStores); //设置饿了么订单的相关信息
                        orderInfo.setOrder_no(storesService.getStoresNo(orderInfo, cdsStores.getBrand_fromno_start(), orderInfo.getCreate_date()));//获取订单流水号
                        orderInfo = giftService.addGiftGoods(member.getPhone(), orderInfo);
                        orderService.createOrder(orderInfo); //创建订单
                    }
                }
            } catch (ApiOpException e) {
                e.printStackTrace();
            } catch (ApiSysException e) {
                e.printStackTrace();
            }
        }
    }
}
