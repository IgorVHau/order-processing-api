package com.igorvhau.order.mapper;

import org.springframework.stereotype.Component;

import com.igorvhau.order.domain.Order;
import com.igorvhau.order.dto.OrderRequestDTO;
import com.igorvhau.order.dto.OrderResponseDTO;

@Component
public class OrderMapper {
	
	public Order toEntity(OrderRequestDTO dto) {
		Order order = new Order();
		order.setCustomerName(dto.getCustomerName());
		order.setAmount(dto.getAmount());
		
		return order;
	}
	
	public OrderResponseDTO toResponse(Order order) {
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO(
				order.getId(),
				order.getCustomerName(),
				order.getAmount(),
				order.getStatus(),
				order.getCreatedAt()
				);
		return orderResponseDTO;
	}

}
