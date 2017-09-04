package com.framework.mapping.system;

/**
 * 发送手机短信
 */
public class MessageCode {
    private String phone;//手机号码
    private String content;//短信内容
    private int mc_type;//短信类型

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMc_type() {
        return mc_type;
    }

    public void setMc_type(int mc_type) {
        this.mc_type = mc_type;
    }
}
