package com.simvex.backend.domain.object3d.repository;

import com.simvex.backend.domain.object3d.entity.Category;
import com.simvex.backend.domain.object3d.entity.Object3D;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Object3DRepository extends JpaRepository<Object3D, Long> {

    // 전체 조회 (페이지네이션)
    Page<Object3D> findAll(Pageable pageable);

    // enum 타입으로 정확히 비교
    Page<Object3D> findByCategory(Category category, Pageable pageable);

    // 상위 카테고리 검색용 - 하위 카테고리 목록으로 IN 검색
    Page<Object3D> findByCategoryIn(List<Category> categories, Pageable pageable);

    // 상세 조회 (컴포넌트 함께 조회)
    @Query("SELECT o FROM Object3D o " +
           "LEFT JOIN FETCH o.components " +
           "WHERE o.id = :id")
    Optional<Object3D> findByIdWithDetails(@Param("id") Long id);
}
