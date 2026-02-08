package com.simvex.backend.domain.object3d.service;

import com.simvex.backend.domain.object3d.dto.Object3DDetailResponseDto;
import com.simvex.backend.domain.object3d.dto.Object3DListResponseDto;
import com.simvex.backend.domain.object3d.entity.Category;
import com.simvex.backend.domain.object3d.entity.Object3D;
import com.simvex.backend.domain.object3d.exception.Object3DErrorCode;
import com.simvex.backend.domain.object3d.exception.Object3DException;
import com.simvex.backend.domain.object3d.repository.Object3DRepository;
import com.simvex.backend.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Object3DService {

    private final Object3DRepository object3DRepository;

    // 오브젝트 리스트 조회 (카테고리 필터링 선택적)
    public PageResponse<Object3DListResponseDto> getObjects(Category category, Pageable pageable) {
        return PageResponse.of(
                (category != null
                        ? object3DRepository.findByCategory(category.name(), pageable)
                        : object3DRepository.findAll(pageable))
                .map(Object3DListResponseDto::from)
        );
    }

    // 오브젝트 상세 조회 (부품 목록 + 완제품 설명 포함)
    public Object3DDetailResponseDto getObjectDetail(Long id) {
        Object3D object3D = object3DRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new Object3DException(Object3DErrorCode.OBJECT_NOT_FOUND));

        return Object3DDetailResponseDto.from(object3D);
    }
}
