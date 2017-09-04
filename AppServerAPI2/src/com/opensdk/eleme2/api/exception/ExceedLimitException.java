package com.opensdk.eleme2.api.exception;

public class ExceedLimitException extends ServiceException {
    public ExceedLimitException() {
        super("EXCEED_LIMIT", "超出资源访问限制");
    }

    public ExceedLimitException(String message) {
        super("EXCEED_LIMIT", message);
    }

}
