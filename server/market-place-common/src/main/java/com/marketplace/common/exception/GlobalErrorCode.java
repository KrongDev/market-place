package com.marketplace.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
    
    INTERNAL_SERVER_ERROR("G001", "Internal Server Error"),
    INVALID_INPUT("G002", "Invalid Input"),
    UNAUTHORIZED("G003", "Unauthorized"),
    FORBIDDEN("G004", "Forbidden"),
    NOT_FOUND("G005", "Resource Not Found");

    private final String code;
    private final String message;
}
