package com.simvex.backend.domain.object3d.repository;

import com.simvex.backend.domain.object3d.entity.Object3D;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Object3DRepository extends JpaRepository<Object3D, Long> {

    // 전체 조회 (페이지네이션)
    Page<Object3D> findAll(Pageable pageable);

    // 카테고리별 필터링 (페이지네이션)
    Page<Object3D> findByCategory(String category, Pageable pageable);

    // 상세 조회 (컴포넌트 함께 조회)
    @Query("SELECT o FROM Object3D o " +
           "LEFT JOIN FETCH o.components " +
           "WHERE o.id = :id")
    Optional<Object3D> findByIdWithDetails(@Param("id") Long id);
}
