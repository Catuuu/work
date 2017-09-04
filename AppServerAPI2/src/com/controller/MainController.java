package com.controller;

import com.MessageRunnable;
import com.framework.annotation.ResourceMethod;
import com.framework.controller.BasicController;
import com.framework.mapping.system.CdsMember;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

import static com.framework.annotation.CheckType.NO_CHECK;

/**
 * Created by c on 2017-01-26.
 */
@Controller
@RequestMapping("main")
public class MainController extends BasicController {

    @Resource(name = "taskExecutor")
    protected ThreadPoolTaskExecutor poolTaskExecutor;

    public static boolean block = true;

    @RequestMapping(value = "index",method = RequestMethod.GET)
    @ResourceMethod(name = "接收饿了么推送消息", check = NO_CHECK)
    public String index() {

       /* if(block){
            MessageRunnable123 r = new MessageRunnable123();
            Thread t = new Thread(r);//创建线程
            t.start();
            block = false;
        }*/

       // List<CdsMember> list = sqlDao.getRecordList("cds_member.getRecordList");


        return "index";
    }

    class MessageRunnable123 implements Runnable {

        public void run() {
            /*String sms_content = "【菜大师】"+"元宵节快乐！叫外卖，就点菜大师！菜大师庆元宵半价活动满25减14，满40减20。一直到月底哦～你还不来订餐吗？";
            boolean flag= true;

            while (flag){
                try {
                    while (poolTaskExecutor.getActiveCount()>0){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            //e.printStackTrace();
                        }
                    }
                    List<CdsMember> list = sqlDao.getRecordList("cds_member.getRecordList");
                    if(list.size()==0){
                        flag = false;
                    }

                    for (CdsMember member:list) {
                        MessageRunnable r = new MessageRunnable(sqlDao,member,sms_content);
                        Thread udpThread = new Thread(r);//创建线程
                        poolTaskExecutor.execute(udpThread);
                    }


                }catch (Exception e){

                }

            }*/

        }
    }

}
