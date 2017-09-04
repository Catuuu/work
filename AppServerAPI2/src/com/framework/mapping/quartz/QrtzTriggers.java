package com.framework.mapping.quartz;

import com.framework.annotation.*;
import com.framework.mapping.BaseMapping;
import com.framework.util.DateUtil;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 已配置任务对象
 * User: chenbin
 * Date: 12-12-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
@Alias("QrtzTriggers")
@RsTable(describe = "已配置任务", name = "QRTZ_TRIGGERS")
public class QrtzTriggers extends BaseMapping {
    @RsTableField(describe = "调度器名称", name = "SCHED_NAME")
    private String sched_name;

    @RsTableField(describe = "触发器名称", name = "TRIGGER_NAME")
    private String trigger_name;

    @RsTableField(describe = "触发器组名称", name = "TRIGGER_GROUP")
    @RsTreeNode(tagName = RsTreeNodeTag.text)
    private String trigger_group;

    @RsTableField(describe = "工作名称", name = "JOB_NAME")
    private String job_name;

    @RsTableField(describe = "工作组名称", name = "JOB_GROUP")
    private String job_group;

    @RsTableField(describe = "描述信息", name = "DESCRIPTION")
    private String description;

    @RsTableField(describe = "下次执行时间", name = "NEXT_FIRE_TIME")
    private long next_fire_time;

    @RsTableField(describe = "上次执行时间", name = "PREV_FIRE_TIME")
    private long prev_fire_time;

    @RsTableField(describe = "优先级", name = "PRIORITY")
    private int priority;

    @RsTableField(describe = "触发器状态", name = "TRIGGER_STATE")
    private String trigger_state;

    @RsTableField(describe = "触发器类型", name = "TRIGGER_TYPE")
    private String trigger_type;

    @RsTableField(describe = "开始时间", name = "START_TIME")
    private long start_time;

    @RsTableField(describe = "结束时间", name = "END_TIME")
    private long end_time;

    @RsBeanField(describe = "下次执行时间(yyyy-MM-dd HH:mm:ss)")
    private String next_fire_time_str;
    @RsBeanField(describe = "上次执行时间(yyyy-MM-dd HH:mm:ss)")
    private String prev_fire_time_str;
    @RsBeanField(describe = "开始时间(yyyy-MM-dd HH:mm:ss)")
    private String start_time_str;
    @RsBeanField(describe = "结束时间(yyyy-MM-dd HH:mm:ss)")
    private String end_time_str;


    @RsBeanField(describe = "开始时间")
    private Date startDate;
    @RsBeanField(describe = "结束时间")
    private Date endDate;
    @RsBeanField(describe = "间隔时间")
    private int repeatCount;
    @RsBeanField(describe = "间隔时间")
    private long repeatInterval;
    @RsBeanField(describe = "Cron表达式")
    private String cronExpression;

    public String getSched_name() {
        return sched_name;
    }

    public void setSched_name(String sched_name) {
        this.sched_name = sched_name;
    }

    public String getTrigger_name() {
        return trigger_name;
    }

    public void setTrigger_name(String trigger_name) {
        this.trigger_name = trigger_name;
    }

    public String getTrigger_group() {
        return trigger_group;
    }

    public void setTrigger_group(String trigger_group) {
        this.trigger_group = trigger_group;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getJob_group() {
        return job_group;
    }

    public void setJob_group(String job_group) {
        this.job_group = job_group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getNext_fire_time() {
        return next_fire_time;
    }

    public void setNext_fire_time(long next_fire_time) {
        this.next_fire_time = next_fire_time;
        if (0 < next_fire_time) {
            this.next_fire_time_str = DateUtil.timeToStr(new Date(next_fire_time));
        }
    }

    public long getPrev_fire_time() {
        return prev_fire_time;
    }

    public void setPrev_fire_time(long prev_fire_time) {
        this.prev_fire_time = prev_fire_time;
        if (0 < prev_fire_time) {
            this.prev_fire_time_str = DateUtil.timeToStr(new Date(prev_fire_time));
        }
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTrigger_state() {
        return trigger_state;
    }

    public void setTrigger_state(String trigger_state) {
        this.trigger_state = trigger_state;
    }

    public String getTrigger_type() {
        return trigger_type;
    }

    public void setTrigger_type(String trigger_type) {
        this.trigger_type = trigger_type;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
        if (0 < start_time) {
            this.start_time_str = DateUtil.timeToStr(new Date(start_time));
        }
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
        if (0 < end_time) {
            this.end_time_str = DateUtil.timeToStr(new Date(end_time));
        }
    }

    public String getNext_fire_time_str() {
        return next_fire_time_str;
    }

    public String getEnd_time_str() {
        return end_time_str;
    }

    public String getStart_time_str() {
        return start_time_str;
    }

    public String getPrev_fire_time_str() {
        return prev_fire_time_str;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
