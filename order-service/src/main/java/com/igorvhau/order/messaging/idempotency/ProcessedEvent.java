package com.igorvhau.order.messaging.idempotency;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "processed_events")
public class ProcessedEvent {
	
	@Id
	private String eventId;
	
	private LocalDateTime processedAt;
	
	public ProcessedEvent() {}
	
	public ProcessedEvent(String eventId) {
		this.eventId = eventId;
		this.processedAt = LocalDateTime.now();
	}
	
	public String getEventId() {
		return eventId;
	}
}
