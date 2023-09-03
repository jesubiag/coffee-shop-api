package com.trafilea.coffeeshop.app.rest.create;

import com.trafilea.coffeeshop.app.rest.CoffeeShopHandler;
import com.trafilea.coffeeshop.cart.domain.api.CartApi;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class CartCreationHandler extends CoffeeShopHandler {

    private final CartApi cartApi;

    public CartCreationHandler(CartApi cartApi) {
        this.cartApi = cartApi;
    }

    public Mono<ServerResponse> createCart(Long userId) {
        return cartApi.createCart(userId)
                .map(CartCreationJsonResponse::new)
                .flatMap(this::created);
    }

}
