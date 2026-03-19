package com.igorvhau.order.messaging.consumer;

import org.springframework.stereotype.Component;

import com.igorvhau.order.domain.event.OrderCreatedEvent;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class OrderEventConsumer {
	
	//@SqsListener("order-events-queue")
	@SqsListener("${aws.sqs.queue.order-events}")
	public void handle(OrderCreatedEvent event) {
		System.out.println(" Received from SQS: " + event);
	}

}
