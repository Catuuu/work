package com.framework.exception;

/**
 * 未登录异常
 * User: Administrator
 * Date: 13-1-6
 * Time: 下午4:36
 * To change this template use File | Settings | File Templates.
 */
public class NotLoginException extends Exception {

    public NotLoginException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }
    public NotLoginException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }
    public NotLoginException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
    public NotLoginException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
