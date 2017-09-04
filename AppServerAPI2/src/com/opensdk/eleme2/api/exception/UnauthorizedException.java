package com.opensdk.eleme2.api.exception;


public class UnauthorizedException extends ServiceException {
    public UnauthorizedException() {
        super("UNAUTHORIZED", "token认证失败,请重新申请token");
    }

    public UnauthorizedException(String message) {
        super("UNAUTHORIZED", message);
    }
}
