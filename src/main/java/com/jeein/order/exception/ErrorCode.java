package com.jeein.order.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_REQUEST_VALUE(HttpStatus.BAD_REQUEST, "C_001", "요청 데이터를 모두 올바르게 입력했는지 확인해 주세요."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C_002", "요청 데이터의 타입이 올바르지 않습니다."),
    DATABASE_CONSTRAINT_ERROR(HttpStatus.CONFLICT, "C_003", "잘못된 요청 값입니다."),
    INVALID_CONTENT_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "C_004", "지원되지 않는 미디어 타입입니다."),
    PAYLOAD_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "C_005", "요청 데이터의 크기가 너무 큽니다."),
    MULTIPART_NO_BOUNDARY(HttpStatus.BAD_REQUEST, "C_006", "요청 헤더의 content type을 확인해 주세요."),
    INVALID_MULTIPARTFILE(HttpStatus.BAD_REQUEST, "C_007", "요청 데이터의 이미지가 유효하지 않습니다"),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M_001", "회원 정보를 찾을 수 없습니다."),

    EVENT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "E_001", "공연 제목이 이미 존재합니다."),
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "E_002", "공연을 찾을 수 없습니다."),
    EVENT_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "E_003", "이미 삭제된 공연입니다."),
    EVENT_DATETIME_NOT_FOUND(HttpStatus.NOT_FOUND, "E_004", "공연 일정을 찾을 수 없습니다."),

    ORDER_ALREADY_EXISTS(HttpStatus.CONFLICT, "O_001", "이미 예약된 좌석입니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "O_002", "공연 정보를 찾을 수 없습니다."),
    ORDER_ALREADY_DELETED(HttpStatus.CONFLICT, "O_003", "이미 삭제된 주문입니다."),
    ORDER_ALREADY_CANCELED(HttpStatus.CONFLICT, "O_004", "이미 취소된 주문입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S_001", "서버에 오류가 발생했습니다."),
    UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S_002", "파일 업로드에 실패했습니다."),
    REQUEST_MAPPING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S_003", "요청 데이터 처리에 실패했습니다."),
    INVALID_ENTITY_VALUE(
            HttpStatus.INTERNAL_SERVER_ERROR, "S_004", "데이터베이스에 저장하려고 하는 데이터의 타입을 다시 확인해 주세요."),
    MEMBER_FEIGN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S_005", "회원 마이크로서비스 통신 과정에서 오류가 발생했습니다."),
    EVENT_FEIGN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S_006", "공연 마이크로서비스 통신 과정에서 오류가 발생했습니다.");
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    //    public int getStatus() {
    //        return status;
    //    }
    //    public String getMessage() {
    //        return this.message;
    //    }
    //
    //    public String getCode() {
    //        return code;
    //    }

}
