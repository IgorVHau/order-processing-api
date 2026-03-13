package com.igorvhau.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igorvhau.order.domain.Order;
import com.igorvhau.order.dto.OrderRequestDTO;
import com.igorvhau.order.dto.OrderResponseDTO;
import com.igorvhau.order.mapper.OrderMapper;
import com.igorvhau.order.repository.OrderRepository;
import com.igorvhau.order.event.OrderCreatedEvent;
import com.igorvhau.order.event.OrderEventPublisher;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	private final OrderMapper orderMapper;
	
	private final OrderEventPublisher eventPublisher;
	
	public OrderService(
			OrderRepository orderRepository, 
			OrderMapper orderMapper,
			OrderEventPublisher eventPublisher
			) {
		this.orderRepository = orderRepository;
		this.orderMapper = orderMapper;
		this.eventPublisher = eventPublisher;
	}
	
	@Transactional
	public OrderResponseDTO create(OrderRequestDTO request) {
		Order order = orderMapper.toEntity(request);
		
		order.create();
		
		Order savedOrder = orderRepository.save(order);
		publishOrderCreatedEvent(savedOrder);
		OrderResponseDTO response = orderMapper.toResponse(savedOrder);
		
		return response;
	}
	
	private void publishOrderCreatedEvent(Order order) {
		OrderCreatedEvent event = new OrderCreatedEvent(
				order.getId(),
				order.getCustomerName(),
				order.getAmount(),
				order.getStatus(),
				order.getCreatedAt()
				);
		
		eventPublisher.publish(event);
	}

}
