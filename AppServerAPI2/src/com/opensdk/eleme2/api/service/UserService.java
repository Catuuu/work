package com.opensdk.eleme2.api.service;


import com.opensdk.eleme2.api.annotation.Service;
import com.opensdk.eleme2.api.base.BaseNopService;
import com.opensdk.eleme2.api.entity.user.OUser;
import com.opensdk.eleme2.api.exception.ServiceException;
import com.opensdk.eleme2.config.Config;
import com.opensdk.eleme2.oauth.response.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户服务
 */
@Service("eleme.user")
public class UserService extends BaseNopService {
    public UserService(Config config, Token token) {
        super(config, token, UserService.class);
    }

    /**
     * 获取商户账号信息
     *
     * @return 商户账号
     * @throws ServiceException 服务异常
     */
    public OUser getUser() throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        return call("eleme.user.getUser", params);
    }

    /**
     * 获取当前授权账号的手机号,特权接口仅部分帐号可以调用
     *
     * @return 手机号
     * @throws ServiceException 服务异常
     */
    public String getPhoneNumber() throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        return call("eleme.user.getPhoneNumber", params);
    }
}
