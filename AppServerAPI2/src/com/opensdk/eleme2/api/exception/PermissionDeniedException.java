package com.opensdk.eleme2.api.exception;


public class PermissionDeniedException extends ServiceException {
    public PermissionDeniedException() {
        super("PERMISSION_DENIED", "权限不足");
    }

    public PermissionDeniedException(String message) {
        super("PERMISSION_DENIED", message);
    }
}
