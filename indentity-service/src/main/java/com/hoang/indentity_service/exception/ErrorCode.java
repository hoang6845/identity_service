package com.hoang.indentity_service.exception;

public enum ErrorCode {
    UNKNOWN_ERROR(9999, "Unknown error"),
    INVALID_KEY(9998, "Invalid message key"),
    USER_EXISTED(1001, "User existed"),
    USER_NOT_EXISTED(1005, "User not existed"),
    USER_NOT_FOUND(1002, "User not found"),
    UNAUTHENTICATED(1006, "Unauthenticated"),
    USERNAME_INVALID(1003, "Username must be at least 3 characters long"),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters long");

    private int code;
    private String message;

     ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
