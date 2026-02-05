package com.simvex.backend.domain.ai.controller;

import com.simvex.backend.domain.ai.dto.AiChatRequestDto;
import com.simvex.backend.domain.ai.dto.AiChatResponseDto;
import com.simvex.backend.domain.ai.service.AiAssistantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiAssistantController {

    private final AiAssistantService aiAssistantService;

    @PostMapping("/chat")
    public ResponseEntity<AiChatResponseDto> chat(
        @Valid @RequestBody AiChatRequestDto requestDto
    ) {
        AiChatResponseDto response = aiAssistantService.chat(requestDto);
        return ResponseEntity.ok(response);
    }
}
