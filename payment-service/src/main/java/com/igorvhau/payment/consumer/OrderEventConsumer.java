package com.igorvhau.payment.consumer;

import org.springframework.stereotype.Component;

import com.igorvhau.payment.domain.event.OrderCreatedEvent;
import com.igorvhau.payment.service.PaymentService;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class OrderEventConsumer {
	
	private final PaymentService paymentService;
	
	public OrderEventConsumer(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@SqsListener("${aws.sqs.queue.order-events}")
	public void handle(OrderCreatedEvent event) {
		
		System.out.println(" Event received: " + event.eventId());
		
		paymentService.process(event);
	}

}
