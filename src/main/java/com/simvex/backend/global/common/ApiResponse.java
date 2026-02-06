package com.simvex.backend.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.simvex.backend.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private final String message;

	private final T data;

	private final LocalDateTime timestamp;

	private ApiResponse(String message, T data) {
		this.message = message;
		this.data = data;
		this.timestamp = LocalDateTime.now();
	}

	public static <T> ResponseEntity<ApiResponse<T>> success(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(new ApiResponse<>(message, null));
	}

	public static <T> ResponseEntity<ApiResponse<T>> success(HttpStatus status, String message, T data) {
		return ResponseEntity.status(status).body(new ApiResponse<>(message, data));
	}

	public static <T> ResponseEntity<ApiResponse<T>> error(ErrorCode errorCode) {
		return ResponseEntity
				.status(errorCode.getStatus())
				.body(new ApiResponse<>(errorCode.getMessage(), null));
	}
}
