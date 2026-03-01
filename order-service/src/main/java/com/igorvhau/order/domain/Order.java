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
	
	/*
	public void markAsCreated() {
		this.status = OrderStatus.CREATED;
	}
	*/
	public void create() {
		this.status = OrderStatus.CREATED;
		this.createdAt = LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public OrderStatus getStatus() {
		return status;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt() {
		this.createdAt = LocalDateTime.now();
	}

}
