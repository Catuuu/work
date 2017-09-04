package com.framework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 饿了么爬取评价信息
 */
public class CommentUtil {

    /**
     * 饿了么评论参数
     *
     * @param shopIds   店铺
     * @param queryType 查询类型 0.全部 1.未回复差评 2.未回复
     * @return
     * @throws Exception
     */
    public static String getELemeCommentParams(List shopIds, Integer queryType, Integer page) {
        HashMap commentParams = new HashMap();
        HashMap params = new HashMap();
        HashMap query = new HashMap();
        HashMap metas = new HashMap();

        String state = null;
        String replied = null;
        String method = null;
        if (queryType == 1) {
            state = "NegativeComments";//未回复差评
            replied = "false";
        } else if (queryType == 2) {
            state = "NewComments";//未回复
            replied = "false";
        }

        query.put("beginDate", "2017-08-06" + "T00:00:00");
        query.put("endDate", DateUtil.getDate() + "T23:59:59");
        query.put("deadline", null);//查询区间（7天,30天）
        query.put("hasContent", true);
        query.put("replied", replied);
        query.put("tag", null);
        query.put("limit", 20);
        query.put("offset", (page - 1) * 20);
        query.put("state", state);

        params.put("shopIds", shopIds);
        params.put("query", query);

        metas.put("appName", "melody");
        metas.put("appVersion", "4.4.0");
        metas.put("ksid", "ODJlZTVmNjEtNmQ3Zi00YWRmLWJjNDZGNlOT");

        commentParams.put("id", java.util.UUID.randomUUID().toString());
        commentParams.put("method", "queryChainShopRating");
        commentParams.put("service", "shopRating");
        commentParams.put("ncp", "2.0.0");
        commentParams.put("params", params);
        commentParams.put("metas", metas);

        String json = JSON.toJSONString(commentParams);
        return json;
    }


    /**
     * 饿了么评论数据
     *
     * @param shopIds   店铺
     * @param queryType 查询类型 0.全部 1.未回复差评 2.未回复
     * @return
     * @throws Exception
     */
    public static JSONArray getElemeCommentList(List shopIds, Integer queryType, Integer page) throws Exception {
        String json = getELemeCommentParams(shopIds, queryType, page);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String id = java.util.UUID.randomUUID().toString();
        String url = "https://app-api.shop.ele.me/ugc/invoke?method=shopRating.queryChainShopRating";
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0");
        StringEntity se = new StringEntity(json, "UTF-8");
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");
        httpPost.setEntity(se);
        HttpResponse resp = httpClient.execute(httpPost);
        String respContent = null;
        if (resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he, "utf-8");
        }
        JSONObject jo = JSONObject.parseObject(respContent);
        JSONObject jo1 = jo.getJSONObject("result");
        JSONArray joList = jo1.getJSONArray("orderRatingList");
        return joList;

    }


    /**
     * 饿了么接口参数（评论列表，订单回复,菜品回复）
     *
     * @return
     * @throws Exception
     */
    public static String getReplyCommentParams(HashMap elemeParams) {
        //公共参数
        HashMap commentParams = new HashMap();
        HashMap params = new HashMap();
        HashMap metas = new HashMap();
        metas.put("appName", "melody");
        metas.put("appVersion", "4.4.0");
        metas.put("ksid", "ODJlZTVmNjEtNmQ3Zi00YWRmLWJjNDZGNlOT");

        commentParams.put("id", java.util.UUID.randomUUID().toString());
        commentParams.put("service", "shopRating");
        commentParams.put("ncp", "2.0.0");
        commentParams.put("metas", metas);
        //方法参数
        String method = elemeParams.get("method").toString();
        String ratingId = elemeParams.get("ratingId").toString();
        String content = elemeParams.get("content").toString();
        String shopId = elemeParams.get("shopId").toString();
        String ratingType = elemeParams.get("ratingType").toString();

        HashMap reply = new HashMap();
        reply.put("ratingId", ratingId);
        reply.put("ratingType", ratingType);
        reply.put("content", content);

        params.put("shopId", shopId);
        params.put("reply", reply);

        commentParams.put("method", method);
        commentParams.put("params", params);


        String json = JSON.toJSONString(commentParams);
        return json;
    }


    /**
     * 饿了么回复（订单回复,菜品回复）
     *
     * @return
     * @throws Exception
     */
    public static JSONObject replyElemeComment(HashMap elemeParams) throws Exception {
        String json = getReplyCommentParams(elemeParams);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String id = java.util.UUID.randomUUID().toString();
        String url = "https://app-api.shop.ele.me/ugc/invoke?method=shopRating.replyRating";
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0");
        StringEntity se = new StringEntity(json, "UTF-8");
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");
        httpPost.setEntity(se);
        HttpResponse resp = httpClient.execute(httpPost);
        String respContent = null;
        if (resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he, "utf-8");
        }
        JSONObject jo = JSONObject.parseObject(respContent);
        JSONObject error = jo.getJSONObject("error");
        return error;

    }


}
