package com.simvex.backend.domain.session.dto;

import com.simvex.backend.domain.session.entity.Session;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(description = "세션 응답")
@Getter
@RequiredArgsConstructor
public class SessionResponseDto {

    @Schema(description = "세션 토큰 (UUID)", example = "550e8400-e29b-41d4-a716-446655440000")
    private final String sessionToken;

    public static SessionResponseDto from(Session session) {
        return new SessionResponseDto(session.getSessionToken());
    }
}
