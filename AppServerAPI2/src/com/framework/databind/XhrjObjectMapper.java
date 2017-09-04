package com.framework.databind;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-24
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */
public class XhrjObjectMapper extends ObjectMapper {
    public XhrjObjectMapper(){
        super();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        setDateFormat(formatter);
    }
}
