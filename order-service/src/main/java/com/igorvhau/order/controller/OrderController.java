package com.igorvhau.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igorvhau.order.domain.Order;
import com.igorvhau.order.dto.OrderRequestDTO;
import com.igorvhau.order.dto.OrderResponseDTO;
import com.igorvhau.order.mapper.OrderMapper;
import com.igorvhau.order.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	/*
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		Order createdOrder = orderService.create(order);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(createdOrder);
	}
	*/
	
	/*
	@PostMapping
	public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
		Order order = orderMapper.toEntity(orderRequestDTO);
		Order createdOrder = orderService.create(order);
		OrderResponseDTO orderResponseDTO = orderMapper.toResponse(createdOrder);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDTO);
	}
	*/
	
	@PostMapping 
	public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO request) {
		OrderResponseDTO createdOrder = orderService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
	}
}
