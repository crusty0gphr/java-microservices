package com.harut.songservice.exceptions;

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
		var errorDetails = ex.getErrorDetails();
		if (errorDetails != null && !errorDetails.isEmpty()) {
			return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), errorDetails);
		}
		return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	// Handle EntityNotFoundException with 404 NOT_FOUND
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(EntityNotFoundException ex) {
		return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	// Handle EntityAlreadyExistsException with 409 CONFLICT
	@ExceptionHandler(EntityAlreadyExistsException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(EntityAlreadyExistsException ex) {
		return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
	}

	// Handle IllegalArgumentException with 400 BAD_REQUEST
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
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

	private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message, Map<String, String> errorDetails) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("errorCode", status.value());
		errorResponse.put("details", errorDetails);
		errorResponse.put("errorMessage", message);
		return new ResponseEntity<>(errorResponse, status);
	}
}