package com.simvex.backend.domain.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AiChatRequestDto {

    @NotNull(message = "Object3D ID is required")
    private Long object3DId;

    @NotBlank(message = "Question is required")
    private String question;

    private List<ChatMessage> conversationHistory;

    @Getter
    @NoArgsConstructor
    public static class ChatMessage {
        private String role;
        private String content;

        public ChatMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
