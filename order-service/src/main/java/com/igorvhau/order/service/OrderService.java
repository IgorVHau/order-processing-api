package com.igorvhau.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igorvhau.order.domain.Order;
import com.igorvhau.order.domain.event.OrderCreatedEvent;
import com.igorvhau.order.dto.OrderRequestDTO;
import com.igorvhau.order.dto.OrderResponseDTO;
import com.igorvhau.order.mapper.OrderMapper;
import com.igorvhau.order.messaging.publisher.OrderEventPublisher;
import com.igorvhau.order.outbox.OutboxEvent;
import com.igorvhau.order.outbox.OutboxEventRepository;
import com.igorvhau.order.repository.OrderRepository;

import tools.jackson.databind.ObjectMapper;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	private final OrderMapper orderMapper;
	
	private final OutboxEventRepository outboxRepository;
	
	public OrderService(
			OrderRepository orderRepository, 
			OrderMapper orderMapper,
			OutboxEventRepository outboxRepository
			) {
		this.orderRepository = orderRepository;
		this.orderMapper = orderMapper;
		this.outboxRepository = outboxRepository;
	}
	
	@Transactional
	public OrderResponseDTO create(OrderRequestDTO request) {
		Order order = orderMapper.toEntity(request);
		
		order.create();
		
		Order savedOrder = orderRepository.save(order);
		persistOutboxEvent(savedOrder);
		OrderResponseDTO response = orderMapper.toResponse(savedOrder);
		
		return response;
	}
	
	private void persistOutboxEvent(Order savedOrder) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			OutboxEvent event = new OutboxEvent(
					"Order",
					savedOrder.getId().toString(),
					"OrderCreated",
					null
					);
			
			OrderCreatedEvent domainEvent = new OrderCreatedEvent(
					event.getEventId(),
					savedOrder.getId(),
					savedOrder.getCustomerName(),
					savedOrder.getAmount(),
					savedOrder.getStatus(),
					savedOrder.getCreatedAt()
					);

			String payload = mapper.writeValueAsString(domainEvent);
			
			event.setPayload(payload);
					
			outboxRepository.save(event);
			
		} catch (Exception e) {
			throw new RuntimeException("Serialization event failed", e);
		}
	}

}
