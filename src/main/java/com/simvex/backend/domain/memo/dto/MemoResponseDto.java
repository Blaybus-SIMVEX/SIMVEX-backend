package com.simvex.backend.domain.memo.dto;

import com.simvex.backend.domain.memo.entity.Memo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "메모 응답")
@Getter
@Builder
public class MemoResponseDto {

    @Schema(description = "메모 ID", example = "1")
    private Long id;

    @Schema(description = "오브젝트 ID", example = "1")
    private Long objectId;

    @Schema(description = "메모 내용", example = "피스톤의 왕복운동이 크랭크샤프트로 전달되는 원리 정리")
    private String content;

    @Schema(description = "생성 일시", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "수정 일시", example = "2024-01-15T10:30:00")
    private LocalDateTime updatedAt;

    public static MemoResponseDto from(Memo memo) {
        return MemoResponseDto.builder()
                .id(memo.getId())
                .objectId(memo.getObject3D().getId())
                .content(memo.getContent())
                .createdAt(memo.getCreatedAt())
                .updatedAt(memo.getUpdatedAt())
                .build();
    }
}
