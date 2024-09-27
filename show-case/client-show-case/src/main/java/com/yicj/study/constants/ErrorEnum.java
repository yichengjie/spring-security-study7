package com.yicj.study.constants;


import lombok.Getter;

@Getter
public enum ErrorEnum {
    //common
    NOT_FOUND_ERROR(404, "NOT_FOUND_ERROR"),
    BAD_REQUEST_ERROR(400, "BAD_REQUEST_ERROR"),
    PERMISSION_DENIED_ERROR(403, "PERMISSION_DENIED_ERROR"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    SERVICE_UNAVAILABLE_ERROR(503, "SERVICE_UNAVAILABLE_ERROR"),
    GATEWAY_TIMEOUT(504, "GATEWAY_TIMEOUT"),
    REQUEST_PARAMETER_ERROR(1000, "REQUEST_PARAMETER_ERROR");

    private final int code;
    private final String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
