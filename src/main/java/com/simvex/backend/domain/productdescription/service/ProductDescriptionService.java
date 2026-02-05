package com.simvex.backend.domain.productdescription.service;

import com.simvex.backend.domain.productdescription.dto.ProductDescriptionDto;
import com.simvex.backend.domain.productdescription.entity.ProductDescription;
import com.simvex.backend.domain.productdescription.repository.ProductDescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductDescriptionService {

    private final ProductDescriptionRepository productDescriptionRepository;

    public ProductDescriptionDto getByObject3DId(Long object3DId) {
        ProductDescription pd = productDescriptionRepository.findByObject3DId(object3DId)
                .orElseThrow(() -> new RuntimeException("ProductDescription not found for object3D id: " + object3DId));

        return ProductDescriptionDto.builder()
                .id(pd.getId())
                .structure(pd.getStructure())
                .theory(pd.getTheory())
                .purpose(pd.getPurpose())
                .build();
    }
}
