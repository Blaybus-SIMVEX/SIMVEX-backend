package com.simvex.backend.domain.ai.dto;

public record AiResponse(
    Status status,
    Result result
)
{
    public record Status(
        String code,
        String message
    ){}

    public record Result(
        Message message,
        Integer inputLength,
        Integer outputLength,
        String stopReason,
        long seed
    ){}

    public record Message(
        String role,
        String content
    ) {}
}
