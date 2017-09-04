package com.opensdk.eleme.constants;

import com.opensdk.eleme.exception.ExceptionEnum;

/**
 * CCreated by chenbin on 17/02/05.
 */
public enum ErrorEnum implements ExceptionEnum {

    SYS_ERR(700,"系统错误"),
    LACK_OF_PARAM(701,"缺少参数，数据不完整");

    private int code; // 错误码
    private String msg;//错误信息

    private ErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public static ErrorEnum findByErrorCode(int errorCode) {
        switch(errorCode) {
            case 700:
                return SYS_ERR;
            case 701:
                return LACK_OF_PARAM;
            default:
                return null;
        }
    }

    public String toString() {
        return "code:" + code + ", " + "msg:" + msg;
    }

}
