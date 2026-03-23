package com.igorvhau.order.messaging.consumer;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.igorvhau.order.domain.event.OrderCreatedEvent;
import com.igorvhau.order.messaging.idempotency.ProcessedEvent;
import com.igorvhau.order.messaging.idempotency.ProcessedEventRepository;
import com.igorvhau.payment.service.PaymentService;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class OrderEventConsumer {
	
	private final ProcessedEventRepository repository;
	
	private final PaymentService paymentService;
	
	public OrderEventConsumer(ProcessedEventRepository repository, PaymentService paymentService) {
		this.repository = repository;
		this.paymentService = paymentService;
	}
	
	@SqsListener("${aws.sqs.queue.order-events}")
	@Transactional
	public void handle(OrderCreatedEvent event) {
		
		try {
			paymentService.process(event);
			
			repository.save(new ProcessedEvent(event.eventId()));
		} catch (DataIntegrityViolationException e) {
			System.err.println(" Duplicated event ignored: " + event.eventId());
		}
	}

}
