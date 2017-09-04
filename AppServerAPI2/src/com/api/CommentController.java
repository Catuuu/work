package com.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.JsonMessage;
import com.framework.mapping.system.CdsFrominComments;
import com.framework.mapping.system.CdsOrderInfo;
import com.framework.mapping.system.CdsStoresBrand;
import com.framework.util.CommentUtil;
import com.framework.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

import static com.framework.annotation.CheckType.NO_CHECK;

/**
 * 评价信息模块
 * Created by Administrator on 2017/7/10 0010.
 */
@Controller
@RequestMapping("/CommentApi")
public class CommentController extends BasicController {

    @RequestMapping(value = "comment/list", method = RequestMethod.POST)
    @ResourceMethod(name = "评价列表", check = NO_CHECK)
    @ResponseBody
    public void commentList(@RequestParam HashMap formInfo) throws Exception {
        Integer stores_id = WebUtil.getSession("stores_id");
        formInfo.put("stores_id",stores_id);
        queryAndResponsePage("cds_comment.getCommentList", formInfo);
    }

    @RequestMapping(value = "comment/replyEleme", method = RequestMethod.POST)
    @ResourceMethod(name = "饿了么回复", check = NO_CHECK)
    @ResponseBody
    public JsonMessage replyEleme(@RequestParam HashMap formInfo) throws Exception {
        String ratingType = formInfo.get("ratingType").toString();
        JSONObject error = CommentUtil.replyElemeComment(formInfo);
        String message = "回复成功";
        String replyContent = formInfo.get("content").toString();
        if (error != null) {
            message = "该评论在饿了么平台已回复";
            replyContent = message;
        }

        if ("ORDER".equals(ratingType)) {
            CdsFrominComments comments = new CdsFrominComments();
            comments.setReplied(1);
            comments.setRating_id(formInfo.get("ratingId").toString());
            comments.setReply_time(new Date());
            comments.setReplycontent(replyContent);
            comments.setReply_id(WebUtil.getUser().getId());
            comments.setReplyer(WebUtil.getUser().getUser_nicename());
            comments.resetParamFields("replycontent,reply_time,replied,replyer,reply_id");
            comments.addConditionField("rating_id");
            sqlDao.updateRecord(comments);

        } else if ("FOOD".equals(ratingType)) {
            String foodRatingId = formInfo.get("ratingId").toString();
            final String foodReplyContent = replyContent;

            CdsFrominComments comments = new CdsFrominComments();
            comments.setRating_id(formInfo.get("rating_id").toString());
            comments.addConditionField("rating_id");
            comments = sqlDao.getRecord(comments);

            JSONArray foodList = JSONArray.parseArray(comments.getGoodsinfo());
            List goodsInfoList = foodList.stream()
                    .map(n -> {
                        JSONObject m = JSONObject.parseObject(n.toString());
                        if(foodRatingId.equals(m.getString("ratingId"))){
                            m.put("foodReplyContent", foodReplyContent);
                            m.put("replied","true");
                        }
                        return m;
                    }).collect(Collectors.toList());
            String goodsInfo = JSON.toJSONString(goodsInfoList);

            comments.setReply_time(new Date());
            comments.setGoodsinfo(goodsInfo);
            comments.setReply_id(WebUtil.getUser().getId());
            comments.setReplyer(WebUtil.getUser().getUser_nicename());
            comments.resetParamFields("goodsinfo,reply_time,replyer,reply_id");
            comments.addConditionField("rating_id");
            sqlDao.updateRecord(comments);
        }

        return new JsonMessage(1, message);
    }

    @RequestMapping(value = "comment/shops", method = RequestMethod.GET)
    @ResourceMethod(name = "门店分类", check = NO_CHECK)
    @ResponseBody
    public Object getBrandShops(String fromin) throws Exception {
        List cdsshops = new ArrayList();
        HashMap all = new HashMap();
        all.put("id", 0);
        all.put("text", "全部");
        cdsshops.add(all);

        List<CdsStoresBrand> cdsshops1 = sqlDao.getRecordList("cds_comment.shops");
        Map<String, List<CdsStoresBrand>> brandGroups = cdsshops1.stream()
                .collect(Collectors.groupingBy(CdsStoresBrand::getBrand_name));

        brandGroups.forEach((k, v) -> {
            HashMap brandMap = new HashMap();
            brandMap.put("id", k);
            brandMap.put("text", k);

            if ("eleme".equals(fromin)) {
                List shops = v.stream()
                        .filter(f -> !"".equals(f.getElem_restaurant_id()) && null != f.getElem_restaurant_id())
                        .map((CdsStoresBrand m) -> {
                            HashMap map = new HashMap();
                            map.put("id", m.getElem_restaurant_id());
                            map.put("text", m.getStores_name());
                            map.put("brandName", k);
                            return map;
                        }).collect(Collectors.toList());
                brandMap.put("children", shops);

            } else if ("meituan".equals(fromin)) {
                List shops = v.stream()
                        .map((CdsStoresBrand m) -> {
                            HashMap map = new HashMap();
                            map.put("id", m.getStores_brand_id());
                            map.put("text", m.getStores_name());
                            map.put("brandName", k);
                            return map;
                        }).collect(Collectors.toList());
                brandMap.put("children", shops);
            }
            cdsshops.add(brandMap);
        });

        return cdsshops;
    }

    @RequestMapping(value = "comment/order", method = RequestMethod.GET)
    @ResourceMethod(name = "订单穿透", check = NO_CHECK)
    @ResponseBody
    public JsonMessage showOrderInfo(CdsOrderInfo order) throws Exception {
        order.addConditionField("order_desc");
        order = sqlDao.getRecord(order);
        return new JsonMessage(1, "获取数据成功", order);
    }
}
