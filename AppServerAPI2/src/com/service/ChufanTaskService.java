package com.service;


import com.framework.mapping.system.CdsGoodsTask;
import com.framework.mapping.system.CdsGoodsTaskRecord;
import com.framework.service.BasicService;
import com.framework.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * Created by c on 2017-01-30.
 */
@Service("ChufanTaskService")
public class ChufanTaskService extends BasicService {
    /**
     * 添加出餐记录
     *
     * @param cdsGoodsTask
     * @throws Exception
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void task_add(CdsGoodsTask cdsGoodsTask, int good_count) throws Exception {

        CdsGoodsTaskRecord cdsGoodsTaskRecord = new CdsGoodsTaskRecord();
        cdsGoodsTaskRecord.setTar_id(StringUtil.getPrimaryOrderKey());
        cdsGoodsTaskRecord.setStores_id(cdsGoodsTask.getStores_id());
        cdsGoodsTaskRecord.setGood_id(cdsGoodsTask.getGood_id());
        cdsGoodsTaskRecord.setGood_name(cdsGoodsTask.getGood_name());
        cdsGoodsTaskRecord.setGood_count(good_count);
        cdsGoodsTaskRecord.setCreatetime(cdsGoodsTask.getTask_finsh_time());
        cdsGoodsTaskRecord.setGtr_status(3);
        cdsGoodsTaskRecord.setEs_id(cdsGoodsTask.getEs_id());
        cdsGoodsTaskRecord.setCm_id(cdsGoodsTask.getCm_id());
        cdsGoodsTaskRecord.setTask_id(cdsGoodsTask.getTask_id());
        cdsGoodsTaskRecord.setIs_do(0);
        cdsGoodsTaskRecord.addParamFields();

        cdsGoodsTaskRecord.setBefore_good_count(cdsGoodsTask.getGood_count()-cdsGoodsTask.getFinsh_count());
        cdsGoodsTask.setFinsh_count(cdsGoodsTask.getFinsh_count() + good_count);
        cdsGoodsTaskRecord.setAfter_good_count(cdsGoodsTask.getGood_count()-cdsGoodsTask.getFinsh_count());



        if (cdsGoodsTask.getGood_count() <= cdsGoodsTask.getFinsh_count()) {
            cdsGoodsTask.setTask_status(2);
            cdsGoodsTask.addParamFields("finsh_count,task_status,task_finsh_time");
        } else {
            cdsGoodsTask.addParamFields("finsh_count");
        }
        cdsGoodsTask.addConditionField("task_id");


        sqlDao.updateRecord(cdsGoodsTask);
        sqlDao.insertRecord(cdsGoodsTaskRecord);
    }

    /**
     * 取消下达任务
     *
     * @param cdsGoodsTask
     * @throws Exception
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void task_cancel(CdsGoodsTask cdsGoodsTask, CdsGoodsTaskRecord cdsGoodsTaskRecord) throws Exception {
        if (cdsGoodsTask.getFinsh_count() >= cdsGoodsTaskRecord.getGood_count()) {

            cdsGoodsTask.setFinsh_count(cdsGoodsTask.getFinsh_count() - cdsGoodsTaskRecord.getGood_count());
            if (cdsGoodsTask.getGood_count() == 0) {
                cdsGoodsTask.setTask_status(3);
            } else if (cdsGoodsTask.getGood_count() > cdsGoodsTask.getFinsh_count()) {
                cdsGoodsTask.setTask_status(1);
            } else {
                cdsGoodsTask.setTask_status(2);
            }
            if (cdsGoodsTask.getTask_status() != 3) {
                cdsGoodsTask.setTask_status(1);
                cdsGoodsTask.resetParamFields("finsh_count,task_status");
            } else {
                cdsGoodsTask.resetParamFields("finsh_count");
            }
            cdsGoodsTask.resetConditionFields("task_id");

            cdsGoodsTaskRecord.setIs_do(1);//设置为已取消
            cdsGoodsTaskRecord.resetParamFields("is_do");
            cdsGoodsTaskRecord.resetConditionFields("tar_id");

            CdsGoodsTaskRecord cdsGoodsTaskRecord2 = new CdsGoodsTaskRecord();
            cdsGoodsTaskRecord2.setBefore_good_count(cdsGoodsTask.getFinsh_count());
            cdsGoodsTaskRecord2.setAfter_good_count(cdsGoodsTask.getFinsh_count() - cdsGoodsTaskRecord.getGood_count());
            cdsGoodsTaskRecord2.setTar_id(StringUtil.getPrimaryOrderKey());
            cdsGoodsTaskRecord2.setStores_id(cdsGoodsTask.getStores_id());
            cdsGoodsTaskRecord2.setGood_id(cdsGoodsTask.getGood_id());
            cdsGoodsTaskRecord2.setGood_name(cdsGoodsTask.getGood_name());
            cdsGoodsTaskRecord2.setGood_count(cdsGoodsTaskRecord.getGood_count());
            cdsGoodsTaskRecord2.setCreatetime(new Date());
            cdsGoodsTaskRecord2.setGtr_status(4);
            cdsGoodsTaskRecord2.setEs_id(cdsGoodsTask.getEs_id());
            cdsGoodsTaskRecord2.setCm_id(cdsGoodsTask.getCm_id());
            cdsGoodsTaskRecord2.setTask_id(cdsGoodsTask.getTask_id());
            cdsGoodsTaskRecord2.setIs_do(0);
            cdsGoodsTaskRecord2.addParamFields();

            sqlDao.updateRecord(cdsGoodsTask);
            sqlDao.updateRecord(cdsGoodsTaskRecord);
            sqlDao.insertRecord(cdsGoodsTaskRecord2);
        }
    }
}
