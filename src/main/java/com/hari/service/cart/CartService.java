package com.hari.service.cart;

import com.hari.model.Cart;
import com.hari.model.User;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
