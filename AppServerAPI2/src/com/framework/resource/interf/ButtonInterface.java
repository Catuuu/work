package com.framework.resource.interf;

import java.io.Serializable;

/**
 * 系统按钮接口
 * User: chenbin
 * Date: 13-1-31
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
public interface ButtonInterface extends Serializable {
    //按钮名称
    public String getName();

    //按钮图标
    public String getIcon();

    //排序
    public int getOrder();

    //备注
    public String getRemark();

    //按钮默认代码
    public String getCode();
}
