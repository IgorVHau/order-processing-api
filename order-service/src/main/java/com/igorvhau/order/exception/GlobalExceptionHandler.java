package com.igorvhau.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("BUSINESS_ERROR", ex.getMessage()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
    		String message = ex.getBindingResult()
    				.getFieldErrors()
    				.stream()
    				.map(error -> error.getDefaultMessage())
    				.findFirst()
    				.orElse("Validation error");
    		
    		return ResponseEntity
    				.badRequest()
    				.body(new ErrorResponse("VALIDATION_ERROR", message));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    		return ResponseEntity
    				.status(HttpStatus.BAD_REQUEST)
    				.body(new ErrorResponse("GENERIC_ERROR", ex.getMessage()));
    }
}
