package com.simvex.backend.domain.component.service;

import com.simvex.backend.domain.component.dto.ComponentDto;
import com.simvex.backend.domain.component.entity.Component;
import com.simvex.backend.domain.component.repository.ComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComponentService {

    private final ComponentRepository componentRepository;

    public ComponentDto getComponentDetail(Long id) {
        Component component = componentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Component not found with id: " + id));

        return ComponentDto.builder()
                .id(component.getId())
                .name(component.getName())
                .nameEn(component.getNameEn())
                .modelFileUrl(component.getModelFileUrl())
                .material(component.getMaterial())
                .role(component.getRole())
                .build();
    }
}
