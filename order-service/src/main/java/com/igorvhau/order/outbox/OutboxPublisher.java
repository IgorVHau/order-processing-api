package com.igorvhau.order.outbox;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.*;

@Component
public class OutboxPublisher {
	
	private final OutboxEventRepository repository;
	
	public OutboxPublisher(OutboxEventRepository repository) {
		this.repository = repository;
	}
	
	@Scheduled(fixedDelay = 5000)
	public void publishEvents() {
		List<OutboxEvent> events = repository.findByProcessedFalse();
		
		for (OutboxEvent event : events) {
			System.out.println("Publishing event: " + event.getPayload());
			
			event.markProcessed();
			
			repository.save(event);
		}
	}

}
