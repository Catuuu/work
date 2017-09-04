package com.service;


import com.framework.mapping.system.CdsMember;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsStores;
import com.framework.service.BasicService;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.opensdk.eleme.vo.OrderParam;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by c on 2017-01-30.
 */
@Service("StoresService")
public class StoresService extends BasicService {
    /**
     * 通过商铺品牌ID获取商铺信息
     * @param stores_brand_id
     * @return
     */
    public CdsStores GetStores(int stores_brand_id) {
        String key = "stores_key_" + stores_brand_id;
        CdsStores cdsStores = getRedisBean(key, CdsStores.class);
        if (cdsStores == null) {
            cdsStores = sqlDao.getRecord("cds_brand.getStores", stores_brand_id);
            if (cdsStores != null) {
                addRedis(key, cdsStores, 10 * 60);
            }
        }
        return cdsStores;
    }

    /**
     * 通过商铺品牌ID获取商铺信息
     *
     * @param stores_id
     * @return
     */
    public CdsStores GetStoresById(int stores_id) {
        String key = "stores_key_" + stores_id;
        CdsStores cdsStores = getRedisBean(key, CdsStores.class);
        if (cdsStores == null) {
            Map paramMap = new HashMap();
            paramMap.put("stores_id",stores_id);
            cdsStores = sqlDao.getRecord("cds_stores.orderOnTimeStores", paramMap);
            if (cdsStores != null) {
                addRedis(key, cdsStores, 10 * 60);
            }
        }
        return cdsStores;
    }


    /**
     * 通过商铺品牌ID获取商铺信息
     *
     * @param stores_id  商铺ID
     * @param  brand_id  品牌ID
     * @return
     */
    public CdsStores GetStores(int stores_id,int brand_id) {
        String key = "stores_key_" + stores_id+"_"+brand_id;
        CdsStores cdsStores = getRedisBean(key, CdsStores.class);
        if (cdsStores == null) {
            Map param = new HashMap();
            param.put("stores_id",stores_id);
            param.put("brand_id",brand_id);

            cdsStores = sqlDao.getRecord("cds_brand.getBrandStores", param);
            if (cdsStores != null) {
                addRedis(key, cdsStores, 10 * 60);
            }
        }
        return cdsStores;
    }



    //获取流水号
    public String getStoresNo(CdsOrderInfo orderInfo, int brand_fromno_start, Date day) {
        String key = orderInfo.getFromin() + orderInfo.getOrder_desc();
        String cur_order_no = getRedisString(2, key);
        if (cur_order_no == null) {
            cur_order_no = getStoresNo(orderInfo.getStores_id(), orderInfo.getBrand_id(), brand_fromno_start, day);
            addRedis(2, key, cur_order_no, 10 * 60 * 60);
        }
        return cur_order_no;
    }

    /**
     * 获取门店是否执行补单
     * @return true 补单
     */
    public boolean isBudan(String key) {
        String last_budan = getRedisString(key);
        if (last_budan == null) {
            addRedis(key,"do",3*60);
            return true;
        }
        return false;
    }


    /**
     * 获取日期对应的商铺订单流水号
     *
     * @param stores_id          商铺ID
     * @param brand_id           品牌ID
     * @param brand_fromno_start 流水号开始
     * @param day                日期
     * @return
     */
    private String getStoresNo(int stores_id, int brand_id, int brand_fromno_start, Date day) {
        String storesid = String.valueOf(stores_id);
        String orderday = DateUtil.dateToStr(day);
        String order_no = StringUtil.dispRepairLeft(storesid, "0", 4) + "-";

        //String lock_key = "stores_no_lock_" + storesid + "_" + brand_id;
        String stores_key = "stores_no_" + storesid + "_" + brand_id + "_" + orderday;
       /* while (!lock(lock_key)) { //判断是否加锁,如已加锁，则等待1秒钟
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        String cur_order_no = getRedisString(stores_key);
        int cur_num_order_no = brand_fromno_start;//当前的
        if (cur_order_no == null) {
            Map paramMap = new HashMap();
            paramMap.put("cday", orderday);
            paramMap.put("stores_id", storesid);
            paramMap.put("brand_id", brand_id);
            Map ordernoMap = sqlDao.getRecord("cds_order_info.getOrderNo", paramMap);
            if (ordernoMap == null) {
                cur_num_order_no = brand_fromno_start;
            } else {
                cur_num_order_no = (Integer) ordernoMap.get("order_no");
            }
            cur_num_order_no++;
            addRedis(stores_key, String.valueOf(cur_num_order_no), 10 * 60 * 60);
        } else {
            cur_num_order_no = Integer.parseInt(cur_order_no);
            cur_num_order_no++;
            updateRedis(stores_key, String.valueOf(cur_num_order_no), 10 * 60 * 60);
        }
       // unlock(lock_key);
        order_no = order_no + StringUtil.dispRepairLeft(String.valueOf(cur_num_order_no), "0", 4);
        return order_no;
    }
}
