package com.trafilea.coffeeshop.cart.domain.usecase;

import reactor.core.publisher.Mono;

public interface CreateCart {

    Mono<String> execute(Long userId);

}
