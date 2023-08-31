package com.trafilea.coffeeshopdomain.model;

import java.util.List;

public record Cart(String cartId, Long userId, List<Product> products) {

    public Cart withId(String cartId) {
        return new Cart(cartId, userId, products);
    }

    public static Cart newCart(Long userId) {
        return new Cart(null, userId, List.of());
    }

}
