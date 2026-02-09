package com.simvex.backend.domain.ai.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_history", indexes = {
    @Index(name = "idx_session_object", columnList = "sessionToken, object3dId")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sessionToken;

    @Column(nullable = false)
    private Long object3dId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MessageRole role;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum MessageRole {
        USER, ASSISTANT
    }

    public static ChatHistory of(String sessionToken, Long object3dId, MessageRole role, String content) {
        ChatHistory history = new ChatHistory();
        history.sessionToken = sessionToken;
        history.object3dId = object3dId;
        history.role = role;
        history.content = content;
        return history;
    }
}