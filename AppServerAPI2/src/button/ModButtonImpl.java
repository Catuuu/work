package button;

import com.framework.resource.interf.ButtonInterface;
import org.springframework.stereotype.Component;

/**
 * 修改按钮.
 * User: chenbin
 * Date: 13-1-31
 * Time: 上午10:35
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ModButtonImpl implements ButtonInterface {

    public String getName() {
        return "修改";  //To change body of implemented methods use File | Settings | File Templates.
    }


    public String getIcon() {
        return "icon-edit";  //To change body of implemented methods use File | Settings | File Templates.
    }


    public int getOrder() {
        return 2;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public String getRemark() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public String getCode() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
