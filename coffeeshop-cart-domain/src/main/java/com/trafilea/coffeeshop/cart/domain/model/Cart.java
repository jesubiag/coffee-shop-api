package com.trafilea.coffeeshop.cart.domain.model;

import lombok.With;

import java.util.ArrayList;
import java.util.List;

@With
public record Cart(String cartId, Long ownerId, List<Product> products) {
    public static Cart empty(Long userId) {
        return new Cart(null, userId, List.of());
    }

    public Cart addProducts(List<Product> newProducts) {
        final var combinedProducts = new ArrayList<>(products);
        combinedProducts.addAll(newProducts);
        return withProducts(combinedProducts);
    }
}
