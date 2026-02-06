package com.simvex.backend.domain.ai.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AiChatResponseDto {

    private final String answer;
    private final String role;

    public static AiChatResponseDto of(String answer, String role) {
        return new AiChatResponseDto(answer, role);
    }
}
