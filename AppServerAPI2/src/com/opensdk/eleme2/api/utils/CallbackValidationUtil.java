package com.opensdk.eleme2.api.utils;




import com.opensdk.eleme2.api.entity.other.OMessage;
import com.opensdk.eleme2.utils.SignatureUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CallbackValidationUtil {

    public static boolean isValidMessage(OMessage message, String secret) {
        if (message == null) {
            return false;
        }
        if (message.getSignature() == null) {
            return false;
        }
        Map<String, Object> map = new HashMap();
        map.put("requestId", message.getRequestId());
        map.put("message", message.getMessage());
        map.put("type", message.getType());
        map.put("shopId", message.getShopId());
        map.put("timestamp", message.getTimestamp());
        map.put("userId", message.getUserId());
        map.put("appId", message.getAppId());

        String signature = null;
        try {
            signature = getSignature(map, secret);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return signature.toUpperCase().equals(message.getSignature().toUpperCase());
    }

    public static String getSignature(Map<String, Object> params, String secret) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, Object> sortedParams = new TreeMap<String, Object>(params);
        Set<Map.Entry<String, Object>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, Object> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        basestring.append(secret);

        return SignatureUtil.md5(basestring.toString());
    }
}
