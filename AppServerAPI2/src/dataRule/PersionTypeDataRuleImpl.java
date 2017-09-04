package dataRule;

import com.framework.resource.DataResource;
import com.framework.resource.interf.RuleInterface;
import com.framework.resource.rule.RuleItem;
import com.framework.resource.rule.RuleType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 人员类型.
 * User: Administrator
 * Date: 13-1-30
 * Time: 上午11:52
 * To change this template use File | Settings | File Templates.
 */
@Component()
public class PersionTypeDataRuleImpl implements RuleInterface {


    public RuleType getType() {
        return RuleType.dataFiltering;
    }


    public String getName() {
        return "人员类型";
    }


    public String getKey() {
        return "persionType";
    }


    public List<RuleItem> getOptions() {
        List<RuleItem> list = new ArrayList<RuleItem>();
        list.add(new RuleItem("type1", "普通职员"));
        list.add(new RuleItem("type2", "组长"));
        list.add(new RuleItem("type3", "科长"));
        list.add(new RuleItem("type4", "处长"));
        return list;
    }


    public DataResource doProcessing(List<RuleItem> ruleItems) {
        DataResource dataResource = new DataResource();
        dataResource.put(getKey(), ruleItems);
        return dataResource;
    }
}
