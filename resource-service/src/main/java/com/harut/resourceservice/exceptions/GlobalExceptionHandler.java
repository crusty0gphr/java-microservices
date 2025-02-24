package com.harut.resourceservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle BadRequestException with 400 BAD_REQUEST
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {
		return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "BAD_REQUEST");
	}

	// Handle EntityNotFoundException with 400 BAD_REQUEST
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(EntityNotFoundException ex) {
		return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "NOT_FOUND");
	}

	// Handle ProcessingException with 500 INTERNAL_SERVER_ERROR
	@ExceptionHandler(ProcessingException.class)
	public ResponseEntity<Map<String, Object>> handleProcessingException(ProcessingException ex) {
		return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "PROCESSING_ERROR");
	}

	// Handle Generic Exception (fallback for other unhandled exceptions)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
		return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "INTERNAL_ERROR");
	}

	// Helper method to build error response
	private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message, String errorCode) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("error", errorCode);
		errorResponse.put("message", message);
		errorResponse.put("status", status.value());

		return new ResponseEntity<>(errorResponse, status);
	}
}