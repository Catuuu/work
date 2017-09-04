package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("CdsFrominComments")
@RsTable(describe = "平台评论表", name = "cds_fromin_comments")
public class CdsFrominComments extends BaseMapping {
    @RsTableField(describe = "主键", name = "fc_id", primaryKey = true)
    private String fc_id;
    @RsTableField(describe = "订单id", name = "order_id")
    private String order_id;
    @RsTableField(describe = "第三方评价id", name = "rating_id")
    private String rating_id;
    @RsTableField(describe = "平台", name = "fromin")
    private String fromin;
    @RsTableField(describe = "第三方订单id", name = "fromin_order_id")
    private String fromin_order_id;
    @RsTableField(describe = "第三方店铺id", name = "fromin_stores_id")
    private String fromin_stores_id;
    @RsTableField(describe = "评论时间", name = "create_time")
    private Date create_time;
    @RsTableField(describe = "评论内容", name = "content")
    private String content;
    @RsTableField(describe = "评分", name = "rating")
    private Integer rating;
    @RsTableField(describe = "回复状态", name = "replied")
    private Integer replied;
    @RsTableField(describe = "回复内容", name = "replycontent")
    private String replycontent;
    @RsTableField(describe = "回复人", name = "replyer")
    private String replyer;
    @RsTableField(describe = "回复人id", name = "reply_id")
    private Integer reply_id;
    @RsTableField(describe = "回复时间", name = "reply_time")
    private Date reply_time;
    @RsTableField(describe = "店铺id", name = "stores_id")
    private Integer stores_id;
    @RsTableField(describe = "标签", name = "tags")
    private String tags;
    @RsTableField(describe = "商品信息", name = "goodsinfo")
    private String goodsinfo;
    @RsTableField(describe = "平台店铺名称", name = "fromin_stores_name")
    private String fromin_stores_name;
    @RsTableField(describe = "品牌ID", name = "brand_id")
    private int brand_id;

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }


    public String getFromin_stores_name() {
        return fromin_stores_name;
    }

    public void setFromin_stores_name(String fromin_stores_name) {
        this.fromin_stores_name = fromin_stores_name;
    }

    public String getFc_id() {
        return fc_id;
    }

    public void setFc_id(String fc_id) {
        this.fc_id = fc_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getRating_id() {
        return rating_id;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    public String getFromin() {
        return fromin;
    }

    public void setFromin(String fromin) {
        this.fromin = fromin;
    }

    public String getFromin_order_id() {
        return fromin_order_id;
    }

    public void setFromin_order_id(String fromin_order_id) {
        this.fromin_order_id = fromin_order_id;
    }

    public String getFromin_stores_id() {
        return fromin_stores_id;
    }

    public void setFromin_stores_id(String fromin_stores_id) {
        this.fromin_stores_id = fromin_stores_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getReplied() {
        return replied;
    }

    public void setReplied(Integer replied) {
        this.replied = replied;
    }

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent;
    }

    public String getReplyer() {
        return replyer;
    }

    public void setReplyer(String replyer) {
        this.replyer = replyer;
    }

    public Integer getReply_id() {
        return reply_id;
    }

    public void setReply_id(Integer reply_id) {
        this.reply_id = reply_id;
    }

    public Date getReply_time() {
        return reply_time;
    }

    public void setReply_time(Date reply_time) {
        this.reply_time = reply_time;
    }

    public Integer getStores_id() {
        return stores_id;
    }

    public void setStores_id(Integer stores_id) {
        this.stores_id = stores_id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getGoodsinfo() {
        return goodsinfo;
    }

    public void setGoodsinfo(String goodsinfo) {
        this.goodsinfo = goodsinfo;
    }
}
