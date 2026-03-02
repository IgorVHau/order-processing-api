package com.igorvhau.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetails> handleBusinessException(
    		BusinessException ex,
    		HttpServletRequest request) {
        String message = ex.getLocalizedMessage();
        
        ProblemDetails problem = new ProblemDetails(
        		"https://api.order.com/problems/business-exception",
        		"Business Rule Violation",
        		HttpStatus.UNPROCESSABLE_CONTENT.value(),
        		message,
        		request.getRequestURI()
        		);
    	
        return ResponseEntity
        		.status(HttpStatus.UNPROCESSABLE_CONTENT)
        		.body(problem);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetails> handleValidationException(
    		MethodArgumentNotValidException ex,
    		HttpServletRequest request) {
    		String message = ex.getBindingResult()
    				.getFieldErrors()
    				.stream()
    				.map(error -> error.getDefaultMessage())
    				.findFirst()
    				.orElse("Validation error");
    		
    		ProblemDetails problem = new ProblemDetails(
    				"https://api.order.com/problems/validation-error",
    				"Validation Error",
    				HttpStatus.BAD_REQUEST.value(),
    				message,
    				request.getRequestURI()
    				);
    		
    		return ResponseEntity
    				.status(HttpStatus.BAD_REQUEST)
    				.body(problem);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetails> handleGenericException(
    		Exception ex,
    		HttpServletRequest request) {
    		
    		ProblemDetails problem = new ProblemDetails(
    				"https://api.order.com/problems/internal-server-error",
    				"Internal Server Error",
    				HttpStatus.INTERNAL_SERVER_ERROR.value(),
    				"An unexpected error occured.",
    				request.getRequestURI()
    				);
    	
    		return ResponseEntity
    				.status(HttpStatus.INTERNAL_SERVER_ERROR)
    				.body(problem);
    }
}
