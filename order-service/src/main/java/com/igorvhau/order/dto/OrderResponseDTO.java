package com.igorvhau.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.igorvhau.order.domain.OrderStatus;

public class OrderResponseDTO {
	
	private Long id;
	private String customerName;
	private BigDecimal amount;
	private OrderStatus status;
	private LocalDateTime createdAt;

	public OrderResponseDTO(Long id, String customerName, BigDecimal amount, OrderStatus status, LocalDateTime createdAt) {
		this.id = id;
		this.customerName = customerName;
		this.amount = amount;
		this.status = status;
		this.createdAt = createdAt;
	}
}
