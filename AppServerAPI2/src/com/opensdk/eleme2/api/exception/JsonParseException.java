package com.opensdk.eleme2.api.exception;

public class JsonParseException extends ServerErrorException {

    public JsonParseException() {
        super("JSON_PARSE_ERROR", "JSON解析出错");
    }

    public JsonParseException(String message) {
        super("JSON_PARSE_ERROR", message);
    }

}
