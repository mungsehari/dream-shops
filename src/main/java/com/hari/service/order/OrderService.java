package com.hari.service.order;

import java.util.List;

import com.hari.model.Order;

public interface OrderService {

    Order placeOrder(Long userId);

    Order getOrder(Long OrderId);

    List<Order> getUserOrders(Long userId);

}
