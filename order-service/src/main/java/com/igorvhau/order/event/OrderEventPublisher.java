package com.igorvhau.order.event;

import org.springframework.stereotype.Component;

import io.awspring.cloud.sqs.operations.SqsTemplate;

@Component
public class OrderEventPublisher {
	
	private final SqsTemplate sqsTemplate;
	
	public OrderEventPublisher(SqsTemplate sqsTemplate) {
		this.sqsTemplate = sqsTemplate;
	}
	
	public void publish(String message) {
		sqsTemplate.send("order-events-queue", message);
	}
	
}
