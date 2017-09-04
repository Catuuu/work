package com.framework.resource.action;

import com.framework.annotation.RsBeanField;
import com.framework.resource.interf.ButtonInterface;

/**
 * 按钮资源类
 * User: chenbin
 * Date: 13-3-8
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
public class ButtonAction extends ActionResource {
    @RsBeanField(describe = "按钮ID")
    private String buttonid;

    @RsBeanField(describe = "系统实体按钮")
    private ButtonInterface button;

    public String getButtonid() {
        return buttonid;
    }

    public void setButtonid(String buttonid) {
        this.buttonid = buttonid;
    }

    public ButtonInterface getButton() {
        return button;
    }

    public void setButton(ButtonInterface button) {
        this.button = button;
    }
}
