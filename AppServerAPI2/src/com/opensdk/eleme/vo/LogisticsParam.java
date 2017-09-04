package com.opensdk.eleme.vo;

/**
 * Created by chenbin on 17/02/05.
 */
public class LogisticsParam {
    String result;
    String order_id;
    String logistics_status;
    String logistics_name;
    String send_time;
    String confirm_time;
    String cancel_time;
    String fetch_time;
    String completed_time;
    String dispatcher_name;
    String dispatcher_mobile;

    public String getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
    }

    public String getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(String cancel_time) {
        this.cancel_time = cancel_time;
    }

    public String getCompleted_time() {
        return completed_time;
    }

    public void setCompleted_time(String completed_time) {
        this.completed_time = completed_time;
    }

    public String getDispatcher_mobile() {
        return dispatcher_mobile;
    }

    public void setDispatcher_mobile(String dispatcher_mobile) {
        this.dispatcher_mobile = dispatcher_mobile;
    }

    public String getDispatcher_name() {
        return dispatcher_name;
    }

    public void setDispatcher_name(String dispatcher_name) {
        this.dispatcher_name = dispatcher_name;
    }

    public String getFetch_time() {
        return fetch_time;
    }

    public void setFetch_time(String fetch_time) {
        this.fetch_time = fetch_time;
    }

    public String getLogistics_name() {
        return logistics_name;
    }

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
    }

    public String getLogistics_status() {
        return logistics_status;
    }

    public void setLogistics_status(String logistics_status) {
        this.logistics_status = logistics_status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }
}
