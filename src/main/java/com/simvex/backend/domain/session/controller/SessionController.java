package com.simvex.backend.domain.session.controller;

import com.simvex.backend.domain.session.dto.SessionResponseDto;
import com.simvex.backend.domain.session.service.SessionService;
import com.simvex.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Session", description = "세션 관리 API")
@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @Operation(summary = "세션 발급", description = "새로운 세션을 생성하고 sessionToken을 반환합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<SessionResponseDto>> createSession() {
        SessionResponseDto response = sessionService.createSession();
        return ApiResponse.success(HttpStatus.CREATED, "세션이 생성되었습니다.", response);
    }

    @Operation(summary = "세션 유효성 확인", description = "sessionToken의 유효성을 확인합니다.")
    @GetMapping("/{sessionToken}")
    public ResponseEntity<ApiResponse<SessionResponseDto>> validateSession(
            @Parameter(description = "세션 토큰", required = true)
            @PathVariable String sessionToken
    ) {
        SessionResponseDto response = sessionService.validateSession(sessionToken);
        return ApiResponse.success(HttpStatus.OK, "유효한 세션입니다.", response);
    }
}
