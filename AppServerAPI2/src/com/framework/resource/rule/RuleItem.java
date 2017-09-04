package com.framework.resource.rule;

/**
 * 数据过滤一个选项对象.
 * User: Administrator
 * Date: 13-1-30
 * Time: 下午12:00
 * To change this template use File | Settings | File Templates.
 */
public class RuleItem {
    /**
     * 主键唯一标识
     */
    private String id;
    /**
     * 显示值
     */
    private String text;

    public RuleItem() {

    }

    public RuleItem(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
