package com.opensdk.gaode.vo;

/**
 * Created by chenbin on 17/02/05.
 * 每个接口都需要传入的参数
 */
public class SystemParamGaoDe {

    private String key;

    public SystemParamGaoDe(String key){
        this.setKey(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
