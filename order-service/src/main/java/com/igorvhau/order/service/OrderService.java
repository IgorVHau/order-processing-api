package com.igorvhau.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igorvhau.order.domain.Order;
import com.igorvhau.order.repository.OrderRepository;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@Transactional
	public Order create(Order order) {
		order.markAsCreated();
		order.setCreatedAt();
		return orderRepository.save(order);
	}

}
