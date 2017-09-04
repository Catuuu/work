package com.opensdk.eleme2.api.service;


import com.opensdk.eleme2.api.annotation.Service;
import com.opensdk.eleme2.api.base.BaseNopService;
import com.opensdk.eleme2.api.entity.message.OMessage;
import com.opensdk.eleme2.api.exception.ServiceException;
import com.opensdk.eleme2.config.Config;
import com.opensdk.eleme2.oauth.response.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息服务
 */
@Service("eleme.message")
public class MessageService extends BaseNopService {
    public MessageService(Config config, Token token) {
        super(config, token, MessageService.class);
    }

    /**
     * 获取未到达的推送消息
     *
     * @param appId 应用ID
     * @return 消息列表
     * @throws ServiceException 服务异常
     */
    public List<String> getNonReachedMessages(int appId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appId", appId);
        return call("eleme.message.getNonReachedMessages", params);
    }

    /**
     * 获取未到达的推送消息实体
     *
     * @param appId 应用ID
     * @return 消息列表
     * @throws ServiceException 服务异常
     */
    public List<OMessage> getNonReachedOMessages(int appId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appId", appId);
        return call("eleme.message.getNonReachedOMessages", params);
    }
}
