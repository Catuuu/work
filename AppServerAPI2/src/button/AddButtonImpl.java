package button;

import com.framework.resource.interf.ButtonInterface;
import org.springframework.stereotype.Component;

/**
 * 添加按钮
 * User: Administrator
 * Date: 13-1-31
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AddButtonImpl implements ButtonInterface {

    public String getName() {
        return "添加";  //To change body of implemented methods use File | Settings | File Templates.
    }


    public String getIcon() {
        return "icon-add";  //To change body of implemented methods use File | Settings | File Templates.
    }


    public int getOrder() {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public String getRemark() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getCode() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
