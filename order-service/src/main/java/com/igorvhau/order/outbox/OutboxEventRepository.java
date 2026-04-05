package com.igorvhau.order.outbox;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long>{
	
	List<OutboxEvent> findByProcessedFalse(Pageable pageable);

}
