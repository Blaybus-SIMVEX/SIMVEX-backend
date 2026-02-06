package com.simvex.backend.domain.session.controller;

import com.simvex.backend.domain.session.dto.SessionResponseDto;
import com.simvex.backend.domain.session.service.SessionService;
import com.simvex.backend.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    // 새 세션 발급
    @PostMapping
    public ResponseEntity<ApiResponse<SessionResponseDto>> createSession() {
        SessionResponseDto response = sessionService.createSession();
        return ApiResponse.success(HttpStatus.CREATED, "세션이 생성되었습니다.", response);
    }

    // 세션 유효성 확인
    @GetMapping("/{sessionToken}")
    public ResponseEntity<ApiResponse<SessionResponseDto>> validateSession(
            @PathVariable String sessionToken
    ) {
        SessionResponseDto response = sessionService.validateSession(sessionToken);
        return ApiResponse.success(HttpStatus.OK, "유효한 세션입니다.", response);
    }
}
