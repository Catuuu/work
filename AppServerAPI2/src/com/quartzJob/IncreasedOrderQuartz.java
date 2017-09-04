package com.quartzJob;

import com.alibaba.fastjson.JSONObject;
import com.framework.controller.BasicComponent;
import com.framework.mapping.CdsIncreaseOrder;
import com.framework.mapping.system.CdsStoresBrand;
import com.framework.util.DateUtil;
import com.service.GoodService;
import com.service.OrderService;
import com.service.StoresService;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/27.
 * 订时器处理程序
 */
@Component
public class IncreasedOrderQuartz extends BasicComponent {
    @Resource(name = "GoodService")
    protected GoodService goodService;

    //定时补单程序
    //@Scheduled(cron = "0/60 * *  * * ? ")   //每60秒执行一次
    public void IncreasedOrderDo() {
        Map params = new HashMap();
        params.put("cday", DateUtil.getDate());
        List<CdsIncreaseOrder> list = sqlDao.getRecordList("cds_order_info.getOrderSum", params);
        CdsStoresBrand cdsstoresBrand = new CdsStoresBrand();
        cdsstoresBrand.setStores_id(11);
        cdsstoresBrand.addConditionField("stores_id");
        List<CdsStoresBrand> stores_brand_list = sqlDao.getRecordList(cdsstoresBrand);

        for (CdsStoresBrand brand : stores_brand_list) {
            boolean elemeflag = false;
            boolean meituanflag = false;
            for (CdsIncreaseOrder inc : list) {
                if (brand.getStores_brand_id() == inc.getStores_brand_id()) {
                    if(inc.getFromin().equals("饿了么")){
                        elemeflag = true;
                    }else if(inc.getFromin().equals("美团")){
                        meituanflag = true;
                    }
                    brand.addCdsIncreaseOrder(inc);
                }
            }

            if (elemeflag == false) {
                CdsIncreaseOrder inc = new CdsIncreaseOrder();
                inc.setCday(DateUtil.getDate());
                inc.setStores_id(brand.getStores_id());
                inc.setBrand_id(brand.getBrand_id());
                inc.setStores_brand_id(brand.getStores_brand_id());
                inc.setFromin("饿了么");
                inc.setAll_count(0);
                inc.setMax_num(0);
                brand.addCdsIncreaseOrder(inc);
            }

            if(meituanflag == false){
                CdsIncreaseOrder inc = new CdsIncreaseOrder();
                inc.setCday(DateUtil.getDate());
                inc.setStores_id(brand.getStores_id());
                inc.setBrand_id(brand.getBrand_id());
                inc.setStores_brand_id(brand.getStores_brand_id());
                inc.setFromin("美团");
                inc.setAll_count(0);
                inc.setMax_num(0);
                brand.addCdsIncreaseOrder(inc);
            }

            String incstr = JSONObject.toJSONString(brand);
            jmsTemplate.send("budanorder.increased", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(incstr);
                }
            });
        }
    }

    //处理各门店昨天的用户数据
   // @Scheduled(cron = "0 1 0  * * ? ")   //每天执行一次
    public void IncreasedMemberReportDo() {
        sqlDao.insertRecord("sys_job.insertMemberRecord");
    }


    //处理销售商品数据，转至历史表中
   // @Scheduled(cron = "0 1,15 0 * * ? ")   // 每天0点01分，0点15分
    public void IncreasedMsGoods() {
        goodService.changeGoodTable();
    }





    /*@Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo1(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=1");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo2(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=2");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo3(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=3");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo4(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=4");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo5(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=5");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo6(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=6");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo7(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=7");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo8(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=8");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo9(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=9");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo10(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=10");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo11(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=11");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo12(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=12");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo13(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=13");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo14(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=14");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo15(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=15");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo16(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=16");
        System.out.println("10、"+result);
    }
    @Scheduled(cron="0/20 * *  * * ? ")   //每60秒执行一次
    public void shhouOrderDo17(){
        //SystemParamEleme sysPramEleme = SystemConfig.GetSystemParamEleme();
        //logger.info("**********测试跑批类************");
        String result = HttpUtil.getRequest("http://127.0.0.1/api/CdsShengHou/battchdoorder?store_area=17");
        System.out.println("10、"+result);
    }*/
}
