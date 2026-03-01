package com.igorvhau.order.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderRequestDTO {
	
	@NotBlank(message = "Customer name is mandatory.")
	private String customerName;
	
	@Positive(message = "Amount must be a positive value")
	@NotNull(message = "Amount cannot be null")
	private BigDecimal amount;
	
	public String getCustomerName() {
		return customerName;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

}
