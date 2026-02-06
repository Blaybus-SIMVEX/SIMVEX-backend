package com.simvex.backend.domain.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AiChatRequestDto {

    @NotNull(message = "Object3D ID is required")
    private final Long object3DId;

    @NotBlank(message = "Question is required")
    private final String question;

    private final List<ChatMessage> conversationHistory;

    @Getter
    @RequiredArgsConstructor
    public static class ChatMessage {
        private final String role;
        private final String content;

        public static ChatMessage of(String role, String content) {
            return new ChatMessage(role, content);
        }
    }
}
