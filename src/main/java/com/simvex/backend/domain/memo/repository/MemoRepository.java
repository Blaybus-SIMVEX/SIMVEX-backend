package com.simvex.backend.domain.memo.repository;

import com.simvex.backend.domain.memo.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    // 특정 오브젝트의 특정 세션 메모 전체 조회 (페이지네이션)
    Page<Memo> findByObject3DIdAndSessionId(Long object3DId, Long sessionId, Pageable pageable);

    // 특정 메모 조회 (세션 검증용)
    Optional<Memo> findByIdAndSessionId(Long id, Long sessionId);
}
