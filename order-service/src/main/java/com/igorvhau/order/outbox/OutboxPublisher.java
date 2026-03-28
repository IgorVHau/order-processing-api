package com.igorvhau.order.outbox;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.*;

import com.igorvhau.order.domain.event.OrderCreatedEvent;
import com.igorvhau.order.messaging.publisher.OrderEventPublisher;

import tools.jackson.databind.ObjectMapper;

@Component
public class OutboxPublisher {
	
	private final OutboxEventRepository repository;
	private final ObjectMapper objectMapper;
	private final OrderEventPublisher publisher;
	
	public OutboxPublisher(OutboxEventRepository repository, ObjectMapper objectMapper, OrderEventPublisher publisher) {
		this.repository = repository;
		this.objectMapper = objectMapper;
		this.publisher = publisher;
	}
	
	@Scheduled(fixedDelay = 5000)
	public void publishPendingEvents() {
		List<OutboxEvent> events = repository.findByProcessedFalse(PageRequest.of(0, 100));
		
		for (OutboxEvent event : events) {
			try {
				OrderCreatedEvent domainEvent = 
						objectMapper.readValue(event.getPayload(), OrderCreatedEvent.class);
				publisher.publish(domainEvent);
				
				event.markProcessed();
				repository.save(event);
				System.out.println(" Succeded to publish event: " + event.getAggregateId());
			} catch (Exception e) {
				System.err.println(" Failed to publish event: " + event.getId());
				e.printStackTrace();
			}
		}
	}

}
