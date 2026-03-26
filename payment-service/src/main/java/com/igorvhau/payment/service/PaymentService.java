package com.igorvhau.payment.service;

import org.springframework.stereotype.Service;

import com.igorvhau.payment.domain.event.OrderCreatedEvent;

@Service
public class PaymentService {
	
	public void process(OrderCreatedEvent event) {
		try {
			System.out.println(" Processing event for order: " + event.orderId());
			
			System.out.println(" Payment approved for order: " + event.orderId());
		} catch (Exception e) {
			System.out.println(" Payment not approved for order: " + event.orderId() + "\n Exception: " + e);
		}
		
	}

}
