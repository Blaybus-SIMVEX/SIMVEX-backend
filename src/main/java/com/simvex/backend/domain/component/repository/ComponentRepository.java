package com.simvex.backend.domain.component.repository;

import com.simvex.backend.domain.component.entity.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComponentRepository extends JpaRepository<Component, Long> {

    // 특정 오브젝트의 부품 전체 조회 (페이지네이션)
    Page<Component> findByObject3DId(Long object3DId, Pageable pageable);

    // 특정 오브젝트의 특정 부품 조회
    Optional<Component> findByIdAndObject3DId(Long id, Long object3DId);
}
