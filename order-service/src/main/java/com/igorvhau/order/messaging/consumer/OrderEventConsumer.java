package com.igorvhau.order.messaging.consumer;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.igorvhau.order.domain.event.OrderCreatedEvent;
import com.igorvhau.order.messaging.idempotency.ProcessedEvent;
import com.igorvhau.order.messaging.idempotency.ProcessedEventRepository;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class OrderEventConsumer {
	
	private final ProcessedEventRepository repository;
	
	public OrderEventConsumer(ProcessedEventRepository repository) {
		this.repository = repository;
	}
	
	@SqsListener("${aws.sqs.queue.order-events}")
	@Transactional
	public void handle(OrderCreatedEvent event) {
		
		try {
			repository.save(new ProcessedEvent(event.eventId()));
			
			System.out.println(" Processing event: " + event.eventId());
		} catch (DataIntegrityViolationException e) {
			System.err.println(" Duplicated event ignored: " + event.eventId());
		}
	}

}
