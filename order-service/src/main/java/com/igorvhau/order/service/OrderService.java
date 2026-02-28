package com.igorvhau.order.service;

import org.springframework.stereotype.Service;

import com.igorvhau.order.domain.Order;
import com.igorvhau.order.repository.OrderRepository;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public Order create(Order order) {
		return orderRepository.save(order);
	}

}
