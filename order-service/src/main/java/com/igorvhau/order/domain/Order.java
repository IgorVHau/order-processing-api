package com.igorvhau.order.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String customerName;
	
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@PastOrPresent
	private LocalDateTime createdAt;
	
	public void markAsCreated() {
		this.status = OrderStatus.CREATED;
	}
	
	public void setCreatedAt() {
		this.createdAt = LocalDateTime.now();
	}

}
