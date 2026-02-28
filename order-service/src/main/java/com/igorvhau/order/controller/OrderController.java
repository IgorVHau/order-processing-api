package com.igorvhau.order.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igorvhau.order.domain.Order;
import com.igorvhau.order.service.OrderService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	private OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@Transactional
	@PostMapping
	public Order createOrder(@RequestBody Order order) {
		return orderService.create(order);
	}

}
