package com.opensdk.eleme2.api.exception;


public class InvalidSignatureException extends ServiceException {
    public InvalidSignatureException() {
        super("INVALID_SIGNATURE", "无效的签名");
    }

    public InvalidSignatureException(String message) {
        super("INVALID_SIGNATURE", message);
    }
}
