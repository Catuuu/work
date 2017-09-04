package com.opensdk.eleme2.api.service;


import com.opensdk.eleme2.api.annotation.Service;
import com.opensdk.eleme2.api.base.BaseNopService;
import com.opensdk.eleme2.api.entity.packs.ShopContract;
import com.opensdk.eleme2.api.exception.ServiceException;
import com.opensdk.eleme2.config.Config;
import com.opensdk.eleme2.oauth.response.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签约服务
 */
@Service("eleme.packs")
public class PacksService extends BaseNopService {
    public PacksService(Config config, Token token) {
        super(config, token, PacksService.class);
    }

    /**
     * 查询店铺当前生效合同类型
     *
     * @param shopId 店铺id
     * @return 当前店铺生效的服务包合同类型名称
     * @throws ServiceException 服务异常
     */
    public ShopContract getEffectServicePackContract(Long shopId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        return call("eleme.packs.getEffectServicePackContract", params);
    }
}
