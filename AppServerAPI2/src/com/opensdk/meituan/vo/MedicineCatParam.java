package com.opensdk.meituan.vo;

/**
 * Created by chenbin on 17/02/05.
 */
public class MedicineCatParam {

    private String app_poi_code;
    private String category_code;
    private String category_name;
    private String sequence;
    private String standard_code;

    public String getStandard_code() {
        return standard_code;
    }

    public void setStandard_code(String standard_code) {
        this.standard_code = standard_code;
    }

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "MedicineCatParam{" +
                "app_poi_code='" + app_poi_code + '\'' +
                ", category_code='" + category_code + '\'' +
                ", category_name='" + category_name + '\'' +
                ", sequence='" + sequence + '\'' +
                ", standard_code='" + standard_code + '\'' +
                '}';
    }
}
