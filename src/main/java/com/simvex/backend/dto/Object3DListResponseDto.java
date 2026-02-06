package com.simvex.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Object3DListResponseDto {
    private Long id;
    private String name;
    private String nameEn;
    private String description;
    private String thumbnailUrl;
}
