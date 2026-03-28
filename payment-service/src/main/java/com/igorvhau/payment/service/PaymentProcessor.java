package com.igorvhau.payment.service;

import java.math.BigDecimal;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.igorvhau.payment.domain.event.OrderCreatedEvent;

@Service
public class PaymentProcessor {
	
	@Retryable(
			retryFor={Exception.class}, 
			maxAttempts=3,
			backoff=@Backoff(delay=2000, multiplier=2)
			)
	public void process(OrderCreatedEvent event) {
		System.out.println("Processing event for order: " + event.orderId());
		
		if(event.amount().compareTo(BigDecimal.valueOf(50)) > 0) {
			throw new RuntimeException("Simulated failure. ");
		}
	}
	
	@Recover
	public void recover(Exception e, OrderCreatedEvent event) {
		System.err.println("Process failed after retries: " + event);
	}

}
