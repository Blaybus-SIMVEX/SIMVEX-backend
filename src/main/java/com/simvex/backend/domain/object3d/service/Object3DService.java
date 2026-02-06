package com.simvex.backend.domain.object3d.service;

import com.simvex.backend.domain.object3d.dto.Object3DDetailResponseDto;
import com.simvex.backend.domain.object3d.dto.Object3DListResponseDto;
import com.simvex.backend.domain.object3d.entity.Object3D;
import com.simvex.backend.domain.object3d.repository.Object3DRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Object3DService {

    private final Object3DRepository object3DRepository;

    public List<Object3DListResponseDto> getAllObjects() {
        return object3DRepository.findAll().stream()
                .map(Object3DListResponseDto::from)
                .toList();
    }

    public Object3DDetailResponseDto getObjectDetail(Long id) {
        Object3D object3D = object3DRepository.findByIdWithComponents(id)
                .orElseThrow(() -> new RuntimeException("Object3D not found with id: " + id));

        return Object3DDetailResponseDto.from(object3D);
    }
}
