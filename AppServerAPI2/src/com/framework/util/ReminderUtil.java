package com.framework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.controller.ReminderController;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * 饿了么爬取催单信息(手动登陆)
 */
public class ReminderUtil {

    private static CloseableHttpClient httpclient;
    private static HttpPost httppost;// 用于提交登陆数据
    private static HttpGet httpget;// 用于获得登录后的页面
    private static String login_success;// 用于构造上面的HttpGet

    public static void LoginMis() {
        httpclient = HttpClients.createDefault();
        // mis登陆界面网址
        //httpget = new HttpGet("https://melody.shop.ele.me/login");
        httpget = new HttpGet("https://melody.shop.ele.me/app/chain-shop/order/processing?path=remind");
        //httpget = new HttpGet("http://shop.caidashi.pro");
        httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0");
        httpget.addHeader("Host", "melody.shop.ele.me");
        //httpget.addHeader("");
    }

    public static JSONObject logIn2(String name, String password,String captchaCode) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String id = java.util.UUID.randomUUID().toString();
        String url = "https://app-api.shop.ele.me/arena/invoke/?method=LoginService.loginByUsername";
        String json = "{\"id\":\"" + id + "\",\"method\":\"loginByUsername\",\"service\":\"LoginService\",\"params\"" +
                ":{\"username\":\"" + name + "\",\"password\":\"" + password + "\",\"captchaCode\":\"" + captchaCode + "\",\"loginedSessionIds\":[]},\"metas\":{\"appName\"" +
                ":\"melody\",\"appVersion\":\"4.4.0\"},\"ncp\":\"2.0.0\"}";


        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0");
        StringEntity se = new StringEntity(json, "UTF-8");
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");

        httpPost.setEntity(se);
        String respContent = null;


        HttpResponse resp = httpClient.execute(httpPost);
        if (resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he, "utf-8");
        }
        System.out.println(respContent);
        JSONObject jo = JSONObject.parseObject(respContent);
        return jo;
    }

    public static JSONObject getchida(String id, String ksid) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        String url = "https://app-api.shop.ele.me/nevermore/invoke/?method=OrderService.queryOrder";
        String json = "{\"id\":\""+id+"\",\"metas\":{\"appName\":\"melody\",\"appVersion\":\"4.4.0\",\"ksid\":\""+ksid+"\",\"key\":\"1.0.0\"},\"ncp\":\"2.0.0\",\"service\":\"OrderService\",\"method\":\"queryOrder\",\"params\":{\"shopId\":null,\"orderFilter\":\"QUERY_ALL_REMIND_ORDERS\",\"condition\":{\"page\":1,\"remindOrderTypes\":[\"REMIND_ORDER\"],\"offset\":0,\"limit\":20}}}";


        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0");
        StringEntity se = new StringEntity(json, "UTF-8");
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");

        httpPost.setEntity(se);
        String respContent = null;


        HttpResponse resp = httpClient.execute(httpPost);
        if (resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he, "utf-8");
        }
        System.out.println(respContent);
        JSONObject jo = JSONObject.parseObject(respContent);
        return  jo;
    }


    /**
     *催单回复
     * @param reminderId
     * @param ksid
     * @return
     * @throws Exception
     */
    public static String reminderHf(String reminderId, String ksid,String text) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String id  = java.util.UUID.randomUUID().toString();
        String url = "https://app-api.shop.ele.me/nevermore/invoke/?method=OrderService.replyReminderOrder";
        String json = "{\"id\":\"" + id + "\",\"metas\":{\"appName\":\"melody\",\"appVersion\":\"4.4.0\",\"ksid\":\"" + ksid + "\",\"key\":\"1.0.0\"},\"ncp\":\"2.0.0\",\"service\":\"OrderService\",\"method\":\"replyReminderOrder\",\"params\":{\"reminderId\":\"" + reminderId + "\",\"content\":\"" + text + "\"}}";


        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0");
        StringEntity se = new StringEntity(json, "UTF-8");
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");
        httpPost.setEntity(se);
        HttpResponse resp = httpClient.execute(httpPost);
        if (resp.getStatusLine().getStatusCode() == 200) {
            return "1";
        }else{
            return "0";
        }

    }

    public static void PrintText(String name) throws IOException {
        httpget = new HttpGet(login_success);
        HttpResponse re2 = null;

        try {
            re2 = httpclient.execute(httpget);
            // 输出登录成功后的页面
            String str = EntityUtils.toString(re2.getEntity());

            System.out.println("\n" + name + "首页信息如下:");
            System.out.println(str.substring(8250, 8400));
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            httppost.abort();
            httpget.abort();
            httpclient.close();
        }
    }


    /**
     * 美团
     * @param url
     * @return
     */
    public static JSONObject getMtJson(String url){
//        String url = "http://e.waimai.meituan.com/v2/order/customer/reminder/unprocessed/list?pageNum=1";
//        String url2 = "http://e.waimai.meituan.com/v2/order/customer/reminder/processed/list?wmPoiId=-1&startDate=2017-07-29&endDate=2017-08-03&pageNum=1";
        Connection conn = Jsoup.connect(url);
        conn.header("(Request-Line)", "POST /cgi-bin/login?lang=zh_CN HTTP/1.1");
        conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        conn.header("Accept-Encoding", "gzip, deflate");
        conn.header("Accept-Language", "zh-CN,zh;q=0.8");
        conn.header("Cache-Control", "no-cache");
        conn.header("Connection", "keep-alive");
        conn.header("Content-Type", "text/html;charset=UTF-8");
        conn.header("Cookie","_lxsdk=15d981cce18c8-0668fdf59284478-6c247613-1fa400-15d981cce18c8; _lxsdk_s=db16278d8798e8d753b9ba3417c1%7C%7C19; uuid=49bf73706791154dfad1.1501668876.0.0.0; em=bnVsbA; om=bnVsbA; device_uuid=!709cd1aa-084d-49c7-8490-4095804401f8; uuid_update=true; shopCategory=food; __mta=247539515.1501495611006.1501669387753.1501669388130.17; acctId=24196085; brandId=-1; wmPoiId=-1; isOfflineSelfOpen=0; city_id=420100; isChain=1; existBrandPoi=true; cityId=420100; provinceId=420000; city_location_id=420100; location_id=0; wpush_server_url=wss://wpush.meituan.com; JSESSIONID=epcgeczd0gjq827wo2prpw9o; token=0z37ilLvUvendR0aue5QKXUZ7dmpmHA6v9XAoQ6AQ7gs*; pushToken=0z37ilLvUvendR0aue5QKXUZ7dmpmHA6v9XAoQ6AQ7gs*");
//        conn.header("Cookie", "_lxsdk=15d9642a5e3c8-0835eb8e81a8e1-4383666-1fa400-15d9642a5e35d; device_uuid=!436e2105-551a-4f9c-9ede-e574fff0e684; uuid_update=true; pushToken=0Eni_kCBdsGJ6SXtH2hWUuIOL3WzXBmjNBAwwExz0nZQ*; epassportlr=aHR0cDovL2VwYXNzcG9ydC5tZWl0dWFuLmNvbS9hY2NvdW50L2xvZ2ludjI; em=bnVsbA; __utma=1.21944258.1501475346.1501475346.1501479810.2; __utmc=1; __utmz=1.1501475346.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __mta=255777335.1501464537063.1501475674724.1501479809985.17; uuid=f49ce3222e86887d3e36.1501464546.0.0.0; acctId=27774758; token=0074ETQqH7i3CHl4NBr3zt7P3g-KlzKChhMVvtHox5k8*; brandId=-1; wmPoiId=2922156; isOfflineSelfOpen=0; city_id=420100; shopCategory=food; isChain=0; existBrandPoi=true; cityId=420100; provinceId=420000; city_location_id=420100; location_id=420102; JSESSIONID=g9vb73japn4v1ga09umozl8qe; __mta=255777335.1501464537063.1501479809985.1501485464658.18; wpush_server_url=wss://wpush.meituan.com; _lxsdk_s=0856ca798a1bbdfc32b21fed5fbf%7C%7C10");
        conn.header("Host", "e.waimai.meituan.com");
        conn.header("Referer", url);
        conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Safari/537.36");
        Document doc = null;
        try {
            doc = conn.ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element body = doc.body();
        JSONObject json = JSONObject.parseObject(body.text());
//        ReminderController rc = new ReminderController();
//        rc.saveMtCd(json);
        return json;
    }


}
