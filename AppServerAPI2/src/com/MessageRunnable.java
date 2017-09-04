package com;

import com.framework.controller.BasicComponent;
import com.framework.dao.SqlDao;
import com.framework.mapping.system.CdsMember;
import com.framework.mapping.system.CdsMessageCode;
import com.framework.mapping.system.CdsStores;
import com.framework.util.MessageUtil;
import com.framework.util.StringUtil;
import com.service.StoresService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;

import java.util.Date;

/**
 * Created by c on 2017-01-26.
 */
public class MessageRunnable  implements Runnable {

    private StoresService storesService;
    private int index;
    public MessageRunnable(StoresService storesService,int index){
        this.storesService = storesService;
        this.index = index;
    }
    public void run() {

        try {

        }catch (RedisConnectionFailureException exception){
            System.out.println("异常!!!!!!!!!!!");
        }


        CdsStores stores = storesService.GetStores(9);
        System.out.println(index+"、"+stores.getName()+"  "+stores.getAddress());

        stores = storesService.GetStores(10);
        System.out.println(index+"、"+stores.getName()+"  "+stores.getAddress());

        stores = storesService.GetStores(11);
        System.out.println(index+"、"+stores.getName()+"  "+stores.getAddress());

        stores = storesService.GetStores(12);
        System.out.println(index+"、"+stores.getName()+"  "+stores.getAddress());

        stores = storesService.GetStores(13);
        System.out.println(index+"、"+stores.getName()+"  "+stores.getAddress());

        stores = storesService.GetStores(14);
        System.out.println(index+"、"+stores.getName()+"  "+stores.getAddress());

        stores = storesService.GetStores(15);
        System.out.println(index+"、"+stores.getName()+"  "+stores.getAddress());

        stores = storesService.GetStores(16);
        System.out.println(index+"、"+stores.getName()+"  "+stores.getAddress());
    }
}
