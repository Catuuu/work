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
public class TestDataRuleImpl implements RuleInterface {


    public RuleType getType() {
        return RuleType.dataFiltering;
    }


    public String getName() {
        return "test";
    }


    public String getKey() {
        return "test";
    }


    public List<RuleItem> getOptions() {
        List<RuleItem> list = new ArrayList<RuleItem>();
        list.add(new RuleItem("test1", "test1"));
        list.add(new RuleItem("test2", "test2"));
        list.add(new RuleItem("test3", "test3"));
        list.add(new RuleItem("test4", "test4"));
        return list;
    }


    public DataResource doProcessing(List<RuleItem> ruleItems) {
        DataResource dataResource = new DataResource();
        dataResource.put(getKey(), ruleItems);
        return dataResource;
    }
}
