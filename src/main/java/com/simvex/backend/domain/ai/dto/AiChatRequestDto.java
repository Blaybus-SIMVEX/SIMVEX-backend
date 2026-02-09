package com.simvex.backend.domain.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AiChatRequestDto {

    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Object3D ID is required")
    private Long object3DId;

    @NotBlank(message = "Question is required")
    private String question;
}
