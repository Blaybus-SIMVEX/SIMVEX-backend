package com.simvex.backend.domain.productdescription.exception;

import com.simvex.backend.global.exception.CustomException;

public class ProductDescriptionException extends CustomException {

    public ProductDescriptionException(ProductDescriptionErrorCode errorCode) {
        super(errorCode);
    }
}
