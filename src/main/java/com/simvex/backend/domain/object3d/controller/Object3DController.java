package com.simvex.backend.domain.object3d.controller;

import com.simvex.backend.domain.object3d.dto.Object3DDetailResponseDto;
import com.simvex.backend.domain.object3d.dto.Object3DListResponseDto;
import com.simvex.backend.domain.object3d.service.Object3DService;
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

@Tag(name = "Object3D", description = "3D 오브젝트 API")
@RestController
@RequestMapping("/api/objects")
@RequiredArgsConstructor
public class Object3DController {

    private final Object3DService object3DService;

    @Operation(summary = "오브젝트 목록 조회", description = "3D 오브젝트 목록을 조회합니다. 카테고리 필터링 가능합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<Object3DListResponseDto>>> getObjects(
            @Parameter(description = "카테고리 필터 (선택)")
            @RequestParam(required = false) String category,
            @Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "페이지 크기", example = "8")
            @RequestParam(defaultValue = "8") int size
    ) {
        PageResponse<Object3DListResponseDto> objects = object3DService.getObjects(category, PageRequest.of(page - 1, size));
        return ApiResponse.success(HttpStatus.OK, "오브젝트 목록 조회 성공", objects);
    }

    @Operation(summary = "오브젝트 상세 조회", description = "3D 오브젝트 상세 정보를 조회합니다. 부품 목록이 포함됩니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object3DDetailResponseDto>> getObjectDetail(
            @Parameter(description = "오브젝트 ID", required = true)
            @PathVariable Long id
    ) {
        Object3DDetailResponseDto objectDetail = object3DService.getObjectDetail(id);
        return ApiResponse.success(HttpStatus.OK, "오브젝트 상세 조회 성공", objectDetail);
    }
}
