package com.simvex.backend.domain.memo.controller;

import com.simvex.backend.domain.memo.dto.MemoCreateRequestDto;
import com.simvex.backend.domain.memo.dto.MemoResponseDto;
import com.simvex.backend.domain.memo.dto.MemoUpdateRequestDto;
import com.simvex.backend.domain.memo.service.MemoService;
import com.simvex.backend.global.common.ApiResponse;
import com.simvex.backend.global.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    // 메모 목록 조회
    @GetMapping("/api/objects/{objectId}/memos")
    public ResponseEntity<ApiResponse<PageResponse<MemoResponseDto>>> getMemos(
            @PathVariable Long objectId,
            @RequestHeader("X-Session-Token") String sessionToken,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        PageResponse<MemoResponseDto> memos = memoService.getMemos(objectId, sessionToken, pageable);
        return ApiResponse.success(HttpStatus.OK, "메모 목록 조회 성공", memos);
    }

    // 메모 생성
    @PostMapping("/api/objects/{objectId}/memos")
    public ResponseEntity<ApiResponse<MemoResponseDto>> createMemo(
            @PathVariable Long objectId,
            @RequestHeader("X-Session-Token") String sessionToken,
            @RequestBody MemoCreateRequestDto request
    ) {
        MemoResponseDto memo = memoService.createMemo(objectId, sessionToken, request);
        return ApiResponse.success(HttpStatus.CREATED, "메모 생성 성공", memo);
    }

    // 메모 수정
    @PutMapping("/api/memos/{memoId}")
    public ResponseEntity<ApiResponse<MemoResponseDto>> updateMemo(
            @PathVariable Long memoId,
            @RequestHeader("X-Session-Token") String sessionToken,
            @RequestBody MemoUpdateRequestDto request
    ) {
        MemoResponseDto memo = memoService.updateMemo(memoId, sessionToken, request);
        return ApiResponse.success(HttpStatus.OK, "메모 수정 성공", memo);
    }

    // 메모 삭제
    @DeleteMapping("/api/memos/{memoId}")
    public ResponseEntity<ApiResponse<Void>> deleteMemo(
            @PathVariable Long memoId,
            @RequestHeader("X-Session-Token") String sessionToken
    ) {
        memoService.deleteMemo(memoId, sessionToken);
        return ApiResponse.success(HttpStatus.OK, "메모 삭제 성공", null);
    }
}
