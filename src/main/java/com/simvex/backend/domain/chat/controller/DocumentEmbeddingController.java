package com.simvex.backend.domain.chat.controller;

import com.simvex.backend.domain.chat.service.DocumentEmbeddingService;
import com.simvex.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Document Embedding", description = "문서 임베딩 API (관리용)")
@RestController
@RequestMapping("/api/admin/embeddings")
@RequiredArgsConstructor
public class DocumentEmbeddingController {

    private final DocumentEmbeddingService documentEmbeddingService;

    @Operation(summary = "문서 임베딩", description = "PDF 문서를 읽어서 Pinecone에 벡터로 저장합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> embedDocument(
            @Parameter(description = "PDF 파일 경로 (classpath 기준)", example = "docs/SIMVEX_Drone_RAG.pdf")
            @RequestParam String filePath,
            @Parameter(description = "오브젝트 ID", example = "1")
            @RequestParam Long objectId
    ) {
        int chunkCount = documentEmbeddingService.embedDocument(filePath, objectId);

        Map<String, Object> result = Map.of(
                "filePath", filePath,
                "objectId", objectId,
                "chunkCount", chunkCount
        );

        return ApiResponse.success(HttpStatus.OK, "문서 임베딩 완료", result);
    }
}
