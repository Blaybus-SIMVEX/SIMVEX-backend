package com.simvex.backend.domain.session.exception;

import com.simvex.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SessionErrorCode implements ErrorCode {

    SESSION_NOT_FOUND(404, "SESSION_001", "세션을 찾을 수 없습니다."),
    SESSION_EXPIRED(401, "SESSION_002", "세션이 만료되었습니다.");

    private final int status;
    private final String code;
    private final String message;
}
