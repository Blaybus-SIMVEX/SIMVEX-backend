package com.simvex.backend.domain.productdescription.dto;

import com.simvex.backend.domain.productdescription.entity.ProductDescription;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDescriptionDto {

    private final Long id;
    private final String structure;
    private final String theory;
    private final String purpose;

    public static ProductDescriptionDto from(ProductDescription entity) {
        return ProductDescriptionDto.builder()
                .id(entity.getId())
                .structure(entity.getStructure())
                .theory(entity.getTheory())
                .purpose(entity.getPurpose())
                .build();
    }
}
