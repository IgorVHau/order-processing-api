package com.igorvhau.order.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String customerName;
	
	private BigDecimal amount;
	
	private String status;
	
	private LocalDateTime createdAt;
	
	public Order() {
		this.createdAt = LocalDateTime.now();
		this.status = "CREATED";
	}

}
