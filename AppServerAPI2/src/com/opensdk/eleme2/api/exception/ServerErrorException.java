package com.opensdk.eleme2.api.exception;

public class ServerErrorException extends RuntimeException {
    private String code = "SERVER_ERROR";

    public ServerErrorException() {
        super("服务异常，请重试");
    }

    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException(String code, String message) {
        this(message);
        this.setCode(code);
    }

    public ServerErrorException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    public ServerErrorException(String code, Throwable cause) {
        super(cause);
        this.setCode(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
