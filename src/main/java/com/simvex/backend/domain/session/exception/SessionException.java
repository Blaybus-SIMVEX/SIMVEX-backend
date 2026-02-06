package com.simvex.backend.domain.session.exception;

import com.simvex.backend.global.exception.CustomException;

public class SessionException extends CustomException {

    public SessionException(SessionErrorCode errorCode) {
        super(errorCode);
    }
}
