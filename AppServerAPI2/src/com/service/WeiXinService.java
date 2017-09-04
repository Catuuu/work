package com.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.CdsBrand;
import com.framework.mapping.system.CdsMember;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.service.BasicService;
import com.framework.util.DateUtil;
import com.opensdk.weixin.factory.APIFactoryWeixin;
import com.opensdk.weixin.vo.SystemParamWeixin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.service.MessageService.CodeType.CODE_WX_FINISH;
import static com.service.MessageService.CodeType.CODE_WX_SEND;

/**
 * Created by Administrator on 2017/4/15 0015.
 */
@Service("WeiXinService")
public class WeiXinService extends BasicService {
    @Resource(name = "MessageService")
    protected MessageService messageService;

    /**
     * 1.商家已接单通知
     */
    public void sendGetOrder(CdsOrderInfo cdsOrderInfo) {
        return;
//        HashMap formInfo = new HashMap();
//        String openid = getOpenid(cdsOrderInfo);
//        logger.error("sendGetOrder_openid=="+openid);
//        if (openid == null) {
//            return;
//        }
//        formInfo.put("openid", openid);
//        formInfo.put("order_id", cdsOrderInfo.getOrder_id());
//        formInfo.put("type", 1);
//
//        formInfo.put("goods", getGoods(cdsOrderInfo));
//        String date = DateUtil.dateToStr(cdsOrderInfo.getCreate_date(), "yyyy-MM-dd HH:mm:ss");
//        formInfo.put("create_date", date);
//        formInfo.put("brand_id", cdsOrderInfo.getBrand_id());
//
//        getWeixinUrl(formInfo);
    }

    /**
     * 2.订单配送提醒
     */
    public void sendTaskOrder(CdsOrderInfo cdsOrderInfo) {
        HashMap formInfo = new HashMap();
        CdsMember user = getOpenid(cdsOrderInfo);
        String phone = user.getPhone();
        String openid = user.getOpenid();
        cdsOrderInfo.setReceiver_phone(phone);
        //logger.error("sendTaskOrder_openid=="+openid);
        if (openid == null) {
            if (cdsOrderInfo.getBrand_id() == 3 || cdsOrderInfo.getBrand_id() == 4) {
                return;
            }
            messageService.sendMessage(CODE_WX_SEND, cdsOrderInfo);

//            Integer member_id = cdsOrderInfo.getMember_id();
//            String task_user_phone = cdsOrderInfo.getTask_user_phone();
//            String task_user_name = cdsOrderInfo.getTask_user_name();
//            String sms = "【菜大师】：美味已经出发！骑手：" + task_user_name + "，电话：" + task_user_phone + "，关注微信公众号“菜大师外卖”，查看骑手地图~！退订回T";
//            MessageUtil.sendMessage(phone, sms, "11", member_id);
            return;
        }
        formInfo.put("openid", openid);
        formInfo.put("order_id", cdsOrderInfo.getOrder_id());
        formInfo.put("type", 2);

        formInfo.put("task_name", cdsOrderInfo.getTask_user_name());
        formInfo.put("task_phone", cdsOrderInfo.getTask_user_phone());
        formInfo.put("brand_id", cdsOrderInfo.getBrand_id());
        formInfo.put("cdsOrderInfo", cdsOrderInfo);
        getWeixinUrl(formInfo);
    }

    /**
     * 3.订单完成通知
     */
    public void sendFinishOrder(CdsOrderInfo cdsOrderInfo) {
//        return;
        HashMap formInfo = new HashMap();
        CdsMember user = getOpenid(cdsOrderInfo);
        String phone = user.getPhone();
        String openid = user.getOpenid();
        cdsOrderInfo.setReceiver_phone(phone);
//        logger.error("sendFinishOrder_openid=="+openid);
        if (openid == null) {
            if (cdsOrderInfo.getBrand_id() == 3 || cdsOrderInfo.getBrand_id() == 4) {
                return;
            }
            messageService.sendMessage(CODE_WX_FINISH, cdsOrderInfo);

//            Integer member_id = cdsOrderInfo.getMember_id();
//            String sms = "【菜大师】：报告老板！您的餐品已送达，满意请打赏菜菜五星好评。若有不满意，请拨打客服专线4001148878，菜小妹帮您解决问题哟！退订回T";
//            MessageUtil.sendMessage(phone, sms, "12", member_id);
            return;
        }
        formInfo.put("openid", openid);
        formInfo.put("order_id", cdsOrderInfo.getOrder_id());
        formInfo.put("type", 3);
        String date = DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        formInfo.put("finish_date", date);
        formInfo.put("goods", getGoods(cdsOrderInfo));
        formInfo.put("brand_id", cdsOrderInfo.getBrand_id());
        formInfo.put("cdsOrderInfo", cdsOrderInfo);
        getWeixinUrl(formInfo);
    }

    /**
     * 4.订单取消通知
     */
    public void sendCancelOrder(CdsOrderInfo cdsOrderInfo) {
        return;
//        HashMap formInfo = new HashMap();
//        String openid = getOpenid(cdsOrderInfo);
//        logger.error("sendCancelOrder_openid=="+openid);
//        if (openid == null) {
//            return;
//        }
//        formInfo.put("openid", getOpenid(cdsOrderInfo));
//        formInfo.put("order_id", cdsOrderInfo.getOrder_id());
//        formInfo.put("type", 4);
//        String date = DateUtil.dateToStr(cdsOrderInfo.getCreate_date(), "yyyy-MM-dd HH:mm:ss");
//        formInfo.put("create_date", date);
//        formInfo.put("price", cdsOrderInfo.getTotal_price());
//        formInfo.put("goods", getGoods(cdsOrderInfo));
//        formInfo.put("brand_id", cdsOrderInfo.getBrand_id());
//        getWeixinUrl(formInfo);
    }

    /**
     * 5.订单出餐通知
     */
    public void sendOutOrder(CdsOrderInfo cdsOrderInfo, Integer type) {

        HashMap formInfo = new HashMap();
        String openid = getOpenid(cdsOrderInfo).getOpenid();
        logger.error("sendOutOrder_openid==" + openid);
        if (openid == null) {
            return;
        }
        formInfo.put("openid", openid);
        formInfo.put("order_id", cdsOrderInfo.getOrder_id());
        formInfo.put("type", type);
        String date = DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        formInfo.put("finish_date", date);
        formInfo.put("price", cdsOrderInfo.getTotal_price());
        formInfo.put("goods", getGoods(cdsOrderInfo));
        formInfo.put("brand_id", cdsOrderInfo.getBrand_id());
        getWeixinUrl(formInfo);
    }

    /**
     * 6.订单赠品出餐通知
     */
    public void sendGiftOrder(CdsOrderInfo cdsOrderInfo) {

        HashMap formInfo = new HashMap();
        String openid = getOpenid(cdsOrderInfo).getOpenid();
        logger.error("sendGiftOrder_openid==" + openid);
        if (openid == null) {
            return;
        }
        formInfo.put("openid", openid);
        formInfo.put("order_id", cdsOrderInfo.getOrder_id());
        formInfo.put("type", 7);
        String date = DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        formInfo.put("finish_date", date);
        formInfo.put("price", cdsOrderInfo.getTotal_price());
        formInfo.put("goods", getGoods(cdsOrderInfo));
        formInfo.put("brand_id", cdsOrderInfo.getBrand_id());
        getWeixinUrl(formInfo);
    }

    /**
     * 7.礼品登记成功通知
     */
    public void saveGiftOrder(CdsOrderInfo cdsOrderInfo) {

        HashMap formInfo = new HashMap();
        String openid = getOpenid(cdsOrderInfo).getOpenid();
        logger.error("saveGiftOrder_openid==" + openid);
        if (openid == null) {
            return;
        }
        formInfo.put("openid", openid);
        List<JSONObject> goodsJson = JSONArray.parseArray(cdsOrderInfo.getGoods(), JSONObject.class);
        goodsJson = goodsJson.stream()
                .filter(f -> !"".equals(f.getString("good_name")) && null != f.getString("good_name"))
                .collect(Collectors.toList());
        String goodsNames = "";
        Integer goodsCount = 0;
        for (JSONObject json : goodsJson) {
            goodsNames += json.getString("good_name") + " ";
            goodsCount += json.getInteger("quantity");
        }

        formInfo.put("gift_name", goodsNames);
        formInfo.put("gift_count", goodsCount);
        formInfo.put("type", 8);
        String date = DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        formInfo.put("gift_date", date);
        formInfo.put("brand_id", cdsOrderInfo.getBrand_id());
        getWeixinUrl(formInfo);
    }

    public String getWeixinUrl(HashMap formInfo) {
        Integer brand_id = Integer.parseInt(formInfo.get("brand_id").toString());
        Map params = getRedisMap("params_brand" + brand_id);
        if (params == null) {
            CdsBrand brand = new CdsBrand();
            brand.setBrand_id(brand_id);
            brand.addConditionField("brand_id");
            brand.addParamFields();
            brand = sqlDao.getRecord(brand);
            HashMap map = new HashMap();
            map.put("appid", brand.getWeixin_appid());
            map.put("appsecrest", brand.getWeixin_appsecret());
            addRedis("params_brand" + brand_id, map, 60 * 60);
        }
        SystemParamWeixin wxParams = new SystemParamWeixin(params.get("appid").toString(), params.get("appsecrest").toString());

        String result = "";
        String lock_key = "weixin_accesstoken_no_lock" + brand_id;
        String accesstoken_key = "weixin_accesstoken" + brand_id;

        String accesstoken = getRedisString(accesstoken_key);

        if (accesstoken == null) {
//            while (!lock(lock_key)) { //判断是否加锁,如已加锁，则等待1秒钟
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            unlock(lock_key);
            accesstoken = APIFactoryWeixin.getPoiAPI().accessToken(wxParams);
            addRedis(accesstoken_key, accesstoken, 60 * 60);
        }

        if (!accesstoken.equals("")) {
            int type = (Integer) formInfo.get("type");
            String result_code = "";

            switch (type) {
                case 1://商家已接单通知
                    APIFactoryWeixin.getPoiAPI().templateMessage1(accesstoken, formInfo);
                    break;
                case 2://订单配送提醒
                    result_code = APIFactoryWeixin.getPoiAPI().templateMessage2(accesstoken, formInfo);
                    //取消关注
                    if (result_code.equals("43004")) {
                        CdsOrderInfo cdsOrderInfo = (CdsOrderInfo) formInfo.get("cdsOrderInfo");
                        messageService.sendMessage(CODE_WX_SEND, cdsOrderInfo);
                    }
                    break;
                case 3://订单完成通知
                    result_code = APIFactoryWeixin.getPoiAPI().templateMessage3(accesstoken, formInfo);
                    //取消关注
                    if (result_code.equals("43004")) {
                        CdsOrderInfo cdsOrderInfo = (CdsOrderInfo) formInfo.get("cdsOrderInfo");
                        messageService.sendMessage(CODE_WX_FINISH, cdsOrderInfo);
                    }
                    break;
                case 4://订单取消通知
                    APIFactoryWeixin.getPoiAPI().templateMessage4(accesstoken, formInfo);
                    break;
                case 5://订单出餐通知
                case 6://订单出餐通知(自取)
                    APIFactoryWeixin.getPoiAPI().templateMessage5(accesstoken, formInfo);
                    break;
                case 7://订单赠品出餐通知
                    APIFactoryWeixin.getPoiAPI().templateMessage7(accesstoken, formInfo);
                    break;
                case 8://礼品领取成功通知
                    APIFactoryWeixin.getPoiAPI().templateMessage8(accesstoken, formInfo);
            }

        }
        return result;
    }

    public CdsMember getOpenid(CdsOrderInfo cdsOrderInfo) {
        CdsMember member = new CdsMember();
        member.setMember_id(cdsOrderInfo.getMember_id());
        member.addConditionField("member_id");
        member.addParamFields("openid,phone");
        CdsMember user = sqlDao.getRecord(member);
        return user;
    }

    public String getGoods(CdsOrderInfo cdsOrderInfo) {
        String goods = cdsOrderInfo.getGoods();
        List<Map> goodsJson = JSON.parseArray(goods, Map.class);
        String goodStr = "";
        for (Map goodJson : goodsJson) {
            goodStr = goodStr + goodJson.get("good_name") + "x" + goodJson.get("quantity") + "份" + " ";
        }
        return goodStr;
    }

    public static void main(String[] args) {
        String str = "[{\"good_name\":\"\",\"good_id\":100,\"good_type\":2,\"class_id\":3.5,\"price\":3.5,\"quantity\":1}," +
                "{\"good_name\":\"绿豆汤\",\"good_id\":100,\"good_type\":2,\"class_id\":3.5,\"price\":3.5,\"quantity\":2}]";
        List<JSONObject> goodsJson = JSONArray.parseArray(str, JSONObject.class);
        goodsJson = goodsJson.stream()
                .filter(f -> !"".equals(f.getString("good_name")) && null != f.getString("good_name"))
                .collect(Collectors.toList());
        String goodsNames = "";
        Integer goodsCount = 0;
        for (JSONObject json : goodsJson) {
            goodsNames += json.getString("good_name") + " ";
            goodsCount += json.getInteger("quantity");
        }

        System.out.println(goodsNames);
        System.out.println(goodsCount);

    }

}
