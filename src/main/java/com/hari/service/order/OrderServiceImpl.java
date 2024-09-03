package com.hari.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hari.enums.OrderStatus;
import com.hari.exception.ResourceNotFoundException;
import com.hari.model.Cart;
import com.hari.model.Order;
import com.hari.model.OrderItem;
import com.hari.model.Product;
import com.hari.repository.OrderRepository;
import com.hari.repository.ProductRepository;
import com.hari.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private ProductRepository productRepository;
    private CartService cartService;

    @Override
    public Order placeOrder(Long userId) {

        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemsList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemsList));
        order.setTotalAmount(calulateTotalAmount(orderItemsList));
        Order saveOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return saveOrder;

    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();

    }

    private BigDecimal calulateTotalAmount(List<OrderItem> orderItemsList) {
        return orderItemsList
                .stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrder(Long OrderId) {
        return orderRepository.findById(OrderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
