package com.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.CdsIncreaseOrder;
import com.framework.mapping.system.*;
import com.framework.service.BasicService;
import com.framework.system.SystemConfig;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.eleme.vo.OrderParam;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.meituan.vo.FoodSkuStockParam;
import com.opensdk.meituan.vo.OrderDetailParam;
import com.opensdk.meituan.vo.skuStockParam;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.*;


/**
 * Created by c on 2017-01-30.
 * 订单操作相关信息
 */
@Service("OrderService")
public class OrderService extends BasicService {

    @Resource(name = "GoodService")
    protected GoodService GoodService;

    @Resource(name = "StoresService")
    protected StoresService storesService;
    @Resource(name = "MemberService")
    protected MemberService memberService;

    @Resource(name = "SalesStockService")
    protected SalesStockService salesStockService;

    /**
     * 数据库保存订单对象
     *
     * @param orderInfo 订单对象
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void createOrder(CdsOrderInfo orderInfo) {
        orderGoodDo2(orderInfo);//处理商品明细信息
        orderInfo.addParamFields();
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(orderInfo.getOrder_id());
        cdsOrderLog.setOpt_type("用户");
        cdsOrderLog.setOrder_status(0);
        cdsOrderLog.setOrder_status_chi("商家待接单");
        cdsOrderLog.setOpt_note(orderInfo.getReceiver_name() + "支付成功");
        cdsOrderLog.setOpt_name(orderInfo.getReceiver_name());
        cdsOrderLog.setOpt_time(orderInfo.getPay_time());
        cdsOrderLog.addParamFields();

        sqlDao.insertRecord(orderInfo);//保存订单信息
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志

        orderInfo.removeConditionFields();
        orderInfo.removeParamFields();

        String orderstr = JSONObject.toJSONString(orderInfo);


        //给饿了么、美团发送商家接单信息
        jmsTemplate.send("order.receive", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });
        //更新用户信息,修改用户的订餐数量
        jmsTemplate.send("order.member", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });

        //发送配送信息
        if (orderInfo.getIs_sync() == 1) {
            //发送配送信息
            jmsTemplate.send("order.send", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(orderstr);
                }
            });
        }
        //计算订单配送距离
        jmsTemplate.send("order.sendDistance", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });


        //对订单的商品进行拆分处理
        jmsTemplate.send("order.good", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });

        //打印小票
        /*jmsTemplate.send("order.print", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });*/
    }


    /**
     * 数据库保存订单对象
     *
     * @param orderInfo 订单对象(饿了么2.0)
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void createOrderNew(CdsOrderInfo orderInfo) {
        orderGoodDo2(orderInfo);//处理商品明细信息
        orderInfo.addParamFields();
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(orderInfo.getOrder_id());
        cdsOrderLog.setOpt_type("用户");
        cdsOrderLog.setOrder_status(0);
        cdsOrderLog.setOrder_status_chi("商家待接单");
        cdsOrderLog.setOpt_note(orderInfo.getReceiver_name() + "支付成功");
        cdsOrderLog.setOpt_name(orderInfo.getReceiver_name());
        cdsOrderLog.setOpt_time(orderInfo.getPay_time());
        cdsOrderLog.addParamFields();

        sqlDao.insertRecord(orderInfo);//保存订单信息
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志

        orderInfo.removeConditionFields();
        orderInfo.removeParamFields();

        String orderstr = JSONObject.toJSONString(orderInfo);


        //给饿了么、美团发送商家接单信息
        jmsTemplate.send("order.receiveNew", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });
        //更新用户信息,修改用户的订餐数量
        jmsTemplate.send("order.member", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });

        //发送配送信息
        if (orderInfo.getIs_sync() == 1) {
            //发送配送信息
            jmsTemplate.send("order.send", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(orderstr);
                }
            });
        }
        //计算订单配送距离
        jmsTemplate.send("order.sendDistance", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });


        //对订单的商品进行拆分处理
        jmsTemplate.send("order.good", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });

        //打印小票
        /*jmsTemplate.send("order.print", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });*/
    }


    /**
     * 修改订单的状态为商家已确认接单
     *
     * @param orderInfo 订单对象
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void shopReceiveOrder(CdsOrderInfo orderInfo) {
        orderInfo.setOrder_status(1);
        orderInfo.resetParamField("order_status");
        orderInfo.addConditionField("order_id");
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(orderInfo.getOrder_id());
        cdsOrderLog.setOpt_type("商家接单");
        cdsOrderLog.setOrder_status(1);
        cdsOrderLog.setOrder_status_chi("商家已接单");
        cdsOrderLog.setOpt_note("商家已确认接单");
        cdsOrderLog.setOpt_name("系统处理");
        cdsOrderLog.setOpt_time(new Date());
        cdsOrderLog.addParamFields();

        sqlDao.updateRecord(orderInfo);//保存订单信息
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志
    }


    /**
     * 新版本订单商品拆分处理
     *
     * @param orderInfo
     */
    public void orderGoodDo2(CdsOrderInfo orderInfo) {
        if (orderInfo.getGoods() != null && !orderInfo.getGoods().equals("")) {
            List<CdsGood> cdsGoods = JSONArray.parseArray(orderInfo.getGoods(), CdsGood.class);
            List<CdsMsGoodsList> insertList = new ArrayList<CdsMsGoodsList>();//匹配商品
            Map<String, CdsMsGoodsList> insertMap = new HashMap<String, CdsMsGoodsList>();
            List<CdsNosetGoodsList> nosetGoodsLists = new ArrayList<CdsNosetGoodsList>();//未匹配商品
            List goodinfoList = new ArrayList();//商品拆分明细
            for (CdsGood cdsGood : cdsGoods) {

                //新版本通过多规格获取ERP商品信息
                Map goodMap = GoodService.GetGoodInfo(orderInfo.getStores_id(), orderInfo.getBrand_id(), cdsGood.getGood_name());
                if (goodMap == null) {
                    goodMap = GoodService.GetGoodInfo(cdsGood.getGood_name());
                }
                if(goodMap == null){
                    goodMap = GoodService.GetGoodInfo(orderInfo.getBrand_id(),cdsGood.getGood_name());
                }

                //Map goodMap = GoodService.GetGoodInfo(cdsGood.getGood_name());
                if (goodMap != null) {
                    //float sum_price = Float.parseFloat(goodMap.get("sum_price").toString());
                    List<Map> erpgoods = (List) goodMap.get("erpgoods");
                    for (Map erpgood : erpgoods) {
                        String good_id = erpgood.get("good_id").toString();

                        CdsMsGoodsList cdsMsGoodsList = insertMap.get(good_id+ "_" + cdsGood.getGood_type());
                        if (cdsMsGoodsList == null) {
                            int count = Integer.parseInt(erpgood.get("good_count").toString());
                            //根据销售情况重新分配库存
                            cdsMsGoodsList = new CdsMsGoodsList();
                            cdsMsGoodsList.setGood_list_id(StringUtil.getPrimaryOrderKey());
                            cdsMsGoodsList.setStores_id(orderInfo.getStores_id());
                            cdsMsGoodsList.setOrder_id(orderInfo.getOrder_id());
                            cdsMsGoodsList.setGood_name(erpgood.get("class_good_name").toString());
                            cdsMsGoodsList.setCount(cdsGood.getQuantity() * Integer.parseInt(erpgood.get("good_count").toString()));
                            cdsMsGoodsList.setSale_time(orderInfo.getCreate_date());
                            cdsMsGoodsList.setBrand_id(orderInfo.getBrand_id());
                            cdsMsGoodsList.setPrice(0);
                            cdsMsGoodsList.setGood_id(Integer.parseInt(erpgood.get("good_id").toString()));
                            cdsMsGoodsList.setClass_good_name(erpgood.get("class_good_name").toString());
                            cdsMsGoodsList.setClass_id(Integer.parseInt(erpgood.get("class_id").toString()));
                            cdsMsGoodsList.setGood_type(cdsGood.getGood_type());
                            if (erpgood.get("good_price") != null) {
                                cdsMsGoodsList.setPrice(cdsMsGoodsList.getCount() * (Float.parseFloat(erpgood.get("good_price").toString())));
                            }
                            insertMap.put(good_id + "_" + cdsGood.getGood_type(), cdsMsGoodsList);
                        } else {
                            cdsMsGoodsList.setCount(cdsMsGoodsList.getCount() + cdsGood.getQuantity() * Integer.parseInt(erpgood.get("good_count").toString()));
                            if (erpgood.get("good_price") != null) {
                                cdsMsGoodsList.setPrice(cdsMsGoodsList.getCount() * (Float.parseFloat(erpgood.get("good_price").toString())));
                            }
                            insertMap.put(good_id + "_" + cdsGood.getGood_type(), cdsMsGoodsList);
                        }

                    }
                } else {
                    CdsNosetGoodsList cdsNosetGoodsList = new CdsNosetGoodsList();
                    cdsNosetGoodsList.setNgl_id(StringUtil.getPrimaryKey());
                    cdsNosetGoodsList.setOrder_id(orderInfo.getOrder_id());
                    cdsNosetGoodsList.setGood_name(cdsGood.getGood_name());
                    cdsNosetGoodsList.setGood_info(JSONObject.toJSONString(cdsGood));
                    cdsNosetGoodsList.setCreate_date(orderInfo.getCreate_date());
                    cdsNosetGoodsList.setBrand_id(orderInfo.getBrand_id());
                    cdsNosetGoodsList.setStores_id(orderInfo.getStores_id());

                    Map good = new HashMap();
                    good.put("good_name", cdsGood.getGood_name());
                    good.put("quantity", cdsGood.getQuantity());
                    good.put("good_type", cdsGood.getGood_type());
                    goodinfoList.add(good);

                    nosetGoodsLists.add(cdsNosetGoodsList);
                }
            }


            for (String key : insertMap.keySet()) {
                CdsMsGoodsList cdsMsGoodsList = insertMap.get(key);//得到每个key多对用value的值
                insertList.add(cdsMsGoodsList);

                Map good = new HashMap();
                good.put("good_name", cdsMsGoodsList.getGood_name());
                good.put("quantity", cdsMsGoodsList.getCount());
                good.put("good_type", cdsMsGoodsList.getGood_type());
                goodinfoList.add(good);
            }
            if (nosetGoodsLists.size() > 0) {
                sqlDao.insertRecord("cds_good.insertBatNosetGoodsList", nosetGoodsLists);
            }
            orderInfo.setGoods_info(JSONObject.toJSONString(goodinfoList));
            orderInfo.setMs_goods_info(JSONObject.toJSONString(insertList));
            //批量插入数据库
            //insertBatGoods(insertList, nosetGoodsLists);
        }
    }


    /**
     * 订单商品进行拆分处理
     *
     * @param orderInfo
     */
    public void orderGoodDo(CdsOrderInfo orderInfo) {
        if (orderInfo.getGoods() != null && !orderInfo.getGoods().equals("")) {
            List<CdsGood> cdsGoods = JSONArray.parseArray(orderInfo.getGoods(), CdsGood.class);
            List<CdsMsGoodsList> insertList = new ArrayList<CdsMsGoodsList>();//匹配商品
            List<CdsNosetGoodsList> nosetGoodsLists = new ArrayList<CdsNosetGoodsList>();//未匹配商品
            for (CdsGood cdsGood : cdsGoods) {
                //新版本通过多规格获取ERP商品信息
                Map goodMap = GoodService.GetGoodInfo(orderInfo.getStores_id(), orderInfo.getBrand_id(), cdsGood.getGood_name());
                if (goodMap == null) {
                    goodMap = GoodService.GetGoodInfo(cdsGood.getGood_name());
                }

                //Map goodMap = GoodService.GetGoodInfo(cdsGood.getGood_name());
                if (goodMap != null) {
                    //float sum_price = Float.parseFloat(goodMap.get("sum_price").toString());
                    List<Map> erpgoods = (List) goodMap.get("erpgoods");
                    for (Map erpgood : erpgoods) {
                        String good_id = erpgood.get("good_id").toString();
                        int count = Integer.parseInt(erpgood.get("good_count").toString());
                        //根据销售情况重新分配库存
//                        salesStockService.salesCountAllot(orderInfo,good_id,count);
                        CdsMsGoodsList cdsMsGoodsList = new CdsMsGoodsList();
                        cdsMsGoodsList.setGood_list_id(StringUtil.getPrimaryOrderKey());
                        cdsMsGoodsList.setStores_id(orderInfo.getStores_id());
                        cdsMsGoodsList.setOrder_id(orderInfo.getOrder_id());
                        cdsMsGoodsList.setGood_name(erpgood.get("class_good_name").toString());
                        cdsMsGoodsList.setCount(cdsGood.getQuantity() * Integer.parseInt(erpgood.get("good_count").toString()));
                        cdsMsGoodsList.setSale_time(orderInfo.getCreate_date());
                        cdsMsGoodsList.setBrand_id(orderInfo.getBrand_id());
                        cdsMsGoodsList.setPrice(0);
                        cdsMsGoodsList.setGood_id(Integer.parseInt(erpgood.get("good_id").toString()));
                        cdsMsGoodsList.setClass_good_name(erpgood.get("class_good_name").toString());
                        cdsMsGoodsList.setClass_id(Integer.parseInt(erpgood.get("class_id").toString()));
                        if (erpgood.get("good_price") != null) {
                            cdsMsGoodsList.setPrice((Float.parseFloat(erpgood.get("good_price").toString())));
                        }
                        insertList.add(cdsMsGoodsList);
                    }
                } else {
                    CdsNosetGoodsList cdsNosetGoodsList = new CdsNosetGoodsList();
                    cdsNosetGoodsList.setNgl_id(StringUtil.getPrimaryKey());
                    cdsNosetGoodsList.setOrder_id(orderInfo.getOrder_id());
                    cdsNosetGoodsList.setGood_name(cdsGood.getGood_name());
                    cdsNosetGoodsList.setGood_info(JSONObject.toJSONString(cdsGood));
                    cdsNosetGoodsList.setCreate_date(orderInfo.getCreate_date());
                    cdsNosetGoodsList.setBrand_id(orderInfo.getBrand_id());
                    cdsNosetGoodsList.setStores_id(orderInfo.getStores_id());
                    nosetGoodsLists.add(cdsNosetGoodsList);
                }
            }

            //批量插入数据库
            insertBatGoods(insertList, nosetGoodsLists);
        }
    }


    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void insertBatGoods(List<CdsMsGoodsList> insertList, List<CdsNosetGoodsList> nosetGoodsLists) {
        sqlDao.insertRecord("cds_good.insertBatGoodRecord", insertList);
        sqlDao.insertRecord("cds_good.insertBatNosetGoodsList", nosetGoodsLists);
    }

    public void sendOrder(CdsOrderInfo orderInfo, String result) {
        sendOrder(orderInfo, result, null);
    }

    /**
     * 配送信息保存
     *
     * @param orderInfo 订单对象
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void sendOrder(CdsOrderInfo orderInfo, String result, CdsUsers user) {
        Date d = new Date();
        JSONObject jo = JSONObject.parseObject(result);
        CdsOrderSendLog log = new CdsOrderSendLog();
        log.setSend_id(orderInfo.getSend_id());
        log.setOrder_id(orderInfo.getOrder_id());
        log.setSend_type(orderInfo.getSend_name());
        log.setOpt_time(d);

        if (user == null) {
            log.setOpt_name("系统操作");
        } else {
            log.setOpt_name(user.getUser_nicename());
            log.setOpt_id(user.getId());
        }


        if (jo.getString("code").equals("100000")) {
            orderInfo.setIs_sync(2);
            orderInfo.setSend_time(d);
            orderInfo.setSend_exception("");
            orderInfo.resetParamFields("is_sync,send_time,send_id,send_name,send_exception");
            orderInfo.addConditionField("order_id");
            log.setOpt_type(1);

        } else {
            orderInfo.setIs_sync(1);
            orderInfo.setSend_time(d);
            orderInfo.setSend_exception(jo.getString("message"));
            orderInfo.resetParamFields("is_sync,send_time,send_id,send_exception,send_name");
            orderInfo.addConditionField("order_id");
            log.setOpt_type(9);
            log.setOpt_note(jo.getString("message"));
        }

        log.addParamFields();

        sqlDao.updateRecord(orderInfo);//保存订单信息
        sqlDao.insertRecord(log);//插入订单操作日志
    }


    public void sendDwdOrder(CdsOrderInfo orderInfo, String result) {
        sendDwdOrder(orderInfo, result, null);
    }

    /**
     * 点我达配送信息保存
     *
     * @param orderInfo 订单对象
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void sendDwdOrder(CdsOrderInfo orderInfo, String result, CdsUsers user) {
        Date d = new Date();
        JSONObject jo = JSONObject.parseObject(result);
        CdsOrderSendLog log = new CdsOrderSendLog();
        log.setSend_id(orderInfo.getSend_id());
        log.setOrder_id(orderInfo.getOrder_id());
        log.setSend_type(orderInfo.getSend_name());
        log.setOpt_time(d);

        if (user == null) {
            log.setOpt_name("系统操作");
        } else {
            log.setOpt_name(user.getUser_nicename());
            log.setOpt_id(user.getId());
        }


        if (jo.getString("success").equals("true")) {
            orderInfo.setIs_sync(2);
            orderInfo.setSend_time(d);
            orderInfo.setSend_exception("");
            orderInfo.resetParamFields("is_sync,send_time,send_id,send_name,send_exception");
            orderInfo.addConditionField("order_id");
            log.setOpt_type(1);

        } else {
            orderInfo.setIs_sync(1);
            orderInfo.setSend_time(d);
            orderInfo.setSend_exception(jo.getString("message"));
            orderInfo.resetParamFields("is_sync,send_time,send_id,send_exception,send_name");
            orderInfo.addConditionField("order_id");
            log.setOpt_type(9);
            log.setOpt_note(jo.getString("message"));
        }

        log.addParamFields();

        sqlDao.updateRecord(orderInfo);//保存订单信息
        sqlDao.insertRecord(log);//插入订单操作日志
    }

    /**
     * 配送信息取消
     *
     * @param orderInfo 订单对象
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void sendCancel(CdsOrderInfo orderInfo, CdsUsers user) {
        Date d = new Date();
        CdsOrderSendLog log = new CdsOrderSendLog();
        log.setSend_id(StringUtil.getPrimaryOrderKey());
        log.setOrder_id(orderInfo.getOrder_id());
        log.setSend_type(orderInfo.getSend_name());
        log.setOpt_note("取消订单编号：" + orderInfo.getSend_id());
        log.setOpt_time(d);

        if (user == null) {
            log.setOpt_name("系统操作");
        } else {
            log.setOpt_name(user.getUser_nicename());
            log.setOpt_id(user.getId());
        }
        orderInfo.setSend_id(null);

        orderInfo.setIs_sync(0);
        orderInfo.setSend_time(d);
        orderInfo.resetParamFields("is_sync,send_time,send_id,send_name");
        orderInfo.addConditionField("order_id");
        log.setOpt_type(1);

        log.addParamFields();

        sqlDao.updateRecord(orderInfo);//保存订单信息
        sqlDao.insertRecord(log);//插入订单操作日志
    }


    /**
     * 取消订单
     *
     * @param orderInfo 订单对象
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void cancelorder(CdsOrderInfo orderInfo, CdsUsers user) {
        orderInfo.setOrder_status(99);
        orderInfo.resetParamFields("order_status,cancel_type,cancel_remark");
        orderInfo.addConditionField("order_id");
        CdsOrderLog cdsOrderLog = new CdsOrderLog();
        cdsOrderLog.setOl_id(StringUtil.getPrimaryOrderKey());
        cdsOrderLog.setOrder_id(orderInfo.getOrder_id());

        cdsOrderLog.setOrder_status(99);
        cdsOrderLog.setOrder_status_chi("订单取消");
        cdsOrderLog.setOpt_note(orderInfo.getCancel_type());
        if (user == null) {
            cdsOrderLog.setOpt_type("系统");
            cdsOrderLog.setOpt_name("系统操作");
        } else {
            cdsOrderLog.setOpt_type("员工");
            cdsOrderLog.setOpt_name(user.getUser_nicename());
        }
        cdsOrderLog.setOpt_time(new Date());
        cdsOrderLog.addParamFields();

        sqlDao.updateRecord(orderInfo);//保存订单信息
        sqlDao.insertRecord(cdsOrderLog);//插入订单操作日志
    }

    /**
     * 订单补单
     *
     * @param day 补单日期
     *//*
    public void IncreasedOrder(String day) {
        Map params = new HashMap();
        params.put("cday", day);
        List<CdsIncreaseOrder> list = sqlDao.getRecordList("cds_order_info.getOrderSum", params);
        for (CdsIncreaseOrder inc : list) {
            if (inc.getAll_count() != inc.getMax_num() && inc.getFromin().equals("饿了么")) {
                String incstr = JSONObject.toJSONString(inc);
                //发送饿了么补单消息
                jmsTemplate.send("elemeorder.increased", new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(incstr);
                    }
                });
                //elem_increase( inc);
            } else if (inc.getAll_count() != inc.getMax_num() && inc.getFromin().equals("美团")) {
                String incstr = JSONObject.toJSONString(inc);
                //发送美团补单消息
                jmsTemplate.send("meituanorder.increased", new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(incstr);
                    }
                });
                //maituan_increase(inc);
            }
        }
    }*/
}
