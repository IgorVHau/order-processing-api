package com.igorvhau.payment.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger log = LoggerFactory.getLogger(PaymentProcessor.class);
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
			log.warn("Event already processed (idempotent): {}", event.eventId());
			return;
		}
		
		if(event.amount().compareTo(BigDecimal.valueOf(100)) > 0) {
			log.error("Simulate failure for order: {}", event.orderId());
			throw new RuntimeException("Payment amount exceeds limit for simulation.");
		}
		
		log.info("Payment approved for order: {} | Amount: {}", event.orderId(), event.amount());
	}
	
	@Recover
	public void recover(RuntimeException e, OrderCreatedEvent event) {
		log.error("Process failed after retries for event: {}. Sending to DLQ. Error: {}", event.eventId(), e.getMessage());
		throw e;
	}

}
