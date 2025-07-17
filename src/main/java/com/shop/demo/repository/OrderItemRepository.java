package com.shop.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.demo.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	List<OrderItem> findByProductId(Long id);

}
