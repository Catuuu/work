package com.service;


import com.framework.mapping.system.CdsMsGoodStandard;
import com.framework.mapping.system.CdsMsGoods;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsStores;
import com.framework.service.BasicService;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by c on 2017-01-30.
 */
@Service("GoodService")
public class GoodService extends BasicService {

    public Map GetGoodInfo(String key) {
        Map cdsMSGood = getRedisBean(key, HashMap.class, 1);
        if (cdsMSGood == null) {
            Map paramMap = new HashMap();
            paramMap.put("good_name", key);
            cdsMSGood = sqlDao.getRecord("cds_ms_good_standard.getGoodInfo", paramMap);
            if (cdsMSGood != null) {
                List<Map> erpgoods = sqlDao.getRecordList("cds_ms_good_standard.getErpGoods", cdsMSGood);
                float sum_price = 0.0f;
                for (Map erpgood : erpgoods) {
                    sum_price += Float.parseFloat(erpgood.get("good_price").toString());
                }
                cdsMSGood.put("erpgoods", erpgoods);
                cdsMSGood.put("sum_price", sum_price);
                this.addRedis(1, key, cdsMSGood, 60 * 60);
            }
        }
        return cdsMSGood;
    }

    public Map GetGoodInfo(int stores_id, int brand_id, String good_name) {
        String key = stores_id + "_" + brand_id + "_" + good_name;
        Map cdsMSGood = getRedisBean(key, HashMap.class, 1);
        if (cdsMSGood == null) {
            Map paramMap = new HashMap();
            paramMap.put("good_name", good_name);
            paramMap.put("stores_id", stores_id);
            paramMap.put("brand_id", brand_id);

            List<Map> erpgoods = sqlDao.getRecordList("cds_ms_good_standard.getStoresErpGoods", paramMap);
            if (erpgoods.size() > 0) {
                float sum_price = 0.0f;
                for (Map erpgood : erpgoods) {
                    sum_price += Float.parseFloat(erpgood.get("good_price").toString());
                }
                cdsMSGood = new HashMap();
                cdsMSGood.put("good_name", good_name);
                cdsMSGood.put("stores_id", stores_id);
                cdsMSGood.put("brand_id", brand_id);
                cdsMSGood.put("erpgoods", erpgoods);
                cdsMSGood.put("sum_price", sum_price);
                this.addRedis(1, key, cdsMSGood, 60 * 60);
            }
        }
        return cdsMSGood;
    }


    public Map GetGoodInfo(int brand_id, String good_name) {
        String key = brand_id + "_" + good_name;
        Map cdsMSGood = getRedisBean(key, HashMap.class, 1);
        if (cdsMSGood == null) {
            Map paramMap = new HashMap();
            paramMap.put("good_name", good_name);
            paramMap.put("brand_id", brand_id);

            List<Map> erpgoods = sqlDao.getRecordList("cds_ms_good_standard.getErpNameGoods", paramMap);
            if (erpgoods.size() > 0) {
                float sum_price = 0.0f;
                for (Map erpgood : erpgoods) {
                    sum_price += Float.parseFloat(erpgood.get("good_price").toString());
                }
                cdsMSGood = new HashMap();
                cdsMSGood.put("good_name", good_name);
                cdsMSGood.put("brand_id", brand_id);
                cdsMSGood.put("erpgoods", erpgoods);
                cdsMSGood.put("sum_price", sum_price);
                this.addRedis(1, key, cdsMSGood, 60 * 60);
            }
        }
        return cdsMSGood;
    }

    /**
     * 把昨天的商品数据放至历史表中
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void changeGoodTable() {
        sqlDao.insertRecord("cds_ms_good_standard.changeGoodTable");
        sqlDao.deleteRecord("cds_ms_good_standard.deleteGoodTable");
    }
}
