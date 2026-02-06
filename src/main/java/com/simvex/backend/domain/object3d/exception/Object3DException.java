package com.simvex.backend.domain.object3d.exception;

import com.simvex.backend.global.exception.CustomException;

public class Object3DException extends CustomException {

    public Object3DException(Object3DErrorCode errorCode) {
        super(errorCode);
    }
}
