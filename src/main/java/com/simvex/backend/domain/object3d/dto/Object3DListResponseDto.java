package com.simvex.backend.domain.object3d.dto;

import com.simvex.backend.domain.object3d.entity.Object3D;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Object3DListResponseDto {

    private final Long id;
    private final String name;
    private final String nameEn;
    private final String description;
    private final String thumbnailUrl;
    private final String category;

    public static Object3DListResponseDto from(Object3D entity) {
        return Object3DListResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .nameEn(entity.getNameEn())
                .description(entity.getDescription())
                .thumbnailUrl(entity.getThumbnailUrl())
                .category(null) // TODO: category 필드 추가 필요
                .build();
    }
}
