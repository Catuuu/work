package com.cheea.action;

/**
 * Created by Administrator on 2017/7/17.
 */
public class Text {

    public static void main(String[] args) {
        String APPLICATION_JSON = "application/json";

        String CONTENT_TYPE_TEXT_JSON = "text/json";
        try {
            HttpClient client = new DefaultHttpClient();                //构建一个Client
            HttpPost post = new HttpPost("https://app-api.shop.ele.me/arena/invoke/");    //构建一个POST请求
            //构建表单参数
            JSONObject metas = new JSONObject();
            metas.put("appName","melody");
            metas.put("appVersion","4.4.0");
            JSONObject params = new JSONObject();
            params.put("captchaCode","");
            params.put("loginedSessionIds","");
            params.put("password","cdswhls1");
            params.put("username","cdswhls");
            JSONObject jo = new JSONObject();
            jo.put("id","01f489b1-cd3d-4975-9dab-f463ea3935a6");
            jo.put("method","loginByUsername");
            jo.put("ncp","2.0.0");
            jo.put("service","LoginService");
            jo.put("metas",metas);
            jo.put("params",params);
            String encoderJson = URLEncoder.encode(jo.toJSONString(), HTTP.UTF_8);

//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpPost httpPost = new HttpPost(url);
            post.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

            StringEntity se = new StringEntity(encoderJson);
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            post.setEntity(se);
            client.execute(post);

//            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");//将表单参数转化为“实体”
//            post.setEntity(entity);        //将“实体“设置到POST请求里
            HttpResponse response = client.execute(post);//提交POST请求
            HttpEntity result = response.getEntity();//拿到返回的HttpResponse的"实体"
            String content = EntityUtils.toString(result);;            //用httpcore.jar提供的工具类将"实体"转化为字符串打印到控制台
            System.out.println(content);
            if(content.contains("登陆成功")){
                System.out.println("登陆成功！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
