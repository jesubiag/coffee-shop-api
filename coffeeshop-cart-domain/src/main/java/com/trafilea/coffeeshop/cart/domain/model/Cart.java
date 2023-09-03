package com.trafilea.coffeeshop.cart.domain.model;

import lombok.With;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@With
public record Cart(String cartId, Long ownerId, List<CartProduct> products) {

    public static Cart empty(Long userId) {
        return new Cart(null, userId, List.of());
    }

    public Cart addProducts(List<CartProduct> newProducts) {
        final var combinedProducts = new ArrayList<>(products);
        combinedProducts.addAll(newProducts);
        return withProducts(combinedProducts);
    }

    public Cart updateProduct(CartProduct cartProduct) {
        final var updatedProducts = replace(new ArrayList<>(products), cartProduct);
        return withProducts(updatedProducts);
    }

    private List<CartProduct> replace(List<CartProduct> products, CartProduct cartProduct) {
        final var productId = cartProduct.productId();
        replaceIf(products, p -> Objects.equals(p.productId(), productId), cartProduct);
        return products;
    }

    private static <T> void replaceIf(List<T> list, Predicate<T> predicate, T replacement) {
        for (int i = 0; i < list.size(); ++i)
            if (predicate.test(list.get(i)))
                list.set(i, replacement);
    }
}
