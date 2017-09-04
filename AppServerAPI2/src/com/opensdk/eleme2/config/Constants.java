package com.opensdk.eleme2.config;

public abstract class Constants {

    /**
     * Date默认时区
     **/
    public static final String DATE_TIMEZONE = "GMT+8";

    /**
     * TOP默认时间格式
     **/
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * UTF-8字符集
     **/
    public static final String CHARSET_UTF8 = "UTF-8";
    /**
     * 默认流式读取缓冲区大小
     **/
    public static final int READ_BUFFER_SIZE = 1024 * 4;
    /**
     * 默认媒体类型
     **/
    public static final String MIME_TYPE_DEFAULT = "application/octet-stream";

    /**
     * oauth 请求头部Key
     */
    public static final String HEAD_OAUTH_KEY = "Authorization";

    /**
     * 返回的错误码
     */
    public static final String ERROR_RESPONSE = "error_response";

}
