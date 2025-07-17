package com.shop.demo.service.cart;

import java.math.BigDecimal;

import com.shop.demo.dto.CartDto;
import com.shop.demo.model.Cart;
import com.shop.demo.model.User;
public interface ICartService {

	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);

	Cart initializeNewCart(User user);

	Cart getCartByUserId(Long userId);

	CartDto convertToDto(Cart cart);

}