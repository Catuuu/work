package com.opensdk.shenhou.vo;

/**
 * Created by chenbin on 17/02/05.
 * 每个接口都需要传入的参数
 */
public class SystemParamShenhou {

    private String app_key;
    private String app_secret;
    private int isdebug;   //1、正式  2、测试

    public SystemParamShenhou(String app_key, String app_secret,int isdebug){
        this.setApp_key(app_key);
        this.setApp_secret(app_secret);
        this.isdebug = isdebug;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getApp_secret() {
        return app_secret;
    }

    public void setApp_secret(String app_secret) {
        this.app_secret = app_secret;
    }

    public String getUrl(){
        if(isdebug==1){
            return "http://api.shbj.com";     //正式地址
        }else{
            return "http://api.jiahuanle.com";//测试地址
        }

    }
}
