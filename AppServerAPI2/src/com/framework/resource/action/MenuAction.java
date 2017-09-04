package com.framework.resource.action;

import com.framework.annotation.RsBeanField;
import com.framework.annotation.RsTreeNode;
import com.framework.annotation.RsTreeNodeTag;
import com.framework.resource.interf.RuleInterface;

import java.util.List;

/**
 * 菜单资源类
 * User: chenbin
 * Date: 13-3-8
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
public class MenuAction extends ActionResource {
    @RsBeanField(describe = "规则集合")
    @RsTreeNode(tagName = RsTreeNodeTag.attributes)
    private List<RuleInterface> ruleList;
    @RsBeanField(describe = "菜单按钮")
    @RsTreeNode(tagName = RsTreeNodeTag.attributes)
    private List<ButtonAction> buttonList;

    public List<RuleInterface> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<RuleInterface> ruleList) {
        this.ruleList = ruleList;
    }

    public List<ButtonAction> getButtonList() {
        return buttonList;
    }

    public void setButtonList(List<ButtonAction> buttonList) {
        this.buttonList = buttonList;
    }
}
