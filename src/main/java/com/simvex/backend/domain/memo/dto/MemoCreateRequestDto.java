package com.simvex.backend.domain.memo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "메모 생성 요청")
@Getter
@NoArgsConstructor
public class MemoCreateRequestDto {

    @Schema(description = "메모 내용", example = "피스톤의 왕복운동이 크랭크샤프트로 전달되는 원리 정리")
    private String content;
}
