package com.opensdk.eleme.vo;


import java.util.List;

/**
 * Created by chenbin on 2017/2/7.
 * 就是浇头，可以添加到食物里，比如点一个荷包蛋，加到炒饭这个food的garnish里
 */
public class OrderDetailParam {
    private List<OrderFoodDetailParam> group;  //订单篮子
    private List<OrderExtraParam> extra ; //extra用于传递额外的参数

    public List<OrderFoodDetailParam> getGroup() {
        return group;
    }

    public void setGroup(List<OrderFoodDetailParam> group) {
        this.group = group;
    }

    public List<OrderExtraParam> getExtra() {
        return extra;
    }

    public void setExtra(List<OrderExtraParam> extra) {
        this.extra = extra;
    }
}
