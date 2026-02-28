package com.igorvhau.order.dto;

import java.math.BigDecimal;

public class OrderRequestDTO {
	
	private String customerName;
	private BigDecimal amount;
	
	public String getCustomerName() {
		return customerName;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

}
