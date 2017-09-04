package com.opensdk.eleme.vo;

/**
 * Created by chenbin on 17/02/05.
 * 每个接口都需要传入的参数
 */
public class SystemParamEleme {

    private String consumer_key;
    private String consumer_secret;

    public SystemParamEleme(String appId, String appSecret){
        this.setConsumer_key(appId);
        this.setConsumer_secret(appSecret);
    }

    public String getConsumer_key() {
        return consumer_key;
    }

    public void setConsumer_key(String consumer_key) {
        this.consumer_key = consumer_key;
    }

    public String getConsumer_secret() {
        return consumer_secret;
    }

    public void setConsumer_secret(String consumer_secret) {
        this.consumer_secret = consumer_secret;
    }
}
