package com.shop.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.demo.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
    void deleteAllByCartId(Long id);
    
    List<CartItem> findByProductId(Long productId);
    
}
