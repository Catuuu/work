package com.service;


import com.alibaba.fastjson.JSONArray;
import com.framework.mapping.system.*;
import com.framework.service.BasicService;
import com.framework.util.DateUtil;
import com.framework.util.PrintUtil;
import com.framework.util.StringUtil;
import com.framework.util.WebUtil;
import com.opensdk.weixin.factory.APIFactoryWeixin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;


/**
 * Created by c on 2017-01-30.
 * 配送信息
 */
@Service("SendService")
public class SendService extends BasicService {



    /**
     * 发送配送信息
     * @param cdsOrderInfo
     */
   public void send(CdsOrderInfo cdsOrderInfo) throws Exception{


   }

}
