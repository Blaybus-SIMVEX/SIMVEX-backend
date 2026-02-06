package com.simvex.backend.domain.component.service;

import com.simvex.backend.domain.component.dto.ComponentDto;
import com.simvex.backend.domain.component.entity.Component;
import com.simvex.backend.domain.component.exception.ComponentErrorCode;
import com.simvex.backend.domain.component.exception.ComponentException;
import com.simvex.backend.domain.component.repository.ComponentRepository;
import com.simvex.backend.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComponentService {

    private final ComponentRepository componentRepository;

    // 특정 오브젝트의 부품 전체 조회 (페이지네이션)
    public PageResponse<ComponentDto> getComponentsByObjectId(Long objectId, Pageable pageable) {
        return PageResponse.of(
                componentRepository.findByObject3DId(objectId, pageable)
                        .map(ComponentDto::from)
        );
    }

    // 특정 오브젝트의 특정 부품 상세 조회
    public ComponentDto getComponentDetail(Long objectId, Long componentId) {
        Component component = componentRepository.findByIdAndObject3DId(componentId, objectId)
                .orElseThrow(() -> new ComponentException(ComponentErrorCode.COMPONENT_NOT_FOUND));

        return ComponentDto.from(component);
    }
}
