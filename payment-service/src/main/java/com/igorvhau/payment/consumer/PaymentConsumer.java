package com.igorvhau.payment.consumer;

import org.springframework.stereotype.Service;

import com.igorvhau.payment.domain.event.OrderCreatedEvent;
import com.igorvhau.payment.service.PaymentProcessor;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Service
public class PaymentConsumer {
	
	private final PaymentProcessor processor;
	
	public PaymentConsumer(PaymentProcessor processor) {
		this.processor = processor;
	}
	
	@SqsListener("${aws.sqs.queue.order-events}")
	public void handle(OrderCreatedEvent event) {
		processor.process(event);
	}
	
}
