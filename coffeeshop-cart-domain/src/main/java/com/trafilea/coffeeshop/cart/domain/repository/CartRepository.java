package com.trafilea.coffeeshop.cart.domain.repository;

import com.trafilea.coffeeshop.cart.domain.model.Cart;
import reactor.core.publisher.Mono;

public interface CartRepository {

    Mono<String> create(Cart cart);

}
