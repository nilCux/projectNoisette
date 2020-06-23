package com.nilCux.backRacine.modules.enumeration;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  Some HTTP status codes
 */

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "SUCCESS"),
    FAILURE(400, "FAILURE"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    USER_UNAUTHORIZED(402, "USER_UNAUTHORIZED"),
    ACCESS_DENIED(403, "ACCESS_DENIED"),
    NOT_FOUND(404, "NOT_FOUND"),
    UNCHANGED(304, "UNCHANGED"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");

    private int code;
    private String desc;
}
