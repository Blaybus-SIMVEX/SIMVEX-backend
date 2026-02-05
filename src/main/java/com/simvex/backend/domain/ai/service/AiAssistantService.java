package com.simvex.backend.domain.ai.service;

import com.simvex.backend.domain.ai.dto.AiChatRequestDto;
import com.simvex.backend.domain.ai.dto.AiChatResponseDto;
import com.simvex.backend.domain.ai.dto.AiResponse;
import com.simvex.backend.domain.object3d.entity.Object3D;
import com.simvex.backend.domain.object3d.repository.Object3DRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AiAssistantService {

    private final Object3DRepository object3DRepository;

    @Value("${ai.api.url}")
    private String aiApiUrl;

    @Value("${ai.api.key}")
    private String aiApiKey;

    @Value("${ai.api.model}")
    private String aiModel;

    public static final double TEMPERATURE = 0.5;
    public static final int TOP_K = 0;
    public static final double TOP_P = 0.8;
    public static final double REPEAT_PENALTY = 1.1;
    public static final int MAX_TOKENS = 256;

    public AiChatResponseDto chat(AiChatRequestDto requestDto) {
        Object3D object3D = object3DRepository.findByIdWithComponents(requestDto.getObject3DId())
                .orElseThrow(() -> new RuntimeException("Object3D not found with id: " + requestDto.getObject3DId()));

        String systemPrompt = buildSystemPrompt(object3D);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", systemPrompt));

        if (requestDto.getConversationHistory() != null && !requestDto.getConversationHistory().isEmpty()) {
            for (AiChatRequestDto.ChatMessage msg : requestDto.getConversationHistory()) {
                messages.add(Map.of("role", msg.getRole(), "content", msg.getContent()));
            }
        }

        messages.add(Map.of("role", "user", "content", requestDto.getQuestion()));

        Map<String, Object> body = new HashMap<>();
        body.put("messages", messages);
        body.put("temperature", TEMPERATURE);
        body.put("topK", TOP_K);
        body.put("topP", TOP_P);
        body.put("repeatPenalty", REPEAT_PENALTY);
        body.put("maxTokens", MAX_TOKENS);

        try {
            RestClient restClient = RestClient.create();
            AiResponse response = restClient.post()
                .uri(aiApiUrl + "/" + aiModel)
                .header("Authorization", "Bearer " + aiApiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .retrieve()
                .body(AiResponse.class);

            String answer = Objects.requireNonNull(response).result().message().content();

            return AiChatResponseDto.builder()
                    .answer(answer)
                    .role("assistant")
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("AI API call failed: " + e.getMessage(), e);
        }
    }

    private String buildSystemPrompt(Object3D object3D) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("당신은 공학 교육을 돕는 AI 어시스턴트입니다. ");
        prompt.append("현재 학습자는 '").append(object3D.getName()).append("'에 대해 학습하고 있습니다.\n\n");

        if (object3D.getDescription() != null) {
            prompt.append("오브젝트 설명: ").append(object3D.getDescription()).append("\n\n");
        }

        if (object3D.getProductDescription() != null) {
            if (object3D.getProductDescription().getStructure() != null) {
                prompt.append("구조: ").append(object3D.getProductDescription().getStructure()).append("\n");
            }
            if (object3D.getProductDescription().getTheory() != null) {
                prompt.append("원리: ").append(object3D.getProductDescription().getTheory()).append("\n");
            }
            if (object3D.getProductDescription().getPurpose() != null) {
                prompt.append("목적: ").append(object3D.getProductDescription().getPurpose()).append("\n");
            }
        }

        prompt.append("\n학습자의 질문에 친절하고 정확하게 답변해주세요. ");
        prompt.append("복잡한 개념은 쉽게 설명하고, 필요시 예시를 들어주세요.");

        return prompt.toString();
    }
}
