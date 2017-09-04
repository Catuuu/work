package com.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.CdsIncreaseOrder;
import com.framework.mapping.system.*;
import com.framework.service.BasicService;
import com.framework.system.SystemConfig;
import com.framework.util.CodeUtil;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.eleme.vo.OrderParam;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.meituan.vo.OrderDetailParam;
import com.opensdk.weixin.factory.APIFactoryWeixin;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.*;

/**
 * Created by Administrator on 2017/4/15 0015.
 */
@Service("PackService")
public class PackService extends BasicService {
    @Resource(name = "StoresService")
    protected StoresService storesService;
    @Resource(name = "MemberService")
    protected MemberService memberService;
    @Resource(name = "PrintService")
    protected PrintService printService;

    @Resource(name = "OrderService")
    protected OrderService orderService;

    /**
     * 打印
     *
     * @param cdsOrderInfo
     */

    public boolean printClient(CdsOrderInfo cdsOrderInfo, int type) {
        boolean isdebug = true;
        if (isdebug) {
            cdsOrderInfo.setPrint_type(type);//1系统打印,2手动
            String code = CodeUtil.getCode(cdsOrderInfo);//一维码
            cdsOrderInfo.setCode(code);
            cdsOrderInfo.setUrl(getWeixinUrl(code));//二维码url
            String orderstr = JSONObject.toJSONString(cdsOrderInfo);
            jmsTemplate.send("stores.print_" + cdsOrderInfo.getStores_id(), new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(orderstr);
                }
            });

            System.out.println("测试");
        } else {
            List<CdsPrints> cdsPrintsList = GetPrints2(cdsOrderInfo.getStores_id());
            for (CdsPrints prints : cdsPrintsList) {
                if (prints.getStatus() == 1 && prints.getPrint_model() == 2) {//网络打印机

                    cdsOrderInfo.setPrint_type(type);//1系统打印,2手动
                    String code = CodeUtil.getCode(cdsOrderInfo);//一维码
                    cdsOrderInfo.setCode(code);
                    cdsOrderInfo.setUrl(getWeixinUrl(code));//二维码url

                    String orderstr = JSONObject.toJSONString(cdsOrderInfo);
                    jmsTemplate.send("stores.print_" + cdsOrderInfo.getStores_id(), new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage(orderstr);
                        }
                    });
                }
            }
        }
        return true;
    }

    /**
     * 定时打印
     */
    public void IncreasedPrint() {
        //推送2min前未打印的订单
        CdsOrderInfo cdsOrderInfo = sqlDao.getRecord("cds_stores_prints.getUnPrintInfo");
        if(cdsOrderInfo==null){
            return;
        }
        List<CdsPrints> cdsPrintsList = GetPrints2(cdsOrderInfo.getStores_id());
        for (CdsPrints prints : cdsPrintsList) {
            if (prints.getStatus() == 1 && prints.getPrint_model() == 2) {//网络打印机
                cdsOrderInfo.setPrint_type(1);                //1系统打印,2手动
                String code = CodeUtil.getCode(cdsOrderInfo);//一维码
                cdsOrderInfo.setCode(code.split("_")[0]);
                cdsOrderInfo.setUrl(getWeixinUrl(code.split("_")[1]));//二维码url
                String orderstr = JSONObject.toJSONString(cdsOrderInfo);
                jmsTemplate.send("stores.print_" + cdsOrderInfo.getStores_id(), new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(orderstr);
                    }
                });
            }
        }
    }

    /**
     * 通过商铺ID获取打印机信息
     *
     * @param stores_id 商品关键词
     * @return
     * @throws Exception
     */
    public List<CdsPrints> GetPrints2(int stores_id) {
        String key = "print_key_" + stores_id;
        String lock_key = "print_key_lock_" + stores_id;
        String content = getRedisString(key);
        if (content == null) {
            CdsPrints cdsPrints = new CdsPrints();
            cdsPrints.setStores_id(stores_id);
            cdsPrints.addConditionField("stores_id");
            List<CdsPrints> cdsPrintsList = sqlDao.getRecordList(cdsPrints);
            CdsStores stores = new CdsStores();

            for (CdsPrints print : cdsPrintsList) {
                stores.getPrintsList().add(print);
            }
            while (!lock(lock_key)) { //判断是否加锁,如已加锁，则等待1秒钟
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            addRedis(key, JSONArray.toJSONString(stores.getPrintsList()), 10 * 60);
            unlock(lock_key);
            return stores.getPrintsList();
        }
        List<CdsPrints> cdsprints = new ArrayList<CdsPrints>();
        if (content != null) {
            cdsprints = JSONArray.parseArray(content, CdsPrints.class);
        }
        return cdsprints;
    }


    public String getWeixinUrl(String order_id) {
        String result = "";
        String lock_key = "weixin_accesstoken_no_lock";
        String accesstoken_key = "weixin_accesstoken";
        while (!lock(lock_key)) { //判断是否加锁,如已加锁，则等待1秒钟
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String accesstoken = getRedisString(accesstoken_key);

        if (accesstoken == null) {
            accesstoken = APIFactoryWeixin.getPoiAPI().accessToken(SystemConfig.GetSystemParamWeixin());
            addRedis(accesstoken_key, accesstoken, 60 * 60);
        }
        unlock(lock_key);
        if (!accesstoken.equals("")) {
            result = APIFactoryWeixin.getPoiAPI().createcode(accesstoken, order_id);//一维码
        }
        return result;
    }
}
