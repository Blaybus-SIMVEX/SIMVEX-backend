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

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Object3DService {

    private final Object3DRepository object3DRepository;

    // 오브젝트 리스트 조회 (카테고리 필터링 선택적)
    public PageResponse<Object3DListResponseDto> getObjects(Category category, Pageable pageable) {
        if (category == null) {
            return PageResponse.of(object3DRepository.findAll(pageable).map(Object3DListResponseDto::from));
        }

        // 상위 카테고리면 하위 카테고리들로 검색
        if (category.getParent() == null) {
            List<String> childNames = Arrays.stream(Category.values())
                    .filter(c -> c.getParent() == category)
                    .map(Enum::name)
                    .toList();
            return PageResponse.of(
                    object3DRepository.findByCategoryIn(childNames, pageable)
                            .map(Object3DListResponseDto::from));
        }

        return PageResponse.of(
                object3DRepository.findByCategory(category.name(), pageable)
                        .map(Object3DListResponseDto::from));
    }

    // 오브젝트 상세 조회 (부품 목록 + 완제품 설명 포함)
    public Object3DDetailResponseDto getObjectDetail(Long id) {
        Object3D object3D = object3DRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new Object3DException(Object3DErrorCode.OBJECT_NOT_FOUND));

        return Object3DDetailResponseDto.from(object3D);
    }
}
