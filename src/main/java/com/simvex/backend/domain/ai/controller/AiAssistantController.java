package com.simvex.backend.domain.ai.controller;

import com.simvex.backend.domain.ai.dto.AiChatRequestDto;
import com.simvex.backend.domain.ai.service.AiAssistantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Tag(name = "AI Assistant", description = "AI 챗봇 API")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiAssistantController {

    private final AiAssistantService aiAssistantService;

    @Operation(summary = "AI 채팅 (RAG 스트리밍)", description = "사용자의 질문에 대해 실시간 스트리밍으로 답변합니다.")
    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(
        @Valid @RequestBody AiChatRequestDto requestDto
    ) {
        return aiAssistantService.chatStream(requestDto);
    }
}
