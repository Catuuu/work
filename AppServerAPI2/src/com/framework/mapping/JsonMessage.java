package com.framework.mapping;

/**
 * Json消息对象.
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午12:05
 * To change this template use File | Settings | File Templates.
 */
public class JsonMessage {

    private String status = "T";//状态
    private String message = "成功";//消息
    private Object  obj; //附加对象

    public JsonMessage(){
    }

    public JsonMessage(String status){
        this.status = status;
    }

    public JsonMessage(Integer status){
        this.status = Integer.toString(status);
    }

    public JsonMessage(Integer status,String message){
        this.status = Integer.toString(status);
        this.message = message;
    }

    public JsonMessage(String status,String message){
        this.status = status;
        this.message = message;
    }

    public JsonMessage(String status,String message,Object obj){
        this.status = status;
        this.message = message;
        this.obj = obj;
    }

    public JsonMessage(Integer status,String message,Object obj){
        this.status = Integer.toString(status);
        this.message = message;
        this.obj = obj;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
