package com.simvex.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDescriptionDto {
    private Long id;
    private String structure;
    private String theory;
    private String purpose;
}
