package com.framework.mapping;

import com.framework.mapping.system.CdsUsers;
import com.framework.mapping.system.SysUser;

import java.util.HashMap;

/**
 *  翻页参数组合.
 * User: Administrator
 * Date: 13-1-8
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
public class PageParam extends HashMap {

    public void setPageInfo(PageInfo pageInfo) {
        super.put("pageInfo", pageInfo);
    }

    public void setFormInfo(Object formInfo) {
        super.put("formInfo", formInfo);
    }

    public void setLoginUser(CdsUsers loginUser) {
        super.put("loginUser", loginUser);
    }

    public PageInfo getPageInfo() {
        return (PageInfo) super.get("pageInfo");
    }

    public Object getFormInfo() {
        return super.get("formInfo");
    }

    public SysUser getLoginUser() {
        return (SysUser) super.get("loginUser");
    }

    public void setHead(String head) {
        super.put("head", head);
    }

    public void setFoot(String foot) {
        super.put("foot", foot);
    }

    public void setFieldSort(String fieldSort) {
        super.put("fieldSort", fieldSort);
    }

    public String getHead() {
        return (String) super.get("head");
    }

    public String getFoot() {
        return (String) super.get("foot");
    }

    public String getFieldSort() {
        return (String) super.get("fieldSort");
    }
}
