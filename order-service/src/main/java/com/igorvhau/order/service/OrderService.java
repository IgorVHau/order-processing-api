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
		OrderCreatedEvent event = new OrderCreatedEvent(
				savedOrder.getId(),
				savedOrder.getCustomerName(),
				savedOrder.getAmount(),
				savedOrder.getStatus(),
				savedOrder.getCreatedAt()
				);
		eventPublisher.publish(event);
		
		OrderResponseDTO response = orderMapper.toResponse(savedOrder);
		
		return response;
	}	

}
