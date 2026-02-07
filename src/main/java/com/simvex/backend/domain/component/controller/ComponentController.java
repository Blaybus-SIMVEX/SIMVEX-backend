package com.simvex.backend.domain.component.controller;

import com.simvex.backend.domain.component.dto.ComponentDto;
import com.simvex.backend.domain.component.service.ComponentService;
import com.simvex.backend.global.common.ApiResponse;
import com.simvex.backend.global.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Component", description = "부품 API")
@RestController
@RequestMapping("/api/objects/{objectId}/components")
@RequiredArgsConstructor
public class ComponentController {

    private final ComponentService componentService;

    @Operation(summary = "부품 목록 조회", description = "특정 오브젝트의 부품 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ComponentDto>>> getComponents(
            @Parameter(description = "오브젝트 ID", required = true)
            @PathVariable Long objectId,
            @Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "페이지 크기", example = "8")
            @RequestParam(defaultValue = "8") int size
    ) {
        PageResponse<ComponentDto> components = componentService.getComponentsByObjectId(objectId, PageRequest.of(page - 1, size));
        return ApiResponse.success(HttpStatus.OK, "부품 목록 조회 성공", components);
    }

    @Operation(summary = "부품 상세 조회", description = "특정 오브젝트의 특정 부품 상세 정보를 조회합니다.")
    @GetMapping("/{componentId}")
    public ResponseEntity<ApiResponse<ComponentDto>> getComponentDetail(
            @Parameter(description = "오브젝트 ID", required = true)
            @PathVariable Long objectId,
            @Parameter(description = "부품 ID", required = true)
            @PathVariable Long componentId
    ) {
        ComponentDto component = componentService.getComponentDetail(objectId, componentId);
        return ApiResponse.success(HttpStatus.OK, "부품 상세 조회 성공", component);
    }
}
