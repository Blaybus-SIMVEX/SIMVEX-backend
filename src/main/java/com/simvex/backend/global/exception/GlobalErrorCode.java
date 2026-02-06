package com.simvex.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    VALIDATION_ERROR(400, "VALIDATION_ERROR", "입력값이 올바르지 않습니다."),
    BAD_REQUEST(400, "BAD_REQUEST", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;
}
