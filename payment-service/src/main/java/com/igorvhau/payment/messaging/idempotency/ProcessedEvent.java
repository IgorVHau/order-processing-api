package com.igorvhau.payment.messaging.idempotency;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

@Entity
@Table(name = "processed_events")
public class ProcessedEvent {
	
	@Id
	private String eventId;
	
	public ProcessedEvent() {
		
	}
	
	public ProcessedEvent(String eventId) {
		this.eventId = eventId;
	}

}
