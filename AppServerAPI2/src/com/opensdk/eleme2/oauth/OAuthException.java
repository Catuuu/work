package com.opensdk.eleme2.oauth;

public class OAuthException extends Exception {

    private String errCode;
    private String errMsg;

    public OAuthException() {
        super();
    }

    public OAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuthException(String message) {
        super(message);
    }

    public OAuthException(Throwable cause) {
        super(cause);
    }

    public OAuthException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
