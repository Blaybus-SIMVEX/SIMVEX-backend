package com.simvex.backend.domain.object3d.repository;

import com.simvex.backend.domain.object3d.entity.Object3D;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Object3DRepository extends JpaRepository<Object3D, Long> {

    @Query("SELECT o FROM Object3D o LEFT JOIN FETCH o.components WHERE o.id = :id")
    Optional<Object3D> findByIdWithComponents(@Param("id") Long id);

}
