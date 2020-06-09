package com.nilCux.backRacine.modules.exception;

public class CustomizedException extends RuntimeException {

    private Integer code;

    public CustomizedException(Throwable cause) {
        super(cause);
    }

    public CustomizedException(String message) {
        super(message);
    }

    public CustomizedException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CustomizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getCode() {
        return code;
    }

}
