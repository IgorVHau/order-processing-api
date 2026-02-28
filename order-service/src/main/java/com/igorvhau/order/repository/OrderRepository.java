package com.igorvhau.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.igorvhau.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
