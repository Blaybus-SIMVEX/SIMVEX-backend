package com.simvex.backend.domain.productdescription.repository;

import com.simvex.backend.domain.productdescription.entity.ProductDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Long> {
    Optional<ProductDescription> findByObject3DId(Long object3DId);
}
