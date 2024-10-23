package com.example.ecommerce.services.iservices;

import com.example.ecommerce.models.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long intializrCart();

    Cart getCartByUserId(Long userId);
}
