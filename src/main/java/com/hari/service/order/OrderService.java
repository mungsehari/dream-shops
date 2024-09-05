package com.hari.service.order;

import java.util.List;

import com.hari.dto.OrderDto;
import com.hari.model.Order;

public interface OrderService {

    Order placeOrder(Long userId);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
