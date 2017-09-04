package com.opensdk.weixin.api;

import com.alibaba.fastjson.JSONObject;
import com.opensdk.weixin.util.HttpUtil;
import com.opensdk.weixin.vo.SystemParamWeixin;

import java.util.HashMap;

/**
 * 微信接口调用
 * Created by Administrator on 2017/2/17.
 */
public class PoiAPI {

    public static String accessToken(SystemParamWeixin paramWeixin) {
        String urlNameString = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + paramWeixin.getAppId() + "&secret=" + paramWeixin.getAppSecret();
        String result = HttpUtil.getRequest(urlNameString);
        JSONObject jo = JSONObject.parseObject(result);
        return jo.getString("access_token");
    }

    public static String createcode(String access_token, String order_id) {
        long order_idL = Long.parseLong(order_id);
        String urlNameString = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + access_token;
        String postJson = "{\"expire_seconds\": 2592000, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + order_idL + "}}}";
        String result = HttpUtil.postRequest(access_token, postJson);
        if (!result.equals("")) {
            JSONObject jo = JSONObject.parseObject(result);
            return jo.getString("url");
        }
        return "";
    }

    //商家已接单通知 -okcXeqTbqGTSW0qZ9rn4L-8EhWQPbnmYZXgQGFLpLI
    //zLuXs0C3rnao1rJ2js0S-TdV00WQDHFHbQ7KhRc_0dE 至上一家
    public static void templateMessage1(String access_token, HashMap fromInfo) {
        String tid_cds = "-okcXeqTbqGTSW0qZ9rn4L-8EhWQPbnmYZXgQGFLpLI";
        // String tid_zs = "zLuXs0C3rnao1rJ2js0S-TdV00WQDHFHbQ7KhRc_0dE";
        System.out.println("tid_cds===" + tid_cds);
        String postJson = "  {\n" +
                "           \"touser\":\"" + fromInfo.get("openid") + "\",\n" +
                "           \"template_id\":\"" + tid_cds + "\",\n" +
                "           \"url\":\"http://weixin.caidashi.pro/orderInfo.action?order_id=" + fromInfo.get("order_id") + "\",            \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"震惊，菜大傻已经火速接单，背后竟是.....\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"" + fromInfo.get("goods") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\"" + fromInfo.get("create_date") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"如有一切订单疑问，请直接后台撩菜菜，么么哒~\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = HttpUtil.postRequest1(access_token, postJson);
        System.out.println("result===" + result);

    }

    //订单配送提醒 BYzPor4SBWerv0TO77-mFeEYUSYfxh8CdDZR32v5wHg
    public static String templateMessage2(String access_token, HashMap fromInfo) {
        String tid_cds = "BYzPor4SBWerv0TO77-mFeEYUSYfxh8CdDZR32v5wHg";
        String tid_zs = "fDi96mttnWXdFT1x9gjui7b7kHFeFf68kvrK44vSvHM";

        String postJson = "  {\n" +
                "           \"touser\":\"" + fromInfo.get("openid") + "\",\n" +
                "           \"template_id\":\"" + tid_cds + "\",\n" +
                "           \"url\":\"http://weixin.caidashi.pro/orderInfo.action?order_id=" + fromInfo.get("order_id") + "\",          \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"您的美味已经出发，请做好就餐准备~(*^__^*)\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"" + fromInfo.get("order_id") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\"菜大师外卖\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\"" + fromInfo.get("task_name") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword4\": {\n" +
                "                       \"value\":\"" + fromInfo.get("task_phone") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"猛戳详情，查看我的外卖在哪儿~\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = HttpUtil.postRequest1(access_token, postJson);
        JSONObject json = JSONObject.parseObject(result);
        result = json.getString("errcode");
        if (result == null) {
            result = "";
        }
        return result;
    }

    //订单完成通知 H_UWV9peoOBJi5T8jLy5gcvjMLyc7W_TDIUeZWovaIE
    public static String templateMessage3(String access_token, HashMap fromInfo) {
        String tid_cds = "H_UWV9peoOBJi5T8jLy5gcvjMLyc7W_TDIUeZWovaIE";
        String tid_zs = "-QtuOQa4EtvusaPbykmwgKVWjoTICf1lBJ-HDBKB9gQ";
        System.out.println("订单完成");
        String postJson = "  {\n" +
                "           \"touser\":\"" + fromInfo.get("openid") + "\",\n" +
                "           \"template_id\":\"" + tid_cds + "\",\n" +
                "           \"url\":\"http://weixin.caidashi.pro/orderInfo.action?order_id=" + fromInfo.get("order_id") + "\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"菜大师虽好吃，可还是要多吃，菜菜祝您用餐愉快~\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"" + fromInfo.get("finish_date") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\"" + fromInfo.get("order_id") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\"" + fromInfo.get("goods") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword4\": {\n" +
                "                       \"value\":\"已完成\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword5\": {\n" +
                "                       \"value\":\"订单完成\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"您的美味已经在小手上了吗？，如有任何疑问，请与菜菜联系。您的真实反馈，才是对菜大师最好的帮助~么么哒！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = HttpUtil.postRequest1(access_token, postJson);
        JSONObject json = JSONObject.parseObject(result);
        result = json.getString("errcode");
        if (result == null) {
            result = "";
        }
        return result;

    }

    //订单取消通知 Y6aLnKOtoXFtp7TZX)(fdJTESeOcM3KumowSSXAMM8VQ
    public static void templateMessage4(String access_token, HashMap fromInfo) {
        String tid_cds = "Y6aLnKOBbXFtp7TZXXrdJTE5e0cM3Humow55XAMM8VQ";
        String tid_zs = "qFKGs1v3Q6Xkk0XztZ0wIYpjlMxjP34VNHMCBbtz8RY";
        System.out.println("订单取消====");
        String postJson = "  {\n" +
                "           \"touser\":\"" + fromInfo.get("openid") + "\",\n" +
                "           \"template_id\":\"" + tid_cds + "\",\n" +
                "           \"url\":\"http://weixin.caidashi.pro/orderInfo.action?order_id=" + fromInfo.get("order_id") + "\",            \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"告诉菜菜，疑问解决了没？\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"" + fromInfo.get("create_date") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\"" + fromInfo.get("price") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\"" + fromInfo.get("goods") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword4\": {\n" +
                "                       \"value\":\"已取消\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword5\": {\n" +
                "                       \"value\":\"菜品已卖光\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"您的真实反馈，才是对菜大师最好的帮助~么么哒！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = HttpUtil.postRequest1(access_token, postJson);
        System.out.println("订单取消====" + result);
    }

    //订单出餐通知 _2MjPe-4Py439XvUg9yCHNGEFCRInxXCQtRjbHjOXY8
    public static void templateMessage5(String access_token, HashMap fromInfo) {
        String content = "";
        if (fromInfo.get("type").toString().equals("5")) {
            content = "寂寞的餐品，等待可爱骑手来驾驭...请稍等~";
        } else if (fromInfo.get("type").toString().equals("6")) {
            content = "自取的客官可以去拿餐啦！！";
        }
        String tid_cds = "_2MjPe-4Py439XvUg9yCHNGEFCRInxXCQtRjbHjOXY8";
        String tid_zs = "jrtUQNcRRWLrJ22czewPNqI-xoOGekHXswEw29iIiow";
        System.out.println("订单出餐====");
        String postJson = "  {\n" +
                "           \"touser\":\"" + fromInfo.get("openid") + "\",\n" +
                "           \"template_id\":\"" + tid_cds + "\",\n" +
                "           \"url\":\"http://weixin.caidashi.pro/orderInfo.action?order_id=" + fromInfo.get("order_id") + "\",            \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"震惊，菜大傻已经火速出餐，背后竟是.....\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"" + fromInfo.get("order_id") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\"" + fromInfo.get("goods") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\"" + fromInfo.get("price") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword4\": {\n" +
                "                       \"value\":\"" + fromInfo.get("finish_date") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"" + content + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = HttpUtil.postRequest1(access_token, postJson);
        System.out.println("订单出餐====" + result);
    }

    //订单赠品出餐通知 _2MjPe-4Py439XvUg9yCHNGEFCRInxXCQtRjbHjOXY8
    //触发条件：用户下单后，如有赠品标记，直接微信推送。
    public static void templateMessage7(String access_token, HashMap fromInfo) {
        String content = "菜小妹提醒：菜品到达后，请检查您的赠品是否遗漏，如有遗漏请尽快与菜小妹联系~！";
        String tid_cds = "_2MjPe-4Py439XvUg9yCHNGEFCRInxXCQtRjbHjOXY8";
        String tid_zs = "jrtUQNcRRWLrJ22czewPNqI-xoOGekHXswEw29iIiow";
        System.out.println("赠品订单出餐====");
        String postJson = "  {\n" +
                "           \"touser\":\"" + fromInfo.get("openid") + "\",\n" +
                "           \"template_id\":\"" + tid_cds + "\",\n" +
                "           \"url\":\"http://weixin.caidashi.pro/orderInfo.action?order_id=" + fromInfo.get("order_id") + "\",            \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"菜小妹承诺的赠品，已经成功激活~！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"" + fromInfo.get("order_id") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\"" + fromInfo.get("goods") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\"" + fromInfo.get("price") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword4\": {\n" +
                "                       \"value\":\"" + fromInfo.get("finish_date") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"" + content + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = HttpUtil.postRequest1(access_token, postJson);
        System.out.println("赠品订单出餐====" + result);
    }

    //礼品登记成功通知 tK1IZoezkcKQfFXPTekveil8VPOPzRZ1i19vdiRMRro
    //触发条件：客服后台进行用户赠品标记。
    public static void templateMessage8(String access_token, HashMap fromInfo) {
        String content = "嘿，下次点单时，赠品会自动附加在您的订单上哟~";
        String tid_cds = "tK1IZoezkcKQfFXPTekveil8VPOPzRZ1i19vdiRMRro";
        String tid_zs = "WivhxLSrg1ECxnZnijPQi6mMqZQ53uFmDpGzsdidbjE";
        System.out.println("礼品登记成功通知====");
        String postJson = "  {\n" +
                "           \"touser\":\"" + fromInfo.get("openid") + "\",\n" +
                "           \"template_id\":\"" + tid_cds + "\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"赠品：很幸运遇见了你，请多多关照~\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"" + fromInfo.get("gift_name") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\"" + fromInfo.get("gift_count") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\"" + fromInfo.get("gift_date") + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"" + content + "\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = HttpUtil.postRequest1(access_token, postJson);
        System.out.println("礼品登记成功通知====" + result);
    }
}
