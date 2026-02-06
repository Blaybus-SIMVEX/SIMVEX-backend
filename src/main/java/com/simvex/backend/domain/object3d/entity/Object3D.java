package com.simvex.backend.domain.object3d.entity;

import com.simvex.backend.domain.component.entity.Component;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "object_3d")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Object3D {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "name_en", length = 100)
    private String nameEn;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(length = 50)
    private String category;

    @Column(columnDefinition = "TEXT")
    private String theory;

    @OneToMany(mappedBy = "object3D", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Component> components = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private Object3D(String name, String nameEn, String description, String thumbnailUrl, String category, String theory) {
        this.name = name;
        this.nameEn = nameEn;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.category = category;
        this.theory = theory;
    }
}
