package com.opensdk.eleme2.api.entity.ugc;

import java.util.*;
public class OComment{

    /**
     * 主键Id
     */
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * 订单Id
     */
    private String orderId;
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    /**
     * 评价内容
     */
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * 点评时间
     */
    private Date createTime;
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 是否回复
     */
    private Short replied;
    public Short getReplied() {
        return replied;
    }
    public void setReplied(Short replied) {
        this.replied = replied;
    }
    
    /**
     * 评价星级
     */
    private Integer rating;
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    /**
     * 用户名
     */
    private String userName;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * 订单的回复内容
     */
    private OOrderCommentReply reply;
    public OOrderCommentReply getReply() {
        return reply;
    }
    public void setReply(OOrderCommentReply reply) {
        this.reply = reply;
    }
    
}