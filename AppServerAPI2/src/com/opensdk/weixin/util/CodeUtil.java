package com.opensdk.weixin.util;

import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.opensdk.weixin.vo.WeixinCode;

import java.text.ParseException;

/**
 * Created by Administrator on 2017/5/9.
 */
public class CodeUtil {
    private static String day = "2017-05-09";

    //生成微信唯一标识流水号
    public static long create_code(String create_day, int stores_id, int no) {
        long winxin_order_no = 0;
        try {
            long cday = DateUtil.getBetweenDay(DateUtil.strToDate(day), DateUtil.strToDate(create_day));
            String str_cday = StringUtil.dispRepairLeft(Long.toBinaryString(cday) + "", "0", 12);
            String str_stores_id = StringUtil.dispRepairLeft(Long.toBinaryString(stores_id) + "", "0", 8);
            String str_no = StringUtil.dispRepairLeft(Long.toBinaryString(no) + "", "0", 12);
            String order_no = str_cday + str_stores_id + str_no;
            winxin_order_no = Long.valueOf(order_no, 2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return winxin_order_no;
    }

    //分解微信唯一标识
    public static WeixinCode resolve(long winxin_order_no) {
        WeixinCode code = null;
        try {
            String dd = StringUtil.dispRepairLeft(Long.toBinaryString(winxin_order_no) + "", "0", 32);
            int cday = Integer.valueOf(dd.substring(0, 12), 2);
            int stores_id = Integer.valueOf(dd.substring(12, 20), 2);
            int no = Integer.valueOf(dd.substring(20), 2);

            String create_day = DateUtil.dateToStr(DateUtil.addDay(DateUtil.strToDate(day), cday));
            code = new WeixinCode(create_day, stores_id, no);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static void main(String[] args){


        long winxin_order_no = create_code("2017-06-27",16,17);
        System.out.println(winxin_order_no);
        WeixinCode map =  resolve(winxin_order_no);
        System.out.println("map:"+map.getCreate_day()+"__"+map.getNo()+"__"+map.getStores_id());
    }
}
