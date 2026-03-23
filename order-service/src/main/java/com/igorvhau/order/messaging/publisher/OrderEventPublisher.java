package com.igorvhau.order.messaging.publisher;

import org.springframework.stereotype.Component;

import com.igorvhau.order.domain.event.OrderCreatedEvent;

import io.awspring.cloud.sqs.operations.SqsTemplate;

@Component
public class OrderEventPublisher {
	
	private final SqsTemplate sqsTemplate;
	
	public OrderEventPublisher(SqsTemplate sqsTemplate) {
		this.sqsTemplate = sqsTemplate;
	}
	
	public void publish(OrderCreatedEvent event) {
		sqsTemplate.send("order-events-queue", event);
	}
	
}
