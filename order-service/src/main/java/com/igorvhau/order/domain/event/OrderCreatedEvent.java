package com.igorvhau.order.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.igorvhau.order.domain.OrderStatus;


public record OrderCreatedEvent(
		String eventId,
		Long orderId,
		String customerName,
		BigDecimal amount,
		OrderStatus status,
		LocalDateTime createdAt) {
	
	public static OrderCreatedEvent of(
			Long orderId,
			String customerName,
			BigDecimal amount,
			OrderStatus status,
			LocalDateTime createdAt) {
				return new OrderCreatedEvent(
						UUID.randomUUID().toString(),
						orderId,
						customerName,
						amount,
						status,
						createdAt);
		
	};

}
