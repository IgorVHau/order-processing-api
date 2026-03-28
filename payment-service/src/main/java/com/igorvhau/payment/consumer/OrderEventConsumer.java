package com.igorvhau.payment.consumer;

import org.springframework.stereotype.Component;

import com.igorvhau.payment.domain.event.OrderCreatedEvent;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class OrderEventConsumer {
	
	private final PaymentConsumer paymentService;
	
	public OrderEventConsumer(PaymentConsumer paymentService) {
		this.paymentService = paymentService;
	}
	
	/*
	@SqsListener("${aws.sqs.queue.order-events}")
	public void handle(OrderCreatedEvent event) {
		
		System.out.println(" Event received: " + event.eventId());
		
		paymentService.process(event);
	}*/

}
