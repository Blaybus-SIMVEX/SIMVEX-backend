package com.simvex.backend.domain.component.exception;

import com.simvex.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ComponentErrorCode implements ErrorCode {

    COMPONENT_NOT_FOUND(404, "COMP_001", "부품을 찾을 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}
