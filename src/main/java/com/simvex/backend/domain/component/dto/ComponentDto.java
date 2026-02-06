package com.simvex.backend.domain.component.dto;

import com.simvex.backend.domain.component.entity.Component;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "부품 응답")
@Getter
@Builder
public class ComponentDto {

    @Schema(description = "부품 ID", example = "1")
    private final Long id;

    @Schema(description = "부품 이름 (한글)", example = "암 기어")
    private final String name;

    @Schema(description = "부품 이름 (영문)", example = "Arm gear")
    private final String nameEn;

    @Schema(description = "3D 모델 파일 URL", example = "https://kr.object.ncloudstorage.com/simvex-bucket/drone/Arm%20gear.glb")
    private final String modelFileUrl;

    @Schema(description = "재질", example = "강화 플라스틱(PA/나일론)")
    private final String material;

    @Schema(description = "역할", example = "모터의 회전력을 프로펠러 축으로 전달합니다")
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
