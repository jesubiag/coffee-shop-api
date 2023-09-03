package com.trafilea.coffeeshop.cart.domain.repository;

import com.trafilea.coffeeshop.cart.domain.model.Cart;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface CartRepository {

    Mono<String> create(Cart cart);

    Mono<Optional<Cart>> read(String cartId);

    Mono<Void> update(Cart cart);
}
