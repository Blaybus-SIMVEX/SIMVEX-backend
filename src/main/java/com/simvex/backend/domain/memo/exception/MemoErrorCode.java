package com.simvex.backend.domain.memo.exception;

import com.simvex.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemoErrorCode implements ErrorCode {

    MEMO_NOT_FOUND(404, "MEMO_001", "메모를 찾을 수 없습니다."),
    MEMO_ACCESS_DENIED(403, "MEMO_002", "메모에 대한 접근 권한이 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}
