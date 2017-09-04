package com.opensdk.weixin;

import com.framework.util.WebUtil;
import com.opensdk.weixin.factory.APIFactoryWeixin;

/**
 * Created by Administrator on 2017/2/17.
 */
public class test {
    public static void main(String[] args) {
        //String accesstoken = APIFactoryWeixin.getPoiAPI().accessToken(WebUtil.sysPramWeixin);
        String accesstoken = "oYxHs-RiDhaE1ZylG161CpZyCaNPT4QEYgSzXq522xrsORdfCR7h5E9n1EHr3Z6auratN5xuqBTof6WA41D6Ua_Pzehyu43taT189nDO0UZ-ZvX407xyF3IUBatplmeATCSgADAPYO";
        String result = APIFactoryWeixin.getPoiAPI().createcode(accesstoken,"478958985985");
        System.out.println(result);
    }
}
