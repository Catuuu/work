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
public class PersionType3DataRuleImpl implements RuleInterface {


    public RuleType getType() {
        return RuleType.pageControls;
    }


    public String getName() {
        return "jjjjjjj";
    }


    public String getKey() {
        return "areaType2";
    }


    public List<RuleItem> getOptions() {
        List<RuleItem> list = new ArrayList<RuleItem>();
        list.add(new RuleItem("area1", "区"));
        list.add(new RuleItem("area2", "市"));
        list.add(new RuleItem("area3", "省"));
        list.add(new RuleItem("area4", "国"));
        return list;
    }


    public DataResource doProcessing(List<RuleItem> ruleItems) {
        DataResource dataResource = new DataResource();
        dataResource.put(getKey(), ruleItems);
        return dataResource;
    }
}
