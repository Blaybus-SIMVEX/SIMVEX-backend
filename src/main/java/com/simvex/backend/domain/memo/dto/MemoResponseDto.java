package com.simvex.backend.domain.memo.dto;

import com.simvex.backend.domain.memo.entity.Memo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MemoResponseDto {

    private Long id;
    private Long objectId;
    private String content;
    private LocalDateTime createdAt;
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
