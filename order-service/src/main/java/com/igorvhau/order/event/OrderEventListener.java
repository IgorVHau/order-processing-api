package com.igorvhau.order.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {
	
	@EventListener
	public void handleOrderCreated(OrderCreatedEvent event) {
		System.out.println("Order created event received: " + event);
	}

}
