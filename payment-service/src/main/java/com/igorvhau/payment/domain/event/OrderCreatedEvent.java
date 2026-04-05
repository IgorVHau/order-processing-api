package com.igorvhau.payment.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderCreatedEvent(
	    String eventId,
	    Long orderId,
	    String customerName,
	    BigDecimal amount,
	    String status,
	    LocalDateTime createdAt
	) {
	
}
