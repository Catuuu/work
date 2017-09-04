package com.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.CdsGiftRegisterList;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.service.BasicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
@Service("GiftService")
public class GiftService extends BasicService {
    @Resource
    private WeiXinService weiXinService;

    public CdsOrderInfo addGiftGoods(String phone, CdsOrderInfo orderInfo) {
        //该用户总共未领取的赠品
        CdsGiftRegisterList gift = new CdsGiftRegisterList();
        gift.setPhone(phone);
        gift.setSend_type(0);
        gift.setIs_check(1);//已审核
        gift.addConditionFields("phone,send_type,is_check");
        List<CdsGiftRegisterList> giftlist = sqlDao.getRecordList(gift);
        if (giftlist == null) {
            return orderInfo;
        }

        String old_goods_json = orderInfo.getGoods();
        List old_goodsList = JSONArray.parseArray(old_goods_json);

        for (CdsGiftRegisterList gift1 : giftlist) {
            gift1.setSend_type(1);
            gift1.setGet_time(new Date());
            gift1.setSend_order_id(orderInfo.getOrder_id());
            gift1.resetParamFields("send_order_id,send_type,get_time");
            gift1.addConditionField("id");
            sqlDao.updateRecord(gift1);

            List goodsList = JSONArray.parseArray(gift1.getGoods().toString());
            for (Object goods : goodsList) {
                JSONObject json = JSONObject.parseObject(goods.toString());
                old_goodsList.add(json);
            }
        }

        orderInfo.setGoods(JSONArray.toJSONString(old_goodsList));
        //发送赠品出餐微信消息
        weiXinService.sendGiftOrder(orderInfo);

        return orderInfo;
    }

}
