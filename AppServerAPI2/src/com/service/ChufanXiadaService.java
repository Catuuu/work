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
@Service("ChufanXiadaService")
public class ChufanXiadaService extends BasicService {
    /**
     * 添加下达任务
     *
     * @param cdsGoodsTask
     * @throws Exception
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public CdsGoodsTaskRecord task_add(CdsGoodsTask cdsGoodsTask) throws Exception {

        CdsGoodsTaskRecord cdsGoodsTaskRecord = new CdsGoodsTaskRecord();
        cdsGoodsTaskRecord.setTar_id(StringUtil.getPrimaryOrderKey());
        cdsGoodsTaskRecord.setStores_id(cdsGoodsTask.getStores_id());
        cdsGoodsTaskRecord.setGood_id(cdsGoodsTask.getGood_id());
        cdsGoodsTaskRecord.setGood_name(cdsGoodsTask.getGood_name());
        cdsGoodsTaskRecord.setGood_count(cdsGoodsTask.getGood_count());
        cdsGoodsTaskRecord.setBefore_good_count(0);
        cdsGoodsTaskRecord.setAfter_good_count(cdsGoodsTask.getGood_count());
        cdsGoodsTaskRecord.setCreatetime(cdsGoodsTask.getCreatetime());
        cdsGoodsTaskRecord.setGtr_status(1);
        cdsGoodsTaskRecord.setEs_id(cdsGoodsTask.getEs_id());
        cdsGoodsTaskRecord.setCm_id(cdsGoodsTask.getCm_id());
        cdsGoodsTaskRecord.setTask_id(cdsGoodsTask.getTask_id());
        cdsGoodsTaskRecord.setIs_do(0);
        cdsGoodsTaskRecord.addParamFields();

        sqlDao.insertRecord("cds_chufan_xiada.insertRecord", cdsGoodsTask);
        sqlDao.insertRecord(cdsGoodsTaskRecord);
        return cdsGoodsTaskRecord;
    }

    /**
     * 取消下达任务
     *
     * @param cdsGoodsTask
     * @throws Exception
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public CdsGoodsTaskRecord task_cancel(CdsGoodsTask cdsGoodsTask, int cancel_count) throws Exception {
        if (cdsGoodsTask.getGood_count() - cdsGoodsTask.getFinsh_count() >= cancel_count) {
            CdsGoodsTaskRecord cdsGoodsTaskRecord = new CdsGoodsTaskRecord();

            cdsGoodsTaskRecord.setBefore_good_count(cdsGoodsTask.getGood_count()); //取消这前份数
            cdsGoodsTask.setGood_count(cdsGoodsTask.getGood_count() - cancel_count);
            cdsGoodsTaskRecord.setAfter_good_count(cdsGoodsTask.getGood_count());  //取消之后份数

            if (cdsGoodsTask.getGood_count() == 0) {
                cdsGoodsTask.setTask_status(3);
            } else if (cdsGoodsTask.getGood_count() > cdsGoodsTask.getFinsh_count()) {
                cdsGoodsTask.setTask_status(1);
            } else {
                cdsGoodsTask.setTask_status(2);
            }
            cdsGoodsTask.addParamFields("good_count,task_status");
            cdsGoodsTask.addConditionField("task_id");


            cdsGoodsTaskRecord.setTar_id(StringUtil.getPrimaryOrderKey());
            cdsGoodsTaskRecord.setStores_id(cdsGoodsTask.getStores_id());
            cdsGoodsTaskRecord.setGood_id(cdsGoodsTask.getGood_id());
            cdsGoodsTaskRecord.setGood_name(cdsGoodsTask.getGood_name());

            cdsGoodsTaskRecord.setGood_count(cancel_count);
            cdsGoodsTaskRecord.setCreatetime(new Date());
            cdsGoodsTaskRecord.setGtr_status(2);
            cdsGoodsTaskRecord.setEs_id(cdsGoodsTask.getEs_id());
            cdsGoodsTaskRecord.setCm_id(cdsGoodsTask.getCm_id());
            cdsGoodsTaskRecord.setTask_id(cdsGoodsTask.getTask_id());
            cdsGoodsTaskRecord.setIs_do(0);
            cdsGoodsTaskRecord.addParamFields();

            sqlDao.updateRecord(cdsGoodsTask);
            sqlDao.insertRecord(cdsGoodsTaskRecord);
            return cdsGoodsTaskRecord;
        }
        return null;
    }
}
