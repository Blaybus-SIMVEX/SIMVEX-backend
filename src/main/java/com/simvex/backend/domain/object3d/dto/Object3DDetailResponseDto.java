package com.simvex.backend.domain.object3d.dto;

import com.simvex.backend.domain.component.dto.ComponentDto;
import com.simvex.backend.domain.object3d.entity.Category;
import com.simvex.backend.domain.object3d.entity.Object3D;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "오브젝트 상세 응답")
@Getter
@Builder
public class Object3DDetailResponseDto {

    @Schema(description = "오브젝트 ID", example = "1")
    private final Long id;

    @Schema(description = "오브젝트 이름 (한글)", example = "드론")
    private final String name;

    @Schema(description = "오브젝트 이름 (영문)", example = "Drone")
    private final String nameEn;

    @Schema(description = "오브젝트 설명", example = "쿼드콥터 드론의 프레임 및 동력 전달 구조")
    private final String description;

    @Schema(description = "썸네일 이미지 URL", example = "https://kr.object.ncloudstorage.com/simvex-bucket/drone/%EC%A1%B0%EB%A6%BD%EB%8F%841.png")
    private final String thumbnailUrl;

    @Schema(description = "카테고리 목록", example = "[\"기계공학\", \"항공\"]")
    private final List<String> categories;

    @Schema(description = "관련 이론", example = "드론은 4개의 프로펠러(로터)를 이용해 양력을 발생시켜 비행합니다.")
    private final String theory;

    @Schema(description = "부품 목록")
    private final List<ComponentDto> components;

    public static Object3DDetailResponseDto from(Object3D entity) {
        return Object3DDetailResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .nameEn(entity.getNameEn())
                .description(entity.getDescription())
                .thumbnailUrl(entity.getThumbnailUrl())
                .categories(parseCategories(entity.getCategory()))
                .theory(entity.getTheory())
                .components(entity.getComponents().stream()
                        .map(ComponentDto::from)
                        .toList())
                .build();
    }

    private static List<String> parseCategories(Category category) {
        if (category == null) {
            return List.of();
        }
        return List.of(category.getParent().getKoreanName(), category.getKoreanName());
    }
}
