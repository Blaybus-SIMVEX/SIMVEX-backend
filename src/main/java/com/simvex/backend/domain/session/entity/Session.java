package com.simvex.backend.domain.session.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "session")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private String sessionToken;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime lastAccessedAt;

    private Session(String sessionToken) {
        this.sessionToken = sessionToken;
        this.lastAccessedAt = LocalDateTime.now();
    }

    // 정적 팩토리 메서드
    public static Session create() {
        return new Session(UUID.randomUUID().toString());
    }

    // 마지막 접근 시간 갱신
    public void updateLastAccessedAt() {
        this.lastAccessedAt = LocalDateTime.now();
    }
}
