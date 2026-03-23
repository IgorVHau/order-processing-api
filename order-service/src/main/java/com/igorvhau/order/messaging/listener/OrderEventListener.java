package com.igorvhau.order.messaging.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.igorvhau.order.domain.event.OrderCreatedEvent;

@Component
public class OrderEventListener {
	
	@EventListener
	public void handleOrderCreated(OrderCreatedEvent event) {
		System.out.println("Order created event received: " + event);
	}

}
