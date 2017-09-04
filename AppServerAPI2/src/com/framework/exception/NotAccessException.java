package com.framework.exception;

/**
 * 无权访问异常
 * User: Administrator
 * Date: 13-1-6
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
public class NotAccessException extends Exception{

    public NotAccessException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }
    public NotAccessException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }
    public NotAccessException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
    public NotAccessException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
