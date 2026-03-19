package com.igorvhau.order.outbox;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "outbox_events")
public class OutboxEvent {
	
	@Column(nullable = false, unique = true)
	private String eventId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String aggregateType;
	
	@Column(nullable = false)
	private String aggregateId;
	
	@Column(nullable = false)
	private String eventType;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String payload;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private boolean processed = false;
	
	public OutboxEvent() {
		
	}
	
	public OutboxEvent(String aggregateType, String aggregateId, String eventType, String payload) {
		this.eventId = UUID.randomUUID().toString();
		this.aggregateType = aggregateType;
		this.aggregateId = aggregateId;
		this.eventType = eventType;
		this.payload = payload;
		this.createdAt = LocalDateTime.now();
	}
	
	public String getEventId() {
		return eventId;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getAggregateType() {
		return aggregateType;
	}
	
	public String getAggregateId() {
		return aggregateId;
	}
	
	public String getPayload() {
		return payload;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public boolean getProcessed() {
		return processed;
	}
	
	public void markProcessed() {
		this.processed = true;
	}

}
