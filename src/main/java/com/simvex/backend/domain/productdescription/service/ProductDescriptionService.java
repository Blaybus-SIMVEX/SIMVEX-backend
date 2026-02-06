package com.simvex.backend.domain.productdescription.service;

import com.simvex.backend.domain.productdescription.dto.ProductDescriptionDto;
import com.simvex.backend.domain.productdescription.entity.ProductDescription;
import com.simvex.backend.domain.productdescription.exception.ProductDescriptionErrorCode;
import com.simvex.backend.domain.productdescription.exception.ProductDescriptionException;
import com.simvex.backend.domain.productdescription.repository.ProductDescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductDescriptionService {

    private final ProductDescriptionRepository productDescriptionRepository;

    // 특정 오브젝트의 제품 설명 조회
    public ProductDescriptionDto getByObject3DId(Long object3DId) {
        ProductDescription pd = productDescriptionRepository.findByObject3DId(object3DId)
                .orElseThrow(() -> new ProductDescriptionException(ProductDescriptionErrorCode.PRODUCT_DESCRIPTION_NOT_FOUND));

        return ProductDescriptionDto.from(pd);
    }
}
