package com.igorvhau.order.outbox;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "outbox_events")
public class OutboxEvent {
	
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
		this.aggregateType = aggregateType;
		this.aggregateId = aggregateId;
		this.eventType = eventType;
		this.payload = payload;
		this.createdAt = LocalDateTime.now();
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
