package com.framework.util;

import com.framework.mapping.system.CdsMessageCode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;


/**
 * Created by c on 2017-01-26.
 */
public class MessageUtil {

    public static void sendMessage(String phone, String sms_content) {
        sendMessage(phone, sms_content, "9", 0);
    }

    public static void sendMessage(String phone, String sms_content, String mc_type) {
        sendMessage(phone, sms_content, mc_type, 0);
    }

    //昊博短信（现用）
    public static void sendMessage(String phone, String sms_content, String mc_type, int member_id) {
        try {
            sms_content+="退订回T";
            String param = "un=10690177&pw=mayi2016&da=" + phone + "&sm=" + StringUtil.bin2hex(sms_content) + "&dc=15&rd=1";
            String url = "http://101.227.68.49:7891/mt?" + param;

            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            if (WebUtil.getRequest() != null && WebUtil.getSqlDao() != null) {
                CdsMessageCode message = new CdsMessageCode();
                message.setMember_id(member_id);
                message.setMc_mobile(phone);
                message.setMc_date(new Date());
                message.setMc_addip(ServerUtil.getClientIP(WebUtil.getRequest()));
                message.setMc_type(mc_type);
                message.setMc_content(sms_content);

                WebUtil.getSqlDao().insertRecord("cds_message_code.saveEntity", message);
            }

        } catch (Exception e) {

        }
    }

    //短信网
    public static void sendMessage1(String phone, String sms_content, String mc_type, int member_id) {
        try {

            // 用户名
            String name = "dxwcaidashi";
            // 密码
            String pwd = "0220F37D3F885726469A5A2EC898";
            // 电话号码字符串，中间用英文逗号间隔
            StringBuffer mobileString = new StringBuffer(phone);
            // 内容字符串
            //StringBuffer contextString=new StringBuffer("【菜大师】：报告老板！您的餐品已送达，满意请打赏菜菜五星好评。若有不满意，请拨打客服专线4001148878，菜小妹帮您解决问题哟！");
            //StringBuffer contextString=new StringBuffer("【菜大师】：您正在“菜大师”进行注册，验证码是：485952，请不要把验证码泄露给其他人。");
            //StringBuffer contextString=new StringBuffer("【菜大师】：美味已经出发！骑手：李向阳，电话：13986118301，关注微信公众号“菜大师外卖”，查看骑手地图~！");
            StringBuffer contextString = new StringBuffer(sms_content);
            // 追加发送时间，可为空，为空为及时发送
            String stime = "";
            // 扩展码，必须为数字 可为空
            StringBuffer extno = new StringBuffer();
            doPost(name, pwd, mobileString, contextString, stime, extno);

            if (WebUtil.getRequest() != null && WebUtil.getSqlDao() != null) {
                CdsMessageCode message = new CdsMessageCode();
                message.setMember_id(member_id);
                message.setMc_mobile(phone);
                message.setMc_date(new Date());
                message.setMc_addip(ServerUtil.getClientIP(WebUtil.getRequest()));
                message.setMc_type(mc_type);
                message.setMc_content(sms_content);

                WebUtil.getSqlDao().insertRecord("cds_message_code.saveEntity", message);
            }

        } catch (Exception e) {

        }
    }


    /**
     * 发送短信(短信网)
     *
     * @param name          用户名
     * @param pwd           密码
     * @param mobileString  电话号码字符串，中间用英文逗号间隔
     * @param contextString 内容字符串
     * @param stime         追加发送时间，可为空，为空为及时发送
     * @param extno         扩展码，必须为数字 可为空
     * @return
     * @throws Exception
     */
    public static String doPost(String name, String pwd,
                                StringBuffer mobileString, StringBuffer contextString,
                                String stime, StringBuffer extno) throws Exception {
        StringBuffer param = new StringBuffer();
        param.append("name=" + name);
        param.append("&pwd=" + pwd);
        param.append("&mobile=").append(mobileString);
        //param.append("&sign=").append(URLEncoder.encode(sign,"UTF-8"));
        param.append("&content=").append(URLEncoder.encode(contextString.toString(), "UTF-8"));
        // param.append("&stime="+stime);
        param.append("&type=pt");
        param.append("&extno=").append(extno);

        URL localURL = new URL("http://web.duanxinwang.cc/asmx/smsservice.aspx?");
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(param.length()));

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        String resultBuffer = "";

        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(param.toString());
            outputStreamWriter.flush();

            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            inputStream = httpURLConnection.getInputStream();
            resultBuffer = convertStreamToString(inputStream);

        } finally {

            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }

            if (outputStream != null) {
                outputStream.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

        return resultBuffer;
    }


    /**
     * 转换返回值类型为UTF-8格式.
     *
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        StringBuilder sb1 = new StringBuilder();
        byte[] bytes = new byte[4096];
        int size = 0;

        try {
            while ((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb1.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb1.toString();
    }

}
