package com.simvex.backend.controller;

import com.simvex.backend.dto.AiChatRequestDto;
import com.simvex.backend.dto.AiChatResponseDto;
import com.simvex.backend.service.AiAssistantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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
