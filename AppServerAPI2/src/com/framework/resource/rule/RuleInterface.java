package com.framework.resource.rule;

import com.framework.resource.DataResource;

import java.io.Serializable;
import java.util.List;

/**
 * 规则接口
 * User: chenbin
 * Date: 13-1-30
 * Time: 上午11:41
 * To change this template use File | Settings | File Templates.
 */
public interface RuleInterface extends Serializable {
    /**
     * 规则类型
     *
     * @return
     */
    public RuleType getType();

    /**
     * 规则名称
     *
     * @return
     */
    public String getName();

    /**
     * 规则唯一标识
     *
     * @return
     */
    public String getKey();

    /**
     * 规则选项
     *
     * @return
     */
    public List<RuleItem> getOptions();

    /**
     * 规则输出方法
     *
     * @param ruleItems
     * @return
     */
    public DataResource doProcessing(List<RuleItem> ruleItems);
}
