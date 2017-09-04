package com.opensdk.eleme2.api.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessException extends ServiceException {
    public BusinessException(ServiceException e) {
        super(formatCode(e.getCode()), e.getMessage());
    }

    public BusinessException(String message) {
        super("BIZ_EXCEPTION", message);
    }

    public BusinessException(String code, String message) {
        super(formatCode(code), message);
    }

    public static String formatCode(String code) {
        if (code == null)
            code = "UNKNOWN_CODE";
        String regEx = "[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(code.trim().toUpperCase());
        return "BIZ_" + m.replaceAll("_");
    }

    public static void main(String args[]) {

    }

}
