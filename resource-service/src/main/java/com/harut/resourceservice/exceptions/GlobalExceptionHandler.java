package com.harut.resourceservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle BadRequestException with 400 BAD_REQUEST
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {
		return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	// Handle EntityNotFoundException with 404 NOT_FOUND
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(EntityNotFoundException ex) {
		return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	// Handle ProcessingException with 400 BAD_REQUEST
	@ExceptionHandler(ProcessingException.class)
	public ResponseEntity<Map<String, Object>> handleProcessingException(ProcessingException ex) {
		return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	// Handle IllegalArgumentException with 400 BAD_REQUEST
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
		return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	// Handle InvalidContentTypeException with 400 BAD_REQUEST
	@ExceptionHandler(InvalidContentTypeException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(InvalidContentTypeException ex) {
		return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	// Handle Generic Exception (fallback for other unhandled exceptions)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
		return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	// Helper method to build error response
	private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("errorCode", status.value());
		errorResponse.put("errorMessage", message);

		return new ResponseEntity<>(errorResponse, status);
	}
}