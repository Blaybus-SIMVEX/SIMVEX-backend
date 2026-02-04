package com.simvex.backend.service;

import com.simvex.backend.dto.ComponentDto;
import com.simvex.backend.entity.Component;
import com.simvex.backend.repository.ComponentRepository;
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
                .material(component.getMaterial())
                .role(component.getRole())
                .build();
    }
}
