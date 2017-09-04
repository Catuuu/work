package com.opensdk.eleme2.api.exception;


public class ValidationFailedException extends ServiceException {
    public ValidationFailedException() {
        super("VALIDATION_FAILED", "参数错误");
    }

    public ValidationFailedException(String message) {
        super("VALIDATION_FAILED", message);
    }
}
