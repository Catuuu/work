package com.opensdk.eleme2.api.service;


import com.opensdk.eleme2.api.annotation.Service;
import com.opensdk.eleme2.api.base.BaseNopService;
import com.opensdk.eleme2.api.entity.ugc.OComment;
import com.opensdk.eleme2.api.exception.ServiceException;
import com.opensdk.eleme2.config.Config;
import com.opensdk.eleme2.oauth.response.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单评论服务
 */
@Service("eleme.ugc")
public class UgcService extends BaseNopService {
    public UgcService(Config config, Token token) {
        super(config, token, UgcService.class);
    }

    /**
     * openAPI 查询近2周的评论
     *
     * @param shopId 店铺Id
     * @param offset 分页偏移
     * @param limit 单页数据
     * @return 评论列表
     * @throws ServiceException 服务异常
     */
    public List<OComment> queryOrderComments(Integer shopId, Integer offset, Integer limit) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        params.put("offset", offset);
        params.put("limit", limit);
        return call("eleme.ugc.queryOrderComments", params);
    }

    /**
     * openAPI 查询近2周的评论数量
     *
     * @param shopId 店铺Id
     * @return 评论数量
     * @throws ServiceException 服务异常
     */
    public Long countOrderComments(Integer shopId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        return call("eleme.ugc.countOrderComments", params);
    }

    /**
     * openAPI 回复评论接口
     *
     * @param shopId 店铺Id
     * @param commentId 评论id
     * @param content 回复内容
     * @param replierName 回复人
     * @throws ServiceException 服务异常
     */
    public void replyOrderComment(Integer shopId, Long commentId, String content, String replierName) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        params.put("commentId", commentId);
        params.put("content", content);
        params.put("replierName", replierName);
        call("eleme.ugc.replyOrderComment", params);
    }
}
