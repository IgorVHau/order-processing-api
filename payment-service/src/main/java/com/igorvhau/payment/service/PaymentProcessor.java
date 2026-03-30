package com.igorvhau.payment.service;

import java.math.BigDecimal;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igorvhau.payment.domain.event.OrderCreatedEvent;
import com.igorvhau.payment.messaging.idempotency.ProcessedEvent;
import com.igorvhau.payment.messaging.idempotency.ProcessedEventRepository;

@Service
public class PaymentProcessor {
	
	private final ProcessedEventRepository repository;
	
	public PaymentProcessor(ProcessedEventRepository repository) {
		this.repository = repository;
	}
	
	@Retryable(
			retryFor={RuntimeException.class}, 
			maxAttempts=3,
			backoff=@Backoff(delay=2000, multiplier=2)
			)
	@Transactional
	public void process(OrderCreatedEvent event) {
		
		try {
			repository.saveAndFlush(new ProcessedEvent(event.eventId()));
		} catch (DataIntegrityViolationException e) {
			System.err.println("Event already processed (idempotent): " + event.eventId());
			return;
		}
		
		if(event.amount().compareTo(BigDecimal.valueOf(50)) > 0) {
			throw new RuntimeException("Simulated failure. ");
		}
		
		System.out.println("Processing event for order: " + event.orderId());
		
		System.out.println("Payment approved: " + event.orderId());
	}
	
	@Recover
	public void recover(RuntimeException e, OrderCreatedEvent event) {
		System.err.println("Process failed after retries → going to DLQ: " + event);
	}

}
