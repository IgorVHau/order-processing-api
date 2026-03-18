package com.igorvhau.order.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.igorvhau.order.domain.OrderStatus;


public record OrderCreatedEvent(
		Long orderId,
		String customerName,
		BigDecimal amount,
		OrderStatus status,
		LocalDateTime createdAt) {

}
