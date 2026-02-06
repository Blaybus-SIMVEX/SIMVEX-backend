package com.simvex.backend.domain.component.exception;

import com.simvex.backend.global.exception.CustomException;

public class ComponentException extends CustomException {

    public ComponentException(ComponentErrorCode errorCode) {
        super(errorCode);
    }
}
