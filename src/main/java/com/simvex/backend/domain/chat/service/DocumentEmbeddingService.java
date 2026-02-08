package com.simvex.backend.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentEmbeddingService {

    private final VectorStore vectorStore;
    private final ResourceLoader resourceLoader;

    // PDF 파일을 읽어서 chunk 후 Pinecone에 저장
    public int embedDocument(String filePath, Long objectId) {
        // 1. PDF 읽기
        Resource resource = resourceLoader.getResource("classpath:" + filePath);
        PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
        List<Document> documents = reader.read();

        // 2. 각 문서에 objectId 메타데이터 추가
        documents.forEach(doc ->
                doc.getMetadata().put("objectId", objectId.toString())
        );

        // 3. chunk 분할
        TokenTextSplitter splitter = new TokenTextSplitter(
                300,    // defaultChunkSize
                50,     // minChunkSizeChars
                20,     // minChunkLengthToEmbed
                100,    // maxNumChunks
                true    // keepSeparator
        );
        List<Document> chunks = splitter.apply(documents);

        // 4. Pinecone에 저장 (자동으로 임베딩 + 저장)
        vectorStore.add(chunks);

        log.info("임베딩 완료 - 파일: {}, objectId: {}, chunk 수: {}", filePath, objectId, chunks.size());
        return chunks.size();
    }
}
