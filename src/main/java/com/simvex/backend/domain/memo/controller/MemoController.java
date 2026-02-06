package com.simvex.backend.domain.memo.controller;

import com.simvex.backend.domain.memo.dto.MemoCreateRequestDto;
import com.simvex.backend.domain.memo.dto.MemoResponseDto;
import com.simvex.backend.domain.memo.dto.MemoUpdateRequestDto;
import com.simvex.backend.domain.memo.service.MemoService;
import com.simvex.backend.global.common.ApiResponse;
import com.simvex.backend.global.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Memo", description = "메모 API")
@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @Operation(summary = "메모 목록 조회", description = "특정 오브젝트에 대한 메모 목록을 조회합니다.")
    @GetMapping("/api/objects/{objectId}/memos")
    public ResponseEntity<ApiResponse<PageResponse<MemoResponseDto>>> getMemos(
            @Parameter(description = "오브젝트 ID", required = true)
            @PathVariable Long objectId,
            @Parameter(description = "세션 토큰", required = true)
            @RequestHeader("X-Session-Token") String sessionToken,
            @Parameter(hidden = true)
            @PageableDefault(size = 10) Pageable pageable
    ) {
        PageResponse<MemoResponseDto> memos = memoService.getMemos(objectId, sessionToken, pageable);
        return ApiResponse.success(HttpStatus.OK, "메모 목록 조회 성공", memos);
    }

    @Operation(summary = "메모 생성", description = "새로운 메모를 생성합니다.")
    @PostMapping("/api/objects/{objectId}/memos")
    public ResponseEntity<ApiResponse<MemoResponseDto>> createMemo(
            @Parameter(description = "오브젝트 ID", required = true)
            @PathVariable Long objectId,
            @Parameter(description = "세션 토큰", required = true)
            @RequestHeader("X-Session-Token") String sessionToken,
            @RequestBody MemoCreateRequestDto request
    ) {
        MemoResponseDto memo = memoService.createMemo(objectId, sessionToken, request);
        return ApiResponse.success(HttpStatus.CREATED, "메모 생성 성공", memo);
    }

    @Operation(summary = "메모 수정", description = "기존 메모를 수정합니다.")
    @PutMapping("/api/memos/{memoId}")
    public ResponseEntity<ApiResponse<MemoResponseDto>> updateMemo(
            @Parameter(description = "메모 ID", required = true)
            @PathVariable Long memoId,
            @Parameter(description = "세션 토큰", required = true)
            @RequestHeader("X-Session-Token") String sessionToken,
            @RequestBody MemoUpdateRequestDto request
    ) {
        MemoResponseDto memo = memoService.updateMemo(memoId, sessionToken, request);
        return ApiResponse.success(HttpStatus.OK, "메모 수정 성공", memo);
    }

    @Operation(summary = "메모 삭제", description = "메모를 삭제합니다.")
    @DeleteMapping("/api/memos/{memoId}")
    public ResponseEntity<ApiResponse<Void>> deleteMemo(
            @Parameter(description = "메모 ID", required = true)
            @PathVariable Long memoId,
            @Parameter(description = "세션 토큰", required = true)
            @RequestHeader("X-Session-Token") String sessionToken
    ) {
        memoService.deleteMemo(memoId, sessionToken);
        return ApiResponse.success(HttpStatus.OK, "메모 삭제 성공", null);
    }
}
