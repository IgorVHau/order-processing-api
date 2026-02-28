package com.igorvhau.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igorvhau.order.domain.Order;
import com.igorvhau.order.dto.OrderRequestDTO;
import com.igorvhau.order.dto.OrderResponseDTO;
import com.igorvhau.order.mapper.OrderMapper;
import com.igorvhau.order.repository.OrderRepository;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	private final OrderMapper orderMapper;
	
	public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
		this.orderRepository = orderRepository;
		this.orderMapper = orderMapper;
	}
	
	/*
	@Transactional
	public Order create(Order order) {
		order.markAsCreated();
		order.setCreatedAt();
		return orderRepository.save(order);
	}
	*/
	
	public OrderResponseDTO create(OrderRequestDTO request) {
		Order order = orderMapper.toEntity(request);
		
		order.markAsCreated();
		order.setCreatedAt();
		
		Order savedOrder = orderRepository.save(order);
		OrderResponseDTO response = orderMapper.toResponse(savedOrder);
		
		return response;
	}
	

}
