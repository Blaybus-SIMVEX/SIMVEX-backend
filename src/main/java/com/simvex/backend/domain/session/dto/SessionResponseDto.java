package com.simvex.backend.domain.session.dto;

import com.simvex.backend.domain.session.entity.Session;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SessionResponseDto {

    private final String sessionToken;

    public static SessionResponseDto from(Session session) {
        return new SessionResponseDto(session.getSessionToken());
    }
}
