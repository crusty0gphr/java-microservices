package com.harut.songservice.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class BadRequestException extends RuntimeException {
	private final Map<String, String> errorDetails;

	public BadRequestException(String message) {
		super(message);
		this.errorDetails = null;
	}

	public BadRequestException(String message, Map<String, String> errorDetails) {
		super(message);
		this.errorDetails = errorDetails;
	}
}
