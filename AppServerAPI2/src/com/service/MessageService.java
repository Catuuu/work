package com.service;

import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsStores;
import com.framework.service.BasicService;
import com.framework.util.MessageUtil;
import org.springframework.stereotype.Service;


/**
 * Created by Administrator on 2017/7/26 0026.
 */
@Service("MessageService")
public class MessageService extends BasicService {
    public enum CodeType {
        CODE_WX_SEND, CODE_WX_FINISH
    }

    /**
     * 短信服务
     *
     * @param cdsOrderInfo
     */
    public void sendMessage(CodeType type, CdsOrderInfo cdsOrderInfo){
        String task_user_phone = cdsOrderInfo.getTask_user_phone();
        String task_user_name = cdsOrderInfo.getTask_user_name();
        Integer member_id = cdsOrderInfo.getMember_id();
        Integer stores_id = cdsOrderInfo.getStores_id();
        String fromin = cdsOrderInfo.getFromin();
        String phone = cdsOrderInfo.getReceiver_phone();

        String sms1 = "【菜大师】：美味已经出发！骑手：" + task_user_name + "，电话：" + task_user_phone + "，关注微信公众号“菜大师外卖”，查看骑手地图~！";
        String sms2 = "【菜大师】：报告老板！您的餐品已送达，满意请打赏菜菜五星好评。若有不满意，请拨打客服专线4001148878，菜小妹帮您解决问题哟！";

        StringBuffer contextString1 = new StringBuffer(sms1);
        StringBuffer contextString2 = new StringBuffer(sms2);

        CdsStores stores = new CdsStores();
        stores.setStores_id(stores_id);
        stores.addParamField("code_extinfo");
        stores.addConditionField("stores_id");
        stores = sqlDao.getRecord(stores);

        JSONObject code_extinfo = JSONObject.parseObject(stores.getCode_extinfo());

        /*Integer code_type1 = code_extinfo.getInteger("code_type1");//饿了么
        Integer code_type2 = code_extinfo.getInteger("code_type2");//美团
        Integer code_type3 = code_extinfo.getInteger("code_type3");//百度外卖
        Integer code_type4 = code_extinfo.getInteger("code_type4");//微信*/

        switch (fromin) {
            case "饿了么":
                fromin = "code_type1";
                break;

            case "美团":
                fromin = "code_type2";
                break;

            case "百度外卖":
                fromin = "code_type3";
                break;

            case "微信":
                fromin = "code_type4";
                break;
        }

        Integer code_type = code_extinfo.getInteger(fromin);

        switch (code_type) {
            case 0:
                return;

            case 1:
                //1、短信网
                switch (type) {
                    case CODE_WX_SEND:
                        MessageUtil.sendMessage1(phone, sms1, "13", member_id);
                        break;
                    case CODE_WX_FINISH:
                        MessageUtil.sendMessage1(phone, sms2, "14", member_id);
                        break;
                }
                break;

            case 2:
                //2、昊博(现用)
                switch (type) {
                    case CODE_WX_SEND:
                        MessageUtil.sendMessage(phone, sms1, "11", member_id);
                        break;
                    case CODE_WX_FINISH:
                        MessageUtil.sendMessage(phone, sms2, "12", member_id);
                        break;
                }
                break;
        }
    }
}
