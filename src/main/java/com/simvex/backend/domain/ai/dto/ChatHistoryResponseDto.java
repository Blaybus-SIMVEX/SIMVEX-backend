package com.simvex.backend.domain.ai.dto;

import com.simvex.backend.domain.ai.entity.ChatHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatHistoryResponseDto {
    @Schema(description = "메시지 역할", example = "USER")
    private String role;

    @Schema(description = "메시지 내용", example = "드론의 프레임 재질은 무엇인가요?")
    private String content;

    @Schema(description = "생성 시간", example = "2026-02-09T10:30:00")
    private LocalDateTime createdAt;

    public static ChatHistoryResponseDto from(ChatHistory entity) {
        return ChatHistoryResponseDto.builder()
                .role(entity.getRole().name())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
