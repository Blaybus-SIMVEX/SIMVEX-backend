package com.simvex.backend.domain.ai.service;

import com.simvex.backend.domain.ai.dto.AiChatRequestDto;
import com.simvex.backend.domain.ai.dto.ChatHistoryResponseDto;
import com.simvex.backend.domain.ai.entity.ChatHistory;
import com.simvex.backend.domain.ai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiAssistantService {

    private final ChatClient.Builder chatClientBuilder;
    private final VectorStore vectorStore;
    private final AiContextService aiContextService;
    private final ChatHistoryRepository chatHistoryRepository;

    // 스트리밍 답변 생성
    public Flux<String> chatStream(String sessionToken, AiChatRequestDto requestDto) {
        long startTime = System.currentTimeMillis();
        log.info(">>> [1] 요청 시작 - objectId: {}", requestDto.getObject3DId());

        ChatClient chatClient = chatClientBuilder.build();
        
        // 프롬프트 생성 (검색 포함)
        List<Message> messages = createPromptMessages(sessionToken, requestDto, startTime);

        log.info(">>> [3] AI 모델 호출 시작 (경과시간: {}ms)", System.currentTimeMillis() - startTime);

        // 2. AI 응답 수집용
        StringBuilder fullResponse = new StringBuilder();

        // 첫 번째 토큰일 때만 로그 출력 (너무 많음 방지)
        // 하지만 여기서는 매번 찍지 않고, 첫 토큰 감지를 위해 별도 변수 처리가 어려우므로
        // 간단히 로그를 남기거나, 디버그 레벨로 남깁니다.
        // log.debug("토큰 수신: {}", token);
        return chatClient.prompt()
                .messages(messages)
                .stream()
                .content()
                .doOnSubscribe(s -> log.info(">>> [4] 스트림 구독 시작 (API 요청 전송)"))
                .doOnNext(fullResponse::append)
                .doOnComplete(() -> {
                    String aiResponse = fullResponse.toString();
                    aiContextService.addConversation(
                            sessionToken,
                            requestDto.getObject3DId().toString(),
                            new UserMessage(requestDto.getQuestion()),
                            new AssistantMessage(aiResponse)
                    );

                    log.info(aiResponse);

                    // RDB 저장 (추가)
                    chatHistoryRepository.save(ChatHistory.of(
                            sessionToken, requestDto.getObject3DId(),
                            ChatHistory.MessageRole.USER, requestDto.getQuestion()));
                    chatHistoryRepository.save(ChatHistory.of(
                            sessionToken, requestDto.getObject3DId(),
                            ChatHistory.MessageRole.ASSISTANT, aiResponse));

                    log.info(
                            ">>> [5] 스트리밍 완료 (총 소요시간: {}ms)",
                            System.currentTimeMillis() - startTime
                    );
                });
    }

    // 프롬프트 및 메시지 생성 로직 분리
    private List<Message> createPromptMessages(String sessionToken, AiChatRequestDto requestDto, long startTime) {
        // 1. RAG 검색 (Pinecone)
        String filterExpression = "objectId == '" + requestDto.getObject3DId() + "'";

        SearchRequest searchRequest = SearchRequest.builder()
                .query(requestDto.getQuestion())
                .topK(3)
                .filterExpression(filterExpression)
                .build();

        List<Document> similarDocuments = vectorStore.similaritySearch(searchRequest);
        log.info(">>> [2] Pinecone 검색 완료 (문서 {}개, 경과시간: {}ms)", 
                similarDocuments.size(), System.currentTimeMillis() - startTime);

        // 2. Document 구성
        String document = similarDocuments.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        if (similarDocuments.isEmpty()) {
            log.warn("관련 문서를 찾지 못했습니다. objectId: {}", requestDto.getObject3DId());
        }

        // 3. Context 구성
        List<Message> context = aiContextService.getMessagesForPrompt(sessionToken, requestDto.getObject3DId().toString());

        // 4. 프롬프트 구성
        String systemText = String.format("""
                [Role]
                당신은 공학 교육을 돕는 AI 튜터입니다.
                학습자의 질문에 친절하고 정확하게 답변해주세요.
                
                [Rule]
                1. 아래 제공된 [Document] 정보를 최우선으로 참고하여 답변하세요.
                2. 만약 [Document]와 [Context]에 정보가 없거나 부족하다면, "제공되지 않는 정보입니다!" 라고만 답하세요.
                3. 답변할 때 "제공된 자료에 따르면", "문서에 의하면", "학습 자료에는 없지만" 같은 출처 관련 표현은 절대 사용하지 마세요.
                4. 마치 당신이 모든 것을 원래 알고 있던 것처럼 자연스럽게 설명해주세요.
                5. 답변을 할 때는 존댓말을 사용하세요.
                6. 답변은 마크다운 형식으로 작성하되, 아래 형식 규칙을 반드시 지켜주세요.
                
                [형식 규칙]
                - # ## ### 등 헤딩 문법을 절대 사용하지 마세요.
                - 응답 서두에 **굵은 글씨**를 사용하지 마세요. 첫 문장은 반드시 일반 텍스트로 시작하세요.
                - 사용 가능한 마크다운: **굵은 글씨**(문장 중간), 목록(-, 1.), 줄바꿈
                - 설명 후 세부 항목이 있을 때 목록을 사용하세요.
                
                [좋은 답변 예시]
                질문: 피스톤 링의 역할이 뭐야?
                
                피스톤 링은 피스톤에 장착되어 실린더 벽과 밀착하는 환형 부품으로, 크게 세 가지 역할을 수행합니다.
                
                - **가스 밀봉**: 연소실의 고압 가스가 크랭크케이스로 누설되는 것을 방지합니다.
                - **오일 제어**: 윤활유가 연소실로 올라가는 것을 막고, 실린더 벽에 적절한 유막을 유지합니다.
                - **열전달**: 피스톤 헤드의 열을 실린더 벽으로 전달하는 주요 경로로, 전체 방열의 약 60~70%%를 담당합니다.
                
                일반적으로 3개의 링이 1세트로 구성되며, 탑 링이 가스 밀봉, 세컨드 링이 보조 밀봉과 오일 스크래핑, 오일 링이 잉여 오일 제거를 담당합니다.
                
                [나쁜 답변 예시]
                질문: 피스톤 링의 역할이 뭐야?
                
                ### 피스톤 링의 역할
                **피스톤 링은 중요한 부품입니다.**
                제공된 자료에 따르면, 피스톤 링은...
                
                [Document]
                %s
                
                [Context]
                %s
                """,
                document,
                context
        );

        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage(systemText));

        messages.add(new UserMessage(requestDto.getQuestion()));
        
        return messages;
    }

    public List<ChatHistoryResponseDto> getChatHistory(String sessionToken, Long object3dId) {
        return chatHistoryRepository
                .findBySessionTokenAndObject3dIdOrderByCreatedAtAsc(sessionToken, object3dId)
                .stream()
                .map(ChatHistoryResponseDto::from)
                .toList();
    }
}
