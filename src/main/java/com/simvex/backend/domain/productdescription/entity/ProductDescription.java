package com.simvex.backend.domain.productdescription.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simvex.backend.domain.object3d.entity.Object3D;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_description")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_3d_id", nullable = false, unique = true)
    @JsonIgnore
    private Object3D object3D;

    @Column(columnDefinition = "TEXT")
    private String structure;

    @Column(columnDefinition = "TEXT")
    private String theory;

    @Column(columnDefinition = "TEXT")
    private String purpose;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private ProductDescription(Object3D object3D, String structure, String theory, String purpose) {
        this.object3D = object3D;
        this.structure = structure;
        this.theory = theory;
        this.purpose = purpose;
    }
}
