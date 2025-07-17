package com.shop.demo.service.cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.shop.demo.exceptions.ResourceNotFoundException;
import com.shop.demo.model.Cart;
import com.shop.demo.model.CartItem;
import com.shop.demo.model.Product;
import com.shop.demo.repository.CartItemRepository;
import com.shop.demo.repository.CartRepository;
import com.shop.demo.service.product.IProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService  implements ICartItemService{
	
	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	private final IProductService productService;
	private final ICartService cartService;

	@Override
	public void addItemToCart(Long cartId, Long productId, int quantity) {
		Cart cart = cartService.getCart(cartId);
		Product product = productService.getProductById(productId);
		System.out.println("\n\n=====================================================================================");
		System.out.println("The product Id:" + productId);
		System.out.println("The product:" + product);
		System.out.println("\n\n=====================================================================================");

		CartItem cartItem = cart.getItems()
				.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst().orElse(new CartItem());
		if (cartItem.getId() == null) {
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setUnitPrice(product.getPrice());
		}
		else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}
		cartItem.setTotalPrice();
		cart.addItem(cartItem);
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
	}

	@Override
	public void removeItemFromCart(Long cartId, Long productId) {
		Cart cart = cartService.getCart(cartId);
		CartItem itemToRemove = getCartItem(cartId, productId);
		cart.removeItem(itemToRemove);
		cartRepository.save(cart);
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		Cart cart = cartService.getCart(cartId);
		cart.getItems()
		.stream()
		.filter(item -> item.getProduct().getId().equals(productId))
		.findFirst()
		.ifPresent(item -> {
			item.setQuantity(quantity);
			item.setUnitPrice(item.getProduct().getPrice());
			item.setTotalPrice();
		});
		BigDecimal totalAmount = cart.getItems()
				.stream().map(CartItem ::getTotalPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		cart.setTotalAmount(totalAmount);
		cartRepository.save(cart);
	}

	@Override
	public CartItem getCartItem(Long cartId, Long productId) {
		Cart cart = cartService.getCart(cartId);
		return  cart.getItems()
				.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
	}
	
}