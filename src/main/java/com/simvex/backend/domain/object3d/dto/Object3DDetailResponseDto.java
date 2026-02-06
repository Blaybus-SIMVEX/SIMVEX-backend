package com.simvex.backend.domain.object3d.dto;

import com.simvex.backend.domain.component.dto.ComponentDto;
import com.simvex.backend.domain.object3d.entity.Object3D;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Object3DDetailResponseDto {

    private final Long id;
    private final String name;
    private final String nameEn;
    private final String description;
    private final String thumbnailUrl;
    private final String category;
    private final String theory;
    private final List<ComponentDto> components;

    public static Object3DDetailResponseDto from(Object3D entity) {
        return Object3DDetailResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .nameEn(entity.getNameEn())
                .description(entity.getDescription())
                .thumbnailUrl(entity.getThumbnailUrl())
                .category(entity.getCategory())
                .theory(entity.getTheory())
                .components(entity.getComponents().stream()
                        .map(ComponentDto::from)
                        .toList())
                .build();
    }
}
