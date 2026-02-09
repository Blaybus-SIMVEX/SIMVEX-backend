package com.simvex.backend.domain.ai.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AiContextService {

    private static final int CONTEXT_MAX_LENGTH = 10;
    private final Cache<String, Deque<ConversationPair>> contextCache;

    public AiContextService() {
        this.contextCache = Caffeine.newBuilder()
                .maximumSize(100)  // 30명 + 여유
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .recordStats()
                .build();
    }

    // 대화 페어 저장 (질문-답변 묶음)
    public void addConversation(String userId, String object3dId, UserMessage question, AssistantMessage answer) {
        String context_key = "userId:"+ userId + "object3dId:" + object3dId;

        Deque<ConversationPair> conversations = contextCache.getIfPresent(context_key);

        if (conversations == null) {
            conversations = new ArrayDeque<>();
        }

        conversations.addLast(new ConversationPair(question, answer, LocalDateTime.now()));

        // 최대 10개 대화 페어만 유지 (= 20개 메시지)
        while (conversations.size() > CONTEXT_MAX_LENGTH) {
            conversations.removeFirst();
        }

        contextCache.put(context_key, conversations);
    }

    // ChatClient용 메시지 리스트 변환
    public List<Message> getMessagesForPrompt(String userId, String object3dId) {
        String context_key = "userId:"+ userId + "object3dId:" + object3dId;

        Deque<ConversationPair> conversations = contextCache.getIfPresent(context_key);

        if (conversations == null || conversations.isEmpty()) {
            return Collections.emptyList();
        }

        List<Message> messages = new ArrayList<>();
        for (ConversationPair pair : conversations) {
            messages.add(pair.userMessage());
            messages.add(pair.assistantMessage());
        }

        return messages;
    }

    // 내부 클래스: 대화 페어
    public record ConversationPair(
            UserMessage userMessage,
            AssistantMessage assistantMessage,
            LocalDateTime timestamp
    ) {}
}