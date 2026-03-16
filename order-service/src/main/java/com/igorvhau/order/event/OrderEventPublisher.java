package com.igorvhau.order.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {
	
	private final ApplicationEventPublisher publisher;
	
	public OrderEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
	
	public void publish(OrderCreatedEvent event) {
		publisher.publishEvent(event);
	}
	
}
