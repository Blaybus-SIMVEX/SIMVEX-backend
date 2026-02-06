package com.simvex.backend.domain.component.controller;

import com.simvex.backend.domain.component.dto.ComponentDto;
import com.simvex.backend.domain.component.service.ComponentService;
import com.simvex.backend.global.common.ApiResponse;
import com.simvex.backend.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/objects/{objectId}/components")
@RequiredArgsConstructor
public class ComponentController {

    private final ComponentService componentService;

    // 특정 오브젝트의 부품 전체 조회 (페이지네이션)
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ComponentDto>>> getComponents(
            @PathVariable Long objectId,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        PageResponse<ComponentDto> components = componentService.getComponentsByObjectId(objectId, pageable);
        return ApiResponse.success(HttpStatus.OK, "부품 목록 조회 성공", components);
    }

    // 특정 오브젝트의 특정 부품 상세 조회
    @GetMapping("/{componentId}")
    public ResponseEntity<ApiResponse<ComponentDto>> getComponentDetail(
            @PathVariable Long objectId,
            @PathVariable Long componentId
    ) {
        ComponentDto component = componentService.getComponentDetail(objectId, componentId);
        return ApiResponse.success(HttpStatus.OK, "부품 상세 조회 성공", component);
    }
}
