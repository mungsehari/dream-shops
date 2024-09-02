package com.hari.service.cart;

import com.hari.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();
}
