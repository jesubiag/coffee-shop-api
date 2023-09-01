package com.trafilea.coffeeshop.cart.domain.api;

import com.trafilea.coffeeshop.cart.domain.usecase.CreateCart;
import reactor.core.publisher.Mono;

public class CartApi {

    private final CreateCart createCart;

    public CartApi(CreateCart createCart) {
        this.createCart = createCart;
    }

    public Mono<String> createCart(Long userId) {
        // TODO: any validation?
        return createCart.execute(userId);
    }
}
