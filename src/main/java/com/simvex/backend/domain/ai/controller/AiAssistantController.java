package com.simvex.backend.domain.ai.controller;

import com.simvex.backend.domain.ai.dto.AiChatRequestDto;
import com.simvex.backend.domain.ai.dto.ChatHistoryResponseDto;
import com.simvex.backend.domain.ai.service.AiAssistantService;
import com.simvex.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@Tag(name = "AI Assistant", description = "AI 챗봇 API")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiAssistantController {

    private final AiAssistantService aiAssistantService;

    @Operation(summary = "AI 채팅 (RAG 스트리밍)", description = "사용자의 질문에 대해 실시간 스트리밍으로 답변합니다.")
    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(
        @Valid @RequestBody AiChatRequestDto requestDto,
        @RequestHeader("sessionToken") String sessionToken
    ) {
        return aiAssistantService.chatStream(sessionToken, requestDto);
    }

    @Operation(summary = "대화 내역 조회")
    @GetMapping("/chat/history")
    public ResponseEntity<ApiResponse<List<ChatHistoryResponseDto>>> getChatHistory(
            @Parameter(description = "오브젝트 ID", example = "1")
            @RequestParam Long object3dId,
            @Parameter(description = "세션 토큰", required = true)
            @RequestHeader("sessionToken") String sessionToken
    ) {
        List<ChatHistoryResponseDto> history = aiAssistantService.getChatHistory(sessionToken, object3dId);
        return ApiResponse.success(HttpStatus.OK, "대화 내역 조회 성공", history);
    }
}
