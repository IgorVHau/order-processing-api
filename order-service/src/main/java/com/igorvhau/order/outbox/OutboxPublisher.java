package com.igorvhau.order.outbox;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.*;

import com.igorvhau.order.domain.event.OrderCreatedEvent;
import com.igorvhau.order.messaging.publisher.OrderEventPublisher;

import tools.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OutboxPublisher {
	
	private static final Logger log = LoggerFactory.getLogger(OutboxPublisher.class);
	
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
		
		if(events.isEmpty()) return;
		
		for (OutboxEvent event : events) {
			try {
				OrderCreatedEvent domainEvent = 
						objectMapper.readValue(event.getPayload(), OrderCreatedEvent.class);
				publisher.publish(domainEvent);
				
				event.markProcessed();
				repository.save(event);
				
				log.info("Successfully published event: {} for aggregate: {}", event.getEventId(), event.getAggregateId());
			} catch (Exception e) {
				log.error("Failed to publish outbox event: {} | Error: {}", event.getEventId(), event.getClass().getSimpleName());
			}
		}
	}

}
