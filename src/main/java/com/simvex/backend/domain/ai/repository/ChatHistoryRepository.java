package com.simvex.backend.domain.ai.repository;

import com.simvex.backend.domain.ai.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

    List<ChatHistory> findBySessionTokenAndObject3dIdOrderByCreatedAtAsc(
            String sessionToken, Long object3dId);
}
