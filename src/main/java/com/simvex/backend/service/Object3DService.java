package com.simvex.backend.service;

import com.simvex.backend.dto.ComponentDto;
import com.simvex.backend.dto.Object3DDetailResponseDto;
import com.simvex.backend.dto.Object3DListResponseDto;
import com.simvex.backend.dto.ProductDescriptionDto;
import com.simvex.backend.entity.Object3D;
import com.simvex.backend.entity.ProductDescription;
import com.simvex.backend.repository.Object3DRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Object3DService {

    private final Object3DRepository object3DRepository;

    public List<Object3DListResponseDto> getAllObjects() {
        return object3DRepository.findAll().stream()
                .map(this::convertToListDto)
                .collect(Collectors.toList());
    }

    public Object3DDetailResponseDto getObjectDetail(Long id) {
        Object3D object3D = object3DRepository.findByIdWithComponents(id)
                .orElseThrow(() -> new RuntimeException("Object3D not found with id: " + id));
        
        return convertToDetailDto(object3D);
    }

    private Object3DListResponseDto convertToListDto(Object3D object3D) {
        return Object3DListResponseDto.builder()
                .id(object3D.getId())
                .name(object3D.getName())
                .nameEn(object3D.getNameEn())
                .description(object3D.getDescription())
                .thumbnailUrl(object3D.getThumbnailUrl())
                .build();
    }

    private Object3DDetailResponseDto convertToDetailDto(Object3D object3D) {
        ProductDescriptionDto productDescDto = null;
        if (object3D.getProductDescription() != null) {
            ProductDescription pd = object3D.getProductDescription();
            productDescDto = ProductDescriptionDto.builder()
                    .id(pd.getId())
                    .structure(pd.getStructure())
                    .theory(pd.getTheory())
                    .purpose(pd.getPurpose())
                    .build();
        }

        List<ComponentDto> componentDtos = object3D.getComponents().stream()
                .map(component -> ComponentDto.builder()
                        .id(component.getId())
                        .name(component.getName())
                        .nameEn(component.getNameEn())
                        .material(component.getMaterial())
                        .modelFileUrl(component.getModelFileUrl())
                        .role(component.getRole())
                        .build())
                .collect(Collectors.toList());

        return Object3DDetailResponseDto.builder()
                .id(object3D.getId())
                .name(object3D.getName())
                .nameEn(object3D.getNameEn())
                .description(object3D.getDescription())
                .thumbnailUrl(object3D.getThumbnailUrl())
                .productDescription(productDescDto)
                .components(componentDtos)
                .build();
    }
}
