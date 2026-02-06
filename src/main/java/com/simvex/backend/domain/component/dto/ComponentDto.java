package com.simvex.backend.domain.component.dto;

import com.simvex.backend.domain.component.entity.Component;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComponentDto {

    private final Long id;
    private final String name;
    private final String nameEn;
    private final String modelFileUrl;
    private final String material;
    private final String role;

    public static ComponentDto from(Component entity) {
        return ComponentDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .nameEn(entity.getNameEn())
                .modelFileUrl(entity.getModelFileUrl())
                .material(entity.getMaterial())
                .role(entity.getRole())
                .build();
    }
}
