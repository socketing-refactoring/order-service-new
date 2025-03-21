package com.jeein.order.exception;

import lombok.Getter;

@Getter
public class FeignServiceException extends RuntimeException {
    private final ErrorCode errorCode;

    public FeignServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
