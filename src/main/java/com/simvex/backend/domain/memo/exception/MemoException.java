package com.simvex.backend.domain.memo.exception;

import com.simvex.backend.global.exception.CustomException;

public class MemoException extends CustomException {

    public MemoException(MemoErrorCode errorCode) {
        super(errorCode);
    }
}
