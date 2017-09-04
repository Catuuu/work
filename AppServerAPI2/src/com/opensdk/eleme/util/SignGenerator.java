package com.opensdk.eleme.util;

import com.opensdk.eleme.constants.ErrorEnum;
import com.opensdk.eleme.exception.ApiSysException;
import com.opensdk.eleme.vo.MessageParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenbin on 17/02/05.
 * 签名生成器
 */
public class SignGenerator {


    public static String genSig(String baseUrl) throws ApiSysException{
        String str = null;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA1");
            str =  byte2hex(md.digest(byte2hex(baseUrl.getBytes("UTF-8")).getBytes()));
        }catch (NoSuchAlgorithmException e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }catch (UnsupportedEncodingException e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return str;
    }

    private static String byte2hex(byte[] b) {
        StringBuffer buf = new StringBuffer();
        int i;

        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }

        return buf.toString();
    }

    //推送消息验证
    private static String concatParams(Map<String, String> params2) throws UnsupportedEncodingException {
        Object[] key_arr = params2.keySet().toArray();
        Arrays.sort(key_arr);
        String str = "";

        for (Object key : key_arr) {
            String val = String.valueOf(params2.get(key));
            key = URLEncoder.encode(key.toString(), "UTF-8");
            val = URLEncoder.encode(val, "UTF-8");
            str += "&" + key + "=" + val;
        }

        return str.replaceFirst("&", "");
    }

    /**
     * 推送消息sig验证
     * @param pathUrl
     * @param params
     * @param consumerSecret
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String genSig(String pathUrl, Map<String, String> params,
                                String consumerSecret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String str = concatParams(params);
        str = pathUrl + "?" + str + consumerSecret;

        MessageDigest md = MessageDigest.getInstance("SHA1");
        return byte2hex(md.digest(byte2hex(str.getBytes("UTF-8")).getBytes()));
    }


    public static boolean getSigPush(MessageParam param,String consumerSecret){
        String path = "http://shop.caidashi.pro/ApiServer/ReceiveElemeOrder/";
        Map params = new HashMap();
        params.put("consumer_key",param.getConsumer_key());
        params.put("timestamp",param.getTimestamp());

        if(param.getPush_action()==4){//订单配送状态推送
            params.put("push_action",param.getPush_action());
            params.put("eleme_order_id",param.getEleme_order_id());
            params.put("status_code",param.getStatus_code());
            params.put("sub_status_code",param.getSub_status_code());
            params.put("name",param.getName());
            params.put("phone",param.getPhone());
            params.put("update_at",param.getUpdate_at());

        }else if (param.getPush_action()==3){//退单状态推送
            params.put("push_action",param.getPush_action());
            params.put("eleme_order_id",param.getEleme_order_id());
            params.put("refund_status",param.getRefund_status());
        }else if (param.getPush_action()==2){//退单状态推送
            params.put("push_action",param.getPush_action());
            params.put("eleme_order_id",param.getEleme_order_id());
            params.put("tp_order_id",param.getTp_order_id());
            params.put("new_status",param.getNew_status());
           // params.put("extra", param.getExtra());
            if(param.getExtra()!=null){
                params.put("extra", URLDecoder.decode(param.getExtra()));
            }
        }else if (param.getPush_action()==1){//退单状态推送
            params.put("push_action",param.getPush_action());
            params.put("eleme_order_ids",param.getEleme_order_ids());
        }else {
            return false;
        }

        try {
            String sig = SignGenerator.genSig(path,params,consumerSecret);
            if(param.getSig().equals(sig)){
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;

    }
}
