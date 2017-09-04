package com.opensdk.eleme2.api.protocol;

import java.io.Serializable;

public class ResponsePayload implements Serializable {
    /**
     * Request id
     */
    private String id;

    /**
     * Invocation result
     */
    private Object result;

    /**
     * Invocation error
     */
    private ErrorPayload error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ErrorPayload getError() {
        return error;
    }

    public void setError(ErrorPayload error) {
        this.error = error;
    }
}