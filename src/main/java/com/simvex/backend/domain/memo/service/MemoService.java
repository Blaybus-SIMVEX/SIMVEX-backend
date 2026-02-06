package com.simvex.backend.domain.memo.service;

import com.simvex.backend.domain.memo.dto.MemoCreateRequestDto;
import com.simvex.backend.domain.memo.dto.MemoResponseDto;
import com.simvex.backend.domain.memo.dto.MemoUpdateRequestDto;
import com.simvex.backend.domain.memo.entity.Memo;
import com.simvex.backend.domain.memo.exception.MemoErrorCode;
import com.simvex.backend.domain.memo.exception.MemoException;
import com.simvex.backend.domain.memo.repository.MemoRepository;
import com.simvex.backend.domain.object3d.entity.Object3D;
import com.simvex.backend.domain.object3d.exception.Object3DErrorCode;
import com.simvex.backend.domain.object3d.exception.Object3DException;
import com.simvex.backend.domain.object3d.repository.Object3DRepository;
import com.simvex.backend.domain.session.entity.Session;
import com.simvex.backend.domain.session.exception.SessionErrorCode;
import com.simvex.backend.domain.session.exception.SessionException;
import com.simvex.backend.domain.session.repository.SessionRepository;
import com.simvex.backend.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoService {

    private final MemoRepository memoRepository;
    private final Object3DRepository object3DRepository;
    private final SessionRepository sessionRepository;

    // 메모 목록 조회 (특정 오브젝트 + 세션)
    public PageResponse<MemoResponseDto> getMemos(Long objectId, String sessionToken, Pageable pageable) {
        Session session = findSessionByToken(sessionToken);

        return PageResponse.of(
                memoRepository.findByObject3DIdAndSessionId(objectId, session.getId(), pageable)
                        .map(MemoResponseDto::from)
        );
    }

    // 메모 생성
    @Transactional
    public MemoResponseDto createMemo(Long objectId, String sessionToken, MemoCreateRequestDto request) {
        Object3D object3D = object3DRepository.findById(objectId)
                .orElseThrow(() -> new Object3DException(Object3DErrorCode.OBJECT_NOT_FOUND));

        Session session = findSessionByToken(sessionToken);

        Memo memo = Memo.builder()
                .object3D(object3D)
                .session(session)
                .content(request.getContent())
                .build();

        memoRepository.save(memo);

        return MemoResponseDto.from(memo);
    }

    // 메모 수정
    @Transactional
    public MemoResponseDto updateMemo(Long memoId, String sessionToken, MemoUpdateRequestDto request) {
        Session session = findSessionByToken(sessionToken);

        Memo memo = memoRepository.findByIdAndSessionId(memoId, session.getId())
                .orElseThrow(() -> new MemoException(MemoErrorCode.MEMO_NOT_FOUND));

        memo.updateContent(request.getContent());

        return MemoResponseDto.from(memo);
    }

    // 메모 삭제
    @Transactional
    public void deleteMemo(Long memoId, String sessionToken) {
        Session session = findSessionByToken(sessionToken);

        Memo memo = memoRepository.findByIdAndSessionId(memoId, session.getId())
                .orElseThrow(() -> new MemoException(MemoErrorCode.MEMO_NOT_FOUND));

        memoRepository.delete(memo);
    }

    // 세션 토큰으로 세션 조회
    private Session findSessionByToken(String sessionToken) {
        return sessionRepository.findBySessionToken(sessionToken)
                .orElseThrow(() -> new SessionException(SessionErrorCode.SESSION_NOT_FOUND));
    }
}
