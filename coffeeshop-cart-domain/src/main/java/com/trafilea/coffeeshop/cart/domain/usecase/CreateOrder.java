package com.trafilea.coffeeshop.cart.domain.usecase;

import com.trafilea.coffeeshop.cart.domain.model.Order;
import reactor.core.publisher.Mono;

public interface CreateOrder {
    Mono<Order> execute(String cartId);
}
