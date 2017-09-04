package com.framework.exception;

/**
 * 无法找到注解异常
 * User: Administrator
 * Date: 13-1-6
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
public class NotAnnotationException extends Exception{

    public NotAnnotationException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }
    public NotAnnotationException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }
    public NotAnnotationException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
    public NotAnnotationException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
