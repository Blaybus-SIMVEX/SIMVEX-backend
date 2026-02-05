package com.simvex.backend.domain.object3d.dto;

import com.simvex.backend.domain.component.dto.ComponentDto;
import com.simvex.backend.domain.productdescription.dto.ProductDescriptionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Object3DDetailResponseDto {
    private Long id;
    private String name;
    private String nameEn;
    private String description;
    private String thumbnailUrl;
    private String modelFileUrl;
    private String category;
    private ProductDescriptionDto productDescription;
    private List<ComponentDto> components;
}
