package com.simvex.backend.domain.object3d.exception;

import com.simvex.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Object3DErrorCode implements ErrorCode {

    OBJECT_NOT_FOUND(404, "OBJ_001", "3D 오브젝트를 찾을 수 없습니다."),
    INVALID_CATEGORY(400, "OBJ_002", "유효하지 않은 카테고리입니다.");

    private final int status;
    private final String code;
    private final String message;
}
