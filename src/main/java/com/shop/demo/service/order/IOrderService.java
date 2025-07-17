package com.shop.demo.service.order;

import java.util.List;

import com.shop.demo.dto.OrderDto;
import com.shop.demo.model.Order;

public interface IOrderService {

	Order placeOrder(Long userId);
	OrderDto getOrder(Long orderId);
	List<OrderDto> getUserOrders(Long userId);

	OrderDto convertToDto(Order order);

}
