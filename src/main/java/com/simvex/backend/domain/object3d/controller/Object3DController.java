package com.simvex.backend.domain.object3d.controller;

import com.simvex.backend.domain.object3d.dto.Object3DDetailResponseDto;
import com.simvex.backend.domain.object3d.dto.Object3DListResponseDto;
import com.simvex.backend.domain.object3d.service.Object3DService;
import com.simvex.backend.global.common.ApiResponse;
import com.simvex.backend.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/objects")
@RequiredArgsConstructor
public class Object3DController {

    private final Object3DService object3DService;

    // 오브젝트 리스트 조회 (페이지네이션, 카테고리 필터링 선택적)
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<Object3DListResponseDto>>> getObjects(
            @RequestParam(required = false) String category,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        PageResponse<Object3DListResponseDto> objects = object3DService.getObjects(category, pageable);
        return ApiResponse.success(HttpStatus.OK, "오브젝트 목록 조회 성공", objects);
    }

    // 오브젝트 상세 조회 (부품 목록 + 완제품 설명 포함)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object3DDetailResponseDto>> getObjectDetail(
            @PathVariable Long id
    ) {
        Object3DDetailResponseDto objectDetail = object3DService.getObjectDetail(id);
        return ApiResponse.success(HttpStatus.OK, "오브젝트 상세 조회 성공", objectDetail);
    }
}
