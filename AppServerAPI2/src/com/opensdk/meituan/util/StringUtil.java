package com.opensdk.meituan.util;

/**
 * Created by chenbin on 17/02/05.
 */
public class StringUtil {

    public static boolean isBlank(Object o){
        if (o == null) return true;
        if ("".equals(trim(o.toString()))) return true;

        return false;
    }

    public static String trim(String s){
        String result = s.replaceAll(" +", "");
        return result;
    }

    public static void main(String[] args){
        trim("   ");
    }
}
