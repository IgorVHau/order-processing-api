package com.igorvhau.order.exception;

import java.time.LocalDateTime;

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
