package com.igorvhau.order.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;

public record ProblemDetails(
		String type, 
		String title, 
		int status,
		String detail,
		String instance,
		LocalDateTime timestamp
		) {
	
	public ProblemDetails(String type, String title, int status, String detail, String instance) {
		this(type, title, status, detail, instance, LocalDateTime.now());
	}

}
