package com.framework.mapping.system;

import com.framework.annotation.RsTable;
import com.framework.annotation.RsTableField;
import com.framework.mapping.BaseMapping;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 *
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("CdsWinxinOrderNo")
@RsTable(describe = "菜大师微信待处理订单", name = "cds_winxin_order_no")
public class CdsWinxinOrderNo extends BaseMapping {

    @RsTableField(describe = "订单编号", name = "order_id", primaryKey = true)
    private String order_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
