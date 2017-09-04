package com.opensdk.eleme2.api.entity.ugc;

import java.util.*;
public class OOrderCommentReply{

    /**
     * 回复内容
     */
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * 回复时间
     */
    private Date createdTime;
    public Date getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    
    /**
     * 回复人
     */
    private String replierName;
    public String getReplierName() {
        return replierName;
    }
    public void setReplierName(String replierName) {
        this.replierName = replierName;
    }
    
}