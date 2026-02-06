package com.simvex.backend.domain.productdescription.exception;

import com.simvex.backend.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductDescriptionErrorCode implements ErrorCode {

    PRODUCT_DESCRIPTION_NOT_FOUND(404, "PD_001", "제품 설명을 찾을 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}
