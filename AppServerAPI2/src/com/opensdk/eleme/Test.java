package com.opensdk.eleme;

import com.alibaba.fastjson.JSONObject;
import com.framework.system.SystemConfig;
import com.framework.util.DateUtil;
import com.framework.util.WebUtil;
import com.opensdk.eleme.factory.APIFactoryEleme;
import com.opensdk.eleme.factory.URLFactoryEleme;
import com.opensdk.eleme.util.ConvertUtil;
import com.opensdk.eleme.util.SignGenerator;
import com.opensdk.eleme.vo.MessageParam;
import com.opensdk.eleme.vo.OrderParam;
import com.opensdk.eleme.vo.SystemParamEleme;
import com.opensdk.eleme.constants.PoiQualificationEnum;
import com.opensdk.eleme.exception.ApiOpException;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.vo.PoiParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenbin on 17/02/05.
 */
public class Test {

    private final static SystemParamEleme sysPram = new SystemParamEleme("0845014911", "d54f311f145a99c7a619e65b93ea77892ae5ac92");
//    private final static String appPoiCode = "ceshi_POI_II";
    private final static String appPoiCode = "ceshi_02";

    public static void main(String[] args)  throws Exception{

        List<String> orders =  APIFactoryEleme.getOrderAPI().orderBatchGet(SystemConfig.GetSystemParamEleme(), DateUtil.getDate(),948112);


        //OrderParam orderParam = APIFactoryEleme.getOrderAPI().orderGetOrderDetail(WebUtil.sysPramEleme,"1202520343242307803");

       /* MessageParam param = new MessageParam();

        param.setRefund_status(0);
        param.setStatus_code(0);
        param.setNew_status("9");
        param.setPush_action(2);
        param.setConsumer_key("0845014911");
        param.setSig("0b85471294bad10af56d48a4eb5669bef194c8b9");
        param.setTp_order_id("");
        param.setEleme_order_id("1201492532148617244");
        param.setTimestamp(1486701497);
        boolean sig = SignGenerator.getSigPush(param, WebUtil.sysPramEleme.getConsumer_secret());*/




        //poiGetIds();
        String order_id = "1201409102081792028";

        //String order_id = "1201401715023315996";
        //try {
           //OrderParam orderDetailParam = APIFactoryEleme.getOrderAPI().orderGetOrderDetail(sysPram,order_id);


           //List<String> orders = APIFactoryEleme.getOrderAPI().orderBatchGet(sysPram,"2017-02-08",948110,"-1");

            //JSONObject result = APIFactoryEleme.getOrderAPI().orderStatus(sysPram,order_id);
           // String result = APIFactoryEleme.getOrderAPI().orderStatusUpdate(sysPram,order_id,1);

            //System.out.println(123);
       /* } catch (ApiOpException e) {

            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }*/


        /*String baseUrl = "http://ppe-openapi.ele.me/v2/restaurants/?consumer_key=7284397484&timestamp=13749080544d31ba58fd73c71db697ab5e4946d52d";

        String sign = SignGenerator.genSig(baseUrl);
        System.out.println(sign);*/
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
            String result = APIFactoryEleme.getPoiAPI().poiSave(sysPram, PoiPram);
            System.out.println(result);
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiGetIds() {
        try {
            String result = APIFactoryEleme.getPoiAPI().poiGetIds(sysPram);
            System.out.println(result);
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiMget() {
        try {
            List<PoiParam> Poslist = APIFactoryEleme.getPoiAPI().poiMget(sysPram, "ceshi_poi1,ceshi_100");
            System.out.println(Poslist);
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiTagList() {
        try {
            System.out.println(APIFactoryEleme.getPoiAPI().poiTagList(sysPram));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiOpen() {
        try {
            System.out.println(APIFactoryEleme.getPoiAPI().poiOpen(sysPram, "ceshi_poi1"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiClose() {
        try {
            System.out.println(APIFactoryEleme.getPoiAPI().poiClose(sysPram, "ceshi_poi1"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiOffline() {
        try {
            System.out.println(APIFactoryEleme.getPoiAPI().poiOffline(sysPram, "ceshi_poi1", "缺货"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiOnline() {
        try {
            System.out.println(APIFactoryEleme.getPoiAPI().poiOnline(sysPram, "ceshi_poi1"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiQualifySave() {
        try {
            System.out.println(APIFactoryEleme.getPoiAPI().poiQualifySave(sysPram, "ceshi_poi1",
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
            System.out.println(APIFactoryEleme.getPoiAPI().poiSendTimeSave(sysPram, "ceshi_poi1,ceshi_100", 50));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiAdditionalSave() {
        try {
            System.out.println(APIFactoryEleme.getPoiAPI().poiAdditionalSave(sysPram, "ceshi_poi1","cyinchao@sina.com", "zhajisong", "8888"));
        } catch (ApiOpException e) {
            e.printStackTrace();
        } catch (ApiSysException e) {
            e.printStackTrace();
        }
    }

    public static void poiUpdatePromotionInfo() {
        try {
            System.out.println(APIFactoryEleme.getPoiAPI().poiUpdatePromotionInfo(sysPram, "ceshi_poi1", "这是一条用于测试的门店公告信息"));
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
