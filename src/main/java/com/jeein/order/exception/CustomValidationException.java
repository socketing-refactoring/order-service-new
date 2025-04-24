package com.jeein.order.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class CustomValidationException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
