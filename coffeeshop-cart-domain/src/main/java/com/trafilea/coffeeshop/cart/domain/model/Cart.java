package com.trafilea.coffeeshop.cart.domain.model;

import java.util.List;

public record Cart(String cartId, Long userId, List<Product> products) {
    public static Cart empty(Long userId) {
        return new Cart(null, userId, List.of());
    }
}
