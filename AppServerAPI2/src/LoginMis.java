import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.Entity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/24.
 */
public class LoginMis {
    private CloseableHttpClient httpclient;
    private HttpPost httppost;// 用于提交登陆数据
    private HttpGet httpget;// 用于获得登录后的页面
    private String login_success;// 用于构造上面的HttpGet

    public LoginMis() {
        httpclient = HttpClients.createDefault();
        // mis登陆界面网址
        //httpget = new HttpGet("https://melody.shop.ele.me/login");
        httpget = new HttpGet("https://melody.shop.ele.me/app/chain-shop/order/processing?path=remind");
        //httpget = new HttpGet("http://shop.caidashi.pro");
        httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0");
        httpget.addHeader("Host", "melody.shop.ele.me");
        //httpget.addHeader("");
    }


    public void logIn2(String name, String password) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String id = java.util.UUID.randomUUID().toString();
        String url = "https://app-api.shop.ele.me/arena/invoke/?method=LoginService.loginByUsername";
        String json = "{\"id\":\"" + id + "\",\"method\":\"loginByUsername\",\"service\":\"LoginService\",\"params\"" +
                ":{\"username\":\"" + name + "\",\"password\":\"" + password + "\",\"captchaCode\":\"\",\"loginedSessionIds\":[]},\"metas\":{\"appName\"" +
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
    }



    public void getchida(String id, String ksid) throws Exception {
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
    }




    public void PrintText(String name) throws IOException {
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

    public static void main(String[] args) throws Exception {
        String name = "cdswhls", password = "cdswhls1";
        // 自己的账号，口令
        LoginMis lr = new LoginMis();
        //lr.logIn2(name, password);
        lr.getchida("56336964-45a5-4557-8d1a-e1c05064e272","YTY2MGY3YTAtMWJjMS00MzQ1LTgwMGMjcyM1");

    }
}
