package com.simvex.backend.domain.session.service;

import com.simvex.backend.domain.session.dto.SessionResponseDto;
import com.simvex.backend.domain.session.entity.Session;
import com.simvex.backend.domain.session.exception.SessionErrorCode;
import com.simvex.backend.domain.session.exception.SessionException;
import com.simvex.backend.domain.session.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SessionService {

    private final SessionRepository sessionRepository;

    // 새 세션 생성
    @Transactional
    public SessionResponseDto createSession() {
        Session session = Session.create();
        sessionRepository.save(session);

        return SessionResponseDto.from(session);
    }

    // 세션 유효성 확인 및 마지막 접근 시간 갱신
    @Transactional
    public SessionResponseDto validateSession(String sessionToken) {
        Session session = sessionRepository.findBySessionToken(sessionToken)
                .orElseThrow(() -> new SessionException(SessionErrorCode.SESSION_NOT_FOUND));

        session.updateLastAccessedAt();

        return SessionResponseDto.from(session);
    }
}
