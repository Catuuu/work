package com.opensdk.eleme2.api.exception;


public class AccessDeniedException extends ServiceException {
    public AccessDeniedException() {
        super("ACCESS_DENIED", "拒绝访问");
    }

    public AccessDeniedException(String message) {
        super("ACCESS_DENIED", message);
    }
}
