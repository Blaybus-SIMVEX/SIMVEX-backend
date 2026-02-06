package com.simvex.backend.domain.component.entity;

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
@Table(name = "component")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_3d_id", nullable = false)
    @JsonIgnore
    private Object3D object3D;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "name_en", length = 100)
    private String nameEn;

    @Column(name = "model_file_url", length = 500)
    private String modelFileUrl;

    @Column(length = 100)
    private String material;

    @Column(columnDefinition = "TEXT")
    private String role;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private Component(Object3D object3D, String name, String nameEn, String modelFileUrl, String material, String role) {
        this.object3D = object3D;
        this.name = name;
        this.nameEn = nameEn;
        this.modelFileUrl = modelFileUrl;
        this.material = material;
        this.role = role;
    }
}
