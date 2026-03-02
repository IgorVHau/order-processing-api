package com.igorvhau.order.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, length=100)
	private String customerName;
	
	@Column(nullable=false, precision=10, scale=2)
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private OrderStatus status;
	
	@PastOrPresent
	@Column(nullable=false, updatable=false)
	private LocalDateTime createdAt;
	
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

}
