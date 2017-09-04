package com.framework.annotation;

/**
 * Created by Administrator on 2017/8/23.
 * 收费类型
 */
public enum ChargeType {
    短信,//短信()
    微信,//微信模板消息
    订单抓取,
    打包出餐, //订单已出餐
    厨房出单  //通过App下达一单任务
}
