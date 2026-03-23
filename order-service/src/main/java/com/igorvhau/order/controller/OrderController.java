package com.igorvhau.order.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igorvhau.order.domain.OrderStatus;
import com.igorvhau.order.domain.event.OrderCreatedEvent;
import com.igorvhau.order.dto.OrderRequestDTO;
import com.igorvhau.order.dto.OrderResponseDTO;
import com.igorvhau.order.messaging.publisher.OrderEventPublisher;
import com.igorvhau.order.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private OrderService orderService;
	
	private OrderEventPublisher orderEventPublisher;
	
	public OrderController(OrderService orderService, OrderEventPublisher orderEventPublisher) {
		this.orderService = orderService;
		this.orderEventPublisher = orderEventPublisher;
	}
	
	@PostMapping 
	public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO request) {
		OrderResponseDTO createdOrder = orderService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
	}
	

	@PostMapping("/test")
	public void sendTestEvent() {
		
		OrderCreatedEvent event = OrderCreatedEvent.of(
				UUID.randomUUID().getMostSignificantBits(),
				"Random customer",
				BigDecimal.valueOf(100),
				OrderStatus.CREATED,
				LocalDateTime.now()
				);
		
		orderEventPublisher.publish(event);
	}
}
