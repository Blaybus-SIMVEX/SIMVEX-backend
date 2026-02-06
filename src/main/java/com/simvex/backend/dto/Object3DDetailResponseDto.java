package com.simvex.backend.dto;

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
    private ProductDescriptionDto productDescription;
    private List<ComponentDto> components;
}
