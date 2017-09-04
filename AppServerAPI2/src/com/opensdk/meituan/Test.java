package com.opensdk.meituan;

import com.alibaba.fastjson.JSONObject;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.system.SystemConfig;
import com.framework.util.DateUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import com.opensdk.meituan.factory.APIFactoryMeituan;
import com.opensdk.meituan.vo.OrderDetailParam;
import com.opensdk.meituan.vo.SystemParamMeituan;
import com.opensdk.meituan.constants.PoiQualificationEnum;
import com.opensdk.meituan.exception.ApiOpException;
import com.opensdk.meituan.exception.ApiSysException;
import com.opensdk.meituan.vo.PoiParam;

import java.util.Date;
import java.util.List;

/**
 * Created by chenbin on 17/02/05.
 */
public class Test {

    private final static SystemParamMeituan sysPram = new SystemParamMeituan("483", "a4afb2e41caad32336832a694509152b");
//    private final static String appPoiCode = "ceshi_POI_II";
    private final static String appPoiCode = "ceshi_02";

    public static void main(String[] args) throws Exception {
        //poiGetIds();
        long order_id = 10274163771016815L;
       OrderDetailParam orderDetailParam = APIFactoryMeituan.getOrderAPI().orderGetOrderDetail(sysPram,order_id,0);

       /* String result = APIFactoryMeituan.getOrderAPI().GetOrderDaySeq(sysPram, String.valueOf(9));
        JSONObject jo = JSONObject.parseObject(result);
        String day_seq = jo.getString("day_seq");
        System.out.println("aaa");*/
        /*String content = APIFactoryMeituan.getOrderAPI().orderConfirm(SystemConfig.GetSystemParamMeituan(),18854363975972170L);
        JSONObject jo = JSONObject.parseObject(content);*/
        System.out.println("123");
    }

    public static void poiSave() {
        PoiParam PoiPram = new PoiParam();
        PoiPram.setApp_poi_code("ceshi_poi1");
        PoiPram.setName("我的门店");
        PoiPram.setAddress("我的门店的地址");
        PoiPram.setLatitude(40.810249f);
        PoiPram.setLongitude(117.502289f);
        PoiPram.setPhone("13425355733");
        PoiPram.setShipping_fee(2f);
        PoiPram.setShipping_time("09:00-13:30,19:00-21:40");
        PoiPram.setPic_url("http://cdwuf.img46.wal8.com/img46/525101_20150811114016/144299728957.jpg");
        PoiPram.setOpen_level(1);
        PoiPram.setIs_online(1);
        PoiPram.setPre_book_min_days(1);
        PoiPram.setPre_book_max_days(2);
        PoiPram.setApp_brand_code("zhajisong");
        PoiPram.setThird_tag_name("麻辣烫");

        try {
            String result = APIFactoryMeituan.getPoiAPI().poiSave(sysPram, PoiPram);
            System.out.println(result);
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiGetIds() {
        try {
            String result = APIFactoryMeituan.getPoiAPI().poiGetIds(sysPram);
            System.out.println(result);
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiMget() {
        try {
            List<PoiParam> Poslist = APIFactoryMeituan.getPoiAPI().poiMget(sysPram, "ceshi_poi1,ceshi_100");
            System.out.println(Poslist);
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiTagList() {
        try {
            System.out.println(APIFactoryMeituan.getPoiAPI().poiTagList(sysPram));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiOpen() {
        try {
            System.out.println(APIFactoryMeituan.getPoiAPI().poiOpen(sysPram, "ceshi_poi1"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiClose() {
        try {
            System.out.println(APIFactoryMeituan.getPoiAPI().poiClose(sysPram, "ceshi_poi1"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiOffline() {
        try {
            System.out.println(APIFactoryMeituan.getPoiAPI().poiOffline(sysPram, "ceshi_poi1", "缺货"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiOnline() {
        try {
            System.out.println(APIFactoryMeituan.getPoiAPI().poiOnline(sysPram, "ceshi_poi1"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiQualifySave() {
        try {
            System.out.println(APIFactoryMeituan.getPoiAPI().poiQualifySave(sysPram, "ceshi_poi1",
                                            PoiQualificationEnum.BUSINESS_LICENSE,
                                            "http://cdwuf.img46.wal8.com/img46/525101_20150811114016/14429972901.jpg",
                                            "2016-01-01", "天安门", "11019123"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

        public static void poiSendTimeSave() {
        try {
            System.out.println(APIFactoryMeituan.getPoiAPI().poiSendTimeSave(sysPram, "ceshi_poi1,ceshi_100", 50));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiAdditionalSave() {
        try {
            System.out.println(APIFactoryMeituan.getPoiAPI().poiAdditionalSave(sysPram, "ceshi_poi1","cyinchao@sina.com", "zhajisong", "8888"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiUpdatePromotionInfo() {
        try {
            System.out.println(APIFactoryMeituan.getPoiAPI().poiUpdatePromotionInfo(sysPram, "ceshi_poi1", "这是一条用于测试的门店公告信息"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }



//    public static void main(String[] args) {
//        byte[] imgData = {85,112,108,111,97,100,115,47,80,114,111,100,117,99,116,115,47,49,53,48,55,47,48,49,47,105,109,103,46,106,112,103};
//        try {
//            System.out.println(
//                APIFactoryEleme.getImageApi().imageUpload(systemParam, appPoiCode, imgData, "ceshi.jpg"));
//        } catch (ApiOpException e) {
//            e.printStackTrace();
//        } catch (ApiSysException e) {
//            e.printStackTrace();
//        }
//    }


}
