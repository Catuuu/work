package com.service;

import com.alibaba.fastjson.JSONObject;
import com.framework.service.BasicService;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/25 0025.
 * 同步打印和扫码服务
 */
@Service("SyncService")
public class SyncService extends BasicService {

    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void SyncPrints(List printList) throws Exception {
        sqlDao.updateRecord("cds_stores_order_info.syncPrints", printList);
    }

    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void SyncScanners(List scannerList) throws Exception {
        List normal_orders = new ArrayList();
        List finsh_orders = new ArrayList();

        for (Object scanner : scannerList) {
            Map map = new HashMap();
            JSONObject json = JSONObject.parseObject(scanner.toString());
            String pack_user_name = json.get("pack_user_name").toString();
            Integer pack_user_id = json.getInteger("pack_user_id");
            String order_id = json.get("order_id").toString();
            String pack_time = json.get("pack_time").toString();

            String finsh_order = getRedisString(3, "finsh_" + order_id);
            map.put("pack_user_name", pack_user_name);
            map.put("order_id", order_id);
            map.put("pack_user_id", pack_user_id);
            map.put("pack_time", pack_time);

            if (finsh_order == null) {
                normal_orders.add(map);
            }else{
                map.put("order_status", 4);
                finsh_orders.add(map);
            }
        }
        //判断当前订单是点我达骑手或微信堂食直接完成订单
       /* sqlDao.updateRecord("cds_stores_order_info.updateWX", finsh_orders);
        sqlDao.updateRecord("cds_stores_order_info.updateOrderInfo", normal_orders);*/

        Map params = new HashMap();
        params.put("finsh_orders",finsh_orders);
        params.put("normal_orders",normal_orders);
        sqlDao.updateRecord("cds_stores_order_info.updateOrderAll", params);

        //发送打包消息
        String orderstr = JSONObject.toJSONString(scannerList);
        jmsTemplate.send("order.packService", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(orderstr);
            }
        });

        //修改商品销售明细
        //sqlDao.updateRecord("cds_stores_order_info.updateGoodList", scanners);

    }
}
