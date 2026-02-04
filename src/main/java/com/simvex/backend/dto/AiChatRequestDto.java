package com.simvex.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiChatRequestDto {
    
    @NotNull(message = "Object3D ID is required")
    private Long object3DId;
    
    @NotBlank(message = "Question is required")
    private String question;
    
    private List<ChatMessage> conversationHistory;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChatMessage {
        private String role;
        private String content;
    }
}
